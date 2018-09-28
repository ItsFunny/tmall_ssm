/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午1:26:18
* 
*/
package com.tmall.wechat.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.joker.library.QueueLogin.QueueConfig;
import com.joker.library.QueueLogin.Exception.QueueLoginRangeException;
import com.joker.library.QueueLogin.model.QueueLoginResultInfo;
import com.joker.library.QueueLogin.service.QueueLoginService;
import com.joker.library.utils.CollectionUtils;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.TmallUserEnums;
import com.tmall.common.exception.TmallIllegalException;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.utils.CommonUtils;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.utils.CookieUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.UserDTO;
import com.tmall.form.FormUser;
import com.tmall.model.PortalUserConfig;
import com.tmall.model.TmallUser;
import com.tmall.model.TmallUserTest;
import com.tmall.wechat.dao.impl.JedisClient;
import com.tmall.wechat.service.ITmallUserService;
import com.tmall.wechat.utils.StoreUtils;

/**
 * 
 * @author joker
 * @date 创建时间：2018年1月27日 下午1:26:18
 */
@Controller
@RestController
@RequestMapping("/portal")
public class PortalController
{
	@Autowired
	private ITmallUserService tmallUserService;
	@Autowired
	private QueueLoginService queueLoginService;

	@Autowired
	private JedisClient jedisClient;

	public void setSessionAttribute(TmallUser userDTO)
	{
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION, userDTO);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/do_login")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> params)
			throws TmallPortalException, TmallIllegalException, InterruptedException, QueueLoginRangeException
	{
		ModelAndView modelAndView = null;
		String redirectString = request.getParameter("redirect");
		String redirect = StringUtils.isEmpty(redirectString) ? ProjectURLConxtant.PORTAL_INDEX_URL : redirectString;
		String key = (String) params.get("key");
		String password = (String) params.get("password");
		String sessionId = request.getSession().getId();
		TmallUser userDTO = tmallUserService.findUserByKey(key);
		params.put("redirect", redirect);
		// ResultVo<?> resultVo = null;
		QueueLoginResultInfo resultInfo = null;
		if (userDTO == null || !userDTO.getPassword().equals(password))
		{
			params.put("error", "账号或者密码错误,请重新登陆");
			modelAndView = new ModelAndView("redirect:/wechat/portal/login", params);
		} else
		{
			QueueConfig<TmallUser> config = (QueueConfig<TmallUser>) QueueConfig.getQueueconfig();
			LinkedBlockingQueue<TmallUser> onLineUserQueue = config.getOnLineUserQueue();
			resultInfo = queueLoginService.login(sessionId, userDTO);
			if (resultInfo != null)
			{
				if (resultInfo.getCode().equals(QueueLoginService.WAITLOGIN))
				{
					params.put("error", "当前登陆的人数过多,您目前排在第" + resultInfo.getIndex() + "位");
					params.put("index", resultInfo.getIndex());
					params.put("sessionId", sessionId);
					modelAndView = new ModelAndView("wait_login", params);
				} else
				{
					//正常登陆
					UserDTO oldUser = (UserDTO) request.getSession()
							.getAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION);
					onLineUserQueue.remove(oldUser);
					setSessionAttribute(userDTO);
					CookieUtils.setCookie(response, CookieConstant.PORTAL_USER, sessionId,
							CookieConstant.PORTAL_USER_EXPIRE);
					String value = JsonUtils.objectToJson(userDTO);
					String key2 = String.format(RedisConstant.PORTAL_USER_PREFIX, sessionId);
					jedisClient.set(key2, value);
					jedisClient.expire(key2, CookieConstant.PORTAL_USER_EXPIRE + 120);
					modelAndView = new ModelAndView("redirect:" + redirect);
				}
			} else
			{
				//重复登陆相同账户
				modelAndView = new ModelAndView("redirect:" + redirect);
			}
		}
		return modelAndView;
	}

	@RequestMapping("check-index")
	public void checkIndex(HttpServletRequest request, HttpServletResponse response)
	{
		QueueConfig<TmallUser> config = QueueConfig.getQueueConfig();
		String sessionId = request.getParameter("sessionId");
		TmallUser userDTO = config.getWaitUserMap().get(sessionId);
		ResultVo<Integer> resultVo = new ResultVo<>();
		List<Object> list = CollectionUtils.collection2List(config.getWaitUserKeyQueue(), Object.class);
		int index = list.indexOf(sessionId);
		// 用户id在等待队列中不存在
		if (index == -1)
		{
			// 登陆成功的标志:等待队列中无这个用户,在线用户中有这个用户
			// 这里的2个判断有待商榷...因为后台排队登陆是单线程的
			// 124: 21:07 因为后台线程无法获取到servlet中的内容(绑定线程的),所以在这里写入到servlet容器中
			// if(userDTO==null&&PortalUserConfig.OnLineUserQueue.contains(userDTO))
			// {
			// resultVo.setCode(200);
			// resultVo.setMsg("用户已登录,跳转到登陆页面");
			// }
			if (userDTO != null)
			{
				setSessionAttribute(userDTO);
				config.getWaitUserMap().remove(sessionId);
				resultVo.setCode(200);
			} else
			{
				resultVo.setCode(500);
				resultVo.setMsg("用户已经登陆,请不要使用同一浏览器排队登陆");
			}
		} else
		{
			resultVo.setCode(300);
			resultVo.setData(index);
		}
		String json = JsonUtils.objectToJson(resultVo);
		try
		{
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * 前端微信登陆
	 */
	// @RequestMapping("/wechat-login")
	// public ModelAndView wechatLogin(HttpServletRequest request,
	// HttpServletResponse response)
	// {
	// String sessionId = request.getSession().getId();
	// String returnUrl = request.getParameter("redirect");
	// if (returnUrl == null)
	// returnUrl = "http://joker.natapp1.cc:8093/index.html";
	// String url = "http://joker.natapp1.cc:8095/auth?sessionId=" + sessionId +
	// "&returnUrl=" + returnUrl;
	// String path = StoreUtils.getQRPath(url, sessionId);
	// ModelAndView modelAndView = new ModelAndView("wechat");
	// modelAndView.addObject("path", path);
	// modelAndView.addObject("sessionId", sessionId);
	// modelAndView.addObject("redirect", returnUrl);
	// return modelAndView;
	// }

	// @RequestMapping("/register")
	// public ModelAndView register(HttpServletRequest request, HttpServletResponse
	// response)
	// {
	// ModelAndView modelAndView = null;
	// String openid = request.getParameter("openid");
	// String error = request.getParameter("error");
	// if (StringUtils.isEmpty(openid))
	// {
	// modelAndView = new ModelAndView("redirect:login");
	// modelAndView.addObject("error", "请先使用微信扫描注册注册账户");
	// return modelAndView;
	// }
	// modelAndView = new ModelAndView("register");
	// if (!StringUtils.isEmpty(error))
	// {
	// modelAndView.addObject("error", error);
	// }
	// modelAndView.addObject("openid", openid);
	// return modelAndView;
	// }

	@RequestMapping("/do_register")
	public ModelAndView doRegister(TmallUser user, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = null;
		user.setStatus(TmallUserEnums.ALLOWED.getCode());
		Map<String, String> params = new HashMap<>();
		if (StringUtils.isEmpty(user.getOpenid()))
		{
			params.put("error", "请使用微信扫描注册");
			modelAndView = new ModelAndView("redirect:login", params);
			return modelAndView;
		}
		String error = null;
		try
		{
			error = tmallUserService.addNormalUser(user);
			if (error != null)
			{
				params.put("error", error);
				modelAndView = new ModelAndView("redirect:login");
				return modelAndView;
			}
		} catch (Exception e)
		{
			params.put("error", e.getMessage());
			modelAndView = new ModelAndView("redirect:login", params);
			return modelAndView;
		}
		modelAndView = new ModelAndView("redirect:" + ProjectURLConxtant.PORTAL_INDEX_URL, params);
		return modelAndView;
	}

}

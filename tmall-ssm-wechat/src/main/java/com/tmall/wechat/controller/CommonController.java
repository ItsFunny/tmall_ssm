/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午7:06:54
* 
*/
package com.tmall.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.CheckUtil;
import com.tmall.common.utils.CookieUtils;
import com.tmall.common.utils.FileUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.UserDTO;
import com.tmall.model.TmallUser;
import com.tmall.wechat.dao.impl.JedisClient;
import com.tmall.wechat.service.ITmallUserService;
import com.tmall.wechat.utils.StoreUtils;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;

/**
 * 这个controller包含了其他3个controller共同点(需要访问共同的地方)
 * 
 * @author joker
 * @date 创建时间：2018年1月26日 下午7:06:54
 */
@Controller
public class CommonController
{
	@Autowired
	private ITmallUserService tmallUserService;
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private QueueLoginService queueLoginService;
	@Autowired
	private JedisClient jedisClient;

	@RequestMapping("/token")
	public void authorizeToken(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce))
		{
			out.print(echostr);
		}
		response.getWriter().append(request.getContextPath());
	}

	@RequestMapping("/{type}/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error, @PathVariable String type,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		Map<String, String> params = new HashMap<>();
		ModelAndView modelAndView = null;
		String redirect = request.getQueryString();
		// String redirect = request.getParameter("redirect");
		// params.put("error", error);
		// if (!StringUtils.isEmpty(error))
		// {
		// error = new String(request.getParameter("error").getBytes("iso-8859-1"),
		// "utf-8");
		// }
		if (type.equals("portal"))
		{
			if (StringUtils.isEmpty(redirect))
			{
				redirect = ProjectURLConxtant.PORTAL_INDEX_URL;
			} else
			{
				redirect = redirect.substring("redirect=".length());
			}
			params.put("redirect", redirect);
			modelAndView = new ModelAndView("login", params);
		} else if (type.equals("store"))
		{
			if (StringUtils.isEmpty(redirect))
			{
				redirect = ProjectURLConxtant.STORE_INDEX_URL;
			} else
			{
				redirect = redirect.substring("redirect=".length());
			}
			params.put("redirect", redirect);
			modelAndView = new ModelAndView("seller_login", params);
		}
		return modelAndView;
	}

	@RequestMapping("/{type}/wechat-login")
	public ModelAndView wechatLogin(@PathVariable("type") String type, HttpServletRequest request,
			HttpServletResponse response)
	{
		String sessionId = request.getSession().getId();
		String returnUrl = request.getParameter("redirect");
		String registerUrl = null;
		if (type.equals("portal"))
		{
			if (returnUrl == null)
				returnUrl = ProjectURLConxtant.PORTAL_INDEX_URL;
			registerUrl = ProjectURLConxtant.PORTAL_REGISTER_URL;
		} else if (type.equals("store"))
		{
			if (returnUrl == null)
				returnUrl = ProjectURLConxtant.STORE_INDEX_URL;
			registerUrl = ProjectURLConxtant.STORE_REGISTER_URL;
		}
		String url = "http://clown.s1.natapp.cc/wechat/auth?sessionId=" + sessionId + "&returnUrl=" + returnUrl
				+ "&type=" + type;
		String path = StoreUtils.getQRPath(url, sessionId);
		ModelAndView modelAndView = new ModelAndView("wechat");
		modelAndView.addObject("path", path);
		modelAndView.addObject("sessionId", sessionId);
		modelAndView.addObject("redirect", returnUrl);
		modelAndView.addObject("registerUrl", registerUrl);
		modelAndView.addObject("type", type);
		return modelAndView;
	}

	@RequestMapping("/auth")
	public String code2AccessToken(@RequestParam(name = "returnUrl") String returnUrl, HttpServletRequest request,
			HttpServletResponse response)
	{
		String url = "http://clown.s1.natapp.cc/wechat/qrUserInfo";
		String sessionId = request.getParameter("sessionId");
		returnUrl = returnUrl + "?sessionId=" + sessionId;
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("SESSIONID_MAP");
		if (map == null)
		{
			map = new HashMap<String, String>();
		}
		map.put(sessionId, null);
		request.getServletContext().setAttribute(ServletContextConstant.SESSIONID_MAP, map);
		@SuppressWarnings("deprecation")
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE,
				URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}

	@RequestMapping("/qrUserInfo")
	public ModelAndView qrUserInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String code = request.getParameter("code");
		String returnUrl = request.getParameter("state");
		String sessionId = returnUrl.substring(returnUrl.indexOf("sessionId") + "sessionId".length() + 1);
		try
		{
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			String openId = wxMpOAuth2AccessToken.getOpenId();
			String accessToken = wxMpOAuth2AccessToken.getAccessToken();
			ModelAndView modelAndView = new ModelAndView("redirect:/wechat/wechat-do-login?openid=" + openId
					+ "&sessionId=" + sessionId + "&accessToken=" + accessToken + "&redirect=" + returnUrl);
			return modelAndView;
		} catch (Exception e)
		{
			// logger.error("[微信网页授权]{}", e);
			throw new TmallException();
		}
	}

	/*
	 * 
	 */
	@RequestMapping("wechat-do-login")
	public void wechatDoLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, WxErrorException
	{

		// 这里获取的是手机微信端的cookie
		// Cookie[] cookies = request.getCookies();
		String sessionId = request.getParameter("sessionId");
		String openid = request.getParameter("openid");
		String accessToken = request.getParameter("accessToken");

		Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("SESSIONID_MAP");
		if (map == null || !map.containsKey(sessionId))
		{
			// 这里好像可以做些安全措施
		} else
		{
			WxMpOAuth2AccessToken oAuth2AccessToken = new WxMpOAuth2AccessToken();
			oAuth2AccessToken.setOpenId(openid);
			oAuth2AccessToken.setAccessToken(accessToken);
			WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken, "zh_CN");
			JSONObject jsonObject = JSONObject.fromObject(wxMpUser);
			String value = jsonObject.toString();
			map.put(sessionId, value);
			// 删除图片
			String path = ServiceEnums.PICTURE_QRCODE_LOCATION.getUrl() + "/" + sessionId + ".jpg";
			FileUtils.deleteFile(path, PictureEnums.PICTURE_TYPE_QRCODE.getMsg());
			response.getOutputStream().write("扫码成功".getBytes());
		}
	}

	@RequestMapping(name = "/check-scan")
	public void checkScan(HttpServletRequest request, HttpServletResponse response)
			throws IOException, QueueLoginRangeException
	{
		// 为什么这里的cookies获取不到上一步设置的cookie:就是接收到openid后会向redis和cookie中写入值,为啥获取不到这个cookie
		// 1230 12:56 解决了:原来这里获取到的才是浏览器的cookie,之前如果在扫描的时候设置cookie设置的是客户端(手机)cookie
		// Cookie[] cookies = request.getCookies();
		PrintWriter writer = response.getWriter();
		ResultVo<Object> resultVo = new ResultVo<>();
		String sessionId = request.getParameter("sessionId");
		String type = request.getParameter("type");
		Map<String, String> map = (Map<String, String>) request.getServletContext()
				.getAttribute(ServletContextConstant.SESSIONID_MAP);
		try
		{
			if (map != null && map.containsKey(sessionId))
			{
				String json = map.get(sessionId);
				
				// 从保存在servletcontext中获取微信用户信息,然后查询用户,在根据结果进行深入设计
				WxMpUser wxMpUser = JsonUtils.jsonToPojo(json, WxMpUser.class);
				TmallUser tmallUser = tmallUserService.findOneUserByOpenid(wxMpUser.getOpenId());
				if (tmallUser != null)
				{
					if (type.equals("portal"))
					{
						QueueLoginResultInfo resultInfo = queueLoginService.login(sessionId, tmallUser);
						// 204"21:45这里需要更改,万一进入排队队列的话
						if (resultInfo != null && resultInfo.getCode().equals(QueueLoginService.WAITLOGIN))
						{
							resultVo.setCode(QueueLoginService.WAITLOGIN);
							resultVo.setData(sessionId);
							resultVo.setMsg(String.valueOf(resultInfo.getIndex()));
							String value = JsonUtils.objectToJson(resultVo);
							writer.write(value);
						} else
						{
							CookieUtils.setCookie(response, CookieConstant.PORTAL_USER, sessionId,
									CookieConstant.PORTAL_USER_EXPIRE);
							jedisClient.set(String.format(RedisConstant.PORTAL_USER_PREFIX, sessionId),
									JsonUtils.objectToJson(tmallUser));
							request.getSession().setAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION, tmallUser);
							resultVo.setCode(200);
							String value = JsonUtils.objectToJson(resultVo);
							writer.write(value);
						}
					} else if (type.equals("store"))
					{
						CookieUtils.setCookie(response, CookieConstant.STORE_SELLER, sessionId,
								CookieConstant.STORE_SELLER_EXPIRE);
						String key = String.format(RedisConstant.STORE_SELLER_PREFIX, sessionId);
						jedisClient.set(key, JsonUtils.objectToJson(tmallUser));
						jedisClient.expire(key, RedisConstant.STORE_SELLER_EXPIRE);
						request.getSession().setAttribute(ServletContextConstant.STORE_SELLER_IN_SESSION, tmallUser);
						resultVo.setCode(200);
						String value = JsonUtils.objectToJson(resultVo);
						writer.write(value);
					}
				} else
				{
					String openid = wxMpUser.getOpenId();
					ResultVo<String> notExitResult = new ResultVo<>();
					notExitResult.setCode(ResultEnums.USER_NOT_EXIT.getCode());
					notExitResult.setData(openid);
					String value = JsonUtils.objectToJson(notExitResult);
					System.out.println(value);
					writer.write(value);
				}
			}
		} finally
		{
			writer.flush();
			writer.close();
		}
	}

	@RequestMapping("/wait-login")
	public ModelAndView waitLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam HashMap<String, Object> params)
	{
		ModelAndView modelAndView = new ModelAndView("wait_login", params);
		return modelAndView;
	}

	@RequestMapping("/{type}/log-out")
	public ModelAndView logOut(@PathVariable String type, HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = null;
		HttpSession session = request.getSession();
		modelAndView = new ModelAndView("temp");
		modelAndView.addObject("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
		session.removeAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION);
		if (type.equals("portal"))
		{
			CookieUtils.delCookie(CookieConstant.TMALL_USER, request, response);
		} else if (type.equals("store"))
		{
			CookieUtils.delCookie(CookieConstant.STORE_SELLER, request, response);
		}
		modelAndView.addObject("error", "成功退出");
		return modelAndView;
	}

	@RequestMapping("/{type}/register")
	public ModelAndView register(@PathVariable String type, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> params) throws UnsupportedEncodingException
	{
		ModelAndView modelAndView = null;
		String openid = request.getParameter("openid");
		String errorstString = request.getParameter("error");
		String redirect = request.getParameter("redirect");
		if (type.equals("portal"))
		{
			modelAndView = new ModelAndView("portal_register", params);
			if (StringUtils.isEmpty(openid))
			{
				modelAndView = new ModelAndView("redirect:/wechat/portal/login");
				modelAndView.addObject("error", "请先使用微信扫描注册注册账户");
				return modelAndView;
			}
			if (StringUtils.isEmpty(redirect))
			{
				redirect = ProjectURLConxtant.PORTAL_INDEX_URL;
			}
		} else if (type.equals("store"))
		{
			modelAndView = new ModelAndView("store_register", params);
			if (StringUtils.isEmpty(openid))
			{
				modelAndView = new ModelAndView("redirect:/wechat/store/login");
				modelAndView.addObject("error", "请先使用微信扫描注册注册账户");
				return modelAndView;
			}
			if (StringUtils.isEmpty(redirect))
			{
				redirect = ProjectURLConxtant.STORE_INDEX_URL;
			}
		}
		if (!StringUtils.isEmpty(errorstString))
		{
			// String error = new String(errorstString.getBytes(), "UTF-8");
			modelAndView.addObject("error", errorstString);
		}
		modelAndView.addObject("openid", openid);
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}
}

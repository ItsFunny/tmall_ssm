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
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.SellerEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.enums.TmallUserEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.FileUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.UserDTO;
import com.tmall.model.TmallUser;
import com.tmall.wechat.service.ITmallUserService;
import com.tmall.wechat.service.impl.TmallUserServiceImpl;
import com.tmall.wechat.utils.StoreUtils;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;

/**
* 这个controller包含了其他3个controller共同点(需要访问共同的地方)
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
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException
	{
		Map<String, String> params = new HashMap<>();
//		if (!StringUtils.isEmpty(error))
//		{
//			error = new String(request.getParameter("error").getBytes("iso-8859-1"), "utf-8");
//		}
//		String redirect = request.getParameter("redirect");
//		params.put("error", error);
//		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("login", params);
		return modelAndView;
	}
	@RequestMapping("/wechat-login/{type}")
	public ModelAndView wechatLogin(@PathVariable("type")String type,HttpServletRequest request, HttpServletResponse response)
	{
		UUID sessionId = UUID.randomUUID();
		String returnUrl = request.getParameter("redirect");
		String checkScanUrl=null;
		if(type.equals("portal"))
		{
			if (returnUrl == null)
				returnUrl = ProjectURLConxtant.PORTAL_INDEX_URL;
		}else if(type.equals("store"))
		{
			if (returnUrl == null)
				returnUrl = ProjectURLConxtant.STORE_INDEX_URL;
		}
	
		String url = "http://joker.natapp1.cc:8095/auth?sessionId=" + sessionId + "&returnUrl=" + returnUrl;
		String path = StoreUtils.getQRPath(url, sessionId);
		ModelAndView modelAndView = new ModelAndView("wechat");
		modelAndView.addObject("path", path);
		modelAndView.addObject("sessionId", sessionId);
		modelAndView.addObject("redirect", returnUrl);
		modelAndView.addObject("checkScanUrl",checkScanUrl);
		return modelAndView;
	}
	@RequestMapping("/auth")
	public String code2AccessToken(@RequestParam(name = "returnUrl") String returnUrl, HttpServletRequest request,
			HttpServletResponse response)
	{
		String url = "http://joker.natapp1.cc:8095/qrUserInfo";
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
			ModelAndView modelAndView = new ModelAndView("redirect:/seller/wechat-do-login?openid=" + openId
					+ "&sessionId=" + sessionId + "&accessToken=" + accessToken + "&redirect=" + returnUrl);
			return modelAndView;
		} catch (Exception e)
		{
//			logger.error("[微信网页授权]{}", e);
			throw new TmallException();
		}
	}
	/*
	 * 
	 */
	@RequestMapping("wechat-do-login")
	public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, WxErrorException
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
			//删除图片
			String path=ServiceEnums.PICTURE_QRCODE_LOCATION.getUrl()+"/"+sessionId+".jpg";
			FileUtils.deleteFile(path, PictureEnums.PICTURE_TYPE_QRCODE.getMsg());
			response.getOutputStream().write("扫码成功".getBytes());
		}
	}
	@RequestMapping(name = "/check-scan")
	@ResponseBody
	public ResultVo<?> checkScan(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// 为什么这里的cookies获取不到上一步设置的cookie:就是接收到openid后会向redis和cookie中写入值,为啥获取不到这个cookie
		// 1230 12:56 解决了:原来这里获取到的才是浏览器的cookie,之前如果在扫描的时候设置cookie设置的是客户端(手机)cookie
		// Cookie[] cookies = request.getCookies();
		String sessionId = request.getParameter("sessionId");
		System.out.println(sessionId);
		Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute(ServletContextConstant.SESSIONID_MAP);
		
		if (map != null && map.containsKey(sessionId))
		{
			String json = map.get(sessionId);
			// 从保存在servletcontext中获取微信用户信息,然后查询用户,在根据结果进行深入设计
			WxMpUser wxMpUser = JsonUtils.jsonToPojo(json, WxMpUser.class);
			UserDTO userDTO = tmallUserService.findOneUserByOpenid(wxMpUser.getOpenId());
			if (userDTO == null)
			{
				ResultVo<TmallUser> resultVo = new ResultVo<>();
				map.remove(sessionId);
				map.put(String.format(ServletContextConstant.SELLER_REGISTER_OPENID, wxMpUser.getOpenId()), json);
				// throw new TmallSellerException(ResultEnums.SELLER_NOT_EXIT,
				// new String(("用户不存在,是否要注册为卖家").getBytes(), "UTF-8"), wxMpUser.getOpenId());
				userDTO=new UserDTO();
				userDTO.setOpenid(wxMpUser.getOpenId());
				userDTO.setNickName(wxMpUser.getNickname());
				resultVo.setCode(ResultEnums.USER_NOT_EXIT.getCode());
				String error = "用户不存在,是否要注册";
				resultVo.setMsg(error);
				resultVo.setData(userDTO);
//				JSONObject jsonObject = JSONObject.fromObject(resultVo);
//				System.out.println(jsonObject.toString());
				return resultVo;
			} else if (userDTO.getStatus().equals(TmallUserEnums.ALLOWED.getCode()))
			{
				PrintWriter writer = response.getWriter();
				String value = JsonUtils.objectToJson(userDTO);
				writer.write(value);
				writer.flush();
//				request.getSession().setAttribute(ServletContextConstant.SELLER_IN_SESSION, userDTO);
				// 写入redis
//				JSONObject jsonObject = JSONObject.fromObject(userDTO);
//				String value = jsonObject.toString();
//				jedisClient.set(String.format(RedisConstant.SELLER_INFO_PREFIX, wxMpUser.getOpenId()), value);
//				jedisClient.expire(String.format(RedisConstant.SELLER_INFO_PREFIX, wxMpUser.getOpenId()), 60 * 60 * 24);
				// 写入cookie
//				Cookie cookie = new Cookie(String.format(CookieConstant.SELLER_INFO,sessionId),wxMpUser.getOpenId());
//				cookie.setPath("/");
//				cookie.setMaxAge(60 * 60 * 24);
//				cookie.setValue(sessionId);
//				response.addCookie(cookie);
//				resultVo.setCode(200);
//				resultVo.setMsg("sucess");
			} else
			{
				ResultVo<WxMpUser> resultVo = new ResultVo<>();
				//这里我想要不要外层包裹一个状态码,这样前台js不需要判断很多的if了
				if (userDTO.getStatus().equals(TmallUserEnums.DISABLED.getCode()))
				{
					resultVo.setCode(TmallUserEnums.DISABLED.getCode());
					resultVo.setMsg(TmallUserEnums.DISABLED.getMsg());
				}
				return resultVo;
			}
		}
		return null;
	}
}

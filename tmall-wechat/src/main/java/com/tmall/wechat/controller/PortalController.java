/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午1:26:18
* 
*/
package com.tmall.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.wechat.utils.StoreUtils;

/**
* 
* @author joker 
* @date 创建时间：2018年1月27日 下午1:26:18
*/
@RestController
@RequestMapping("/portal")
public class PortalController
{
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
	/*
	 * 前端微信登陆
	 */
	@RequestMapping("/wechat-login")
	public ModelAndView wechatLogin(HttpServletRequest request, HttpServletResponse response)
	{
		UUID sessionId = UUID.randomUUID();
		String returnUrl = request.getParameter("redirect");
		if (returnUrl == null)
			returnUrl = "http://joker.natapp1.cc:8093/index.html";
		String url = "http://joker.natapp1.cc:8095/auth?sessionId=" + sessionId + "&returnUrl=" + returnUrl;
		String path = StoreUtils.getQRPath(url, sessionId);
		ModelAndView modelAndView = new ModelAndView("wechat");
		modelAndView.addObject("path", path);
		modelAndView.addObject("sessionId", sessionId);
		modelAndView.addObject("redirect", returnUrl);
		return modelAndView;
	}
}

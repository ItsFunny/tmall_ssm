/**
 * 
 */
package com.tmall.store.controller;

import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Controller
public class MyWechatController
{
	@Autowired
	private RestTemplate restTemplate;

	/*
	 * 获取code
	 */
	@RequestMapping("/my-auth")
	public ModelAndView auth(HttpServletRequest request, HttpServletResponse response)
	{
		String redirectUrl = "http://joker.natapp1.cc/seller/my-get-token";
		String wechatUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=wx4573a9e9e4e7f8cf"
				+ "&redirect_uri=" + URLEncoder.encode(redirectUrl) + "&response_type=code"
				+ "&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		ModelAndView modelAndView = new ModelAndView("redirect:" + wechatUrl);
		return modelAndView;
	}

	/*
	 * code换取accesstoken
	 */
	@RequestMapping("my-get-token")
	public ModelAndView getAccessToken(@RequestParam("code") String code)
	{
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
				+ "?appid=wx4573a9e9e4e7f8cf"
				+ "&secret=412a365b2734af961ec492aeb7b857af"
				+ "&code="+code
				+ "&grant_type=authorization_code";
		String vallue = restTemplate.getForObject(url, String.class);
		JSONObject jsonObject=JSONObject.fromObject(vallue);
		String openid =  jsonObject.getString("openid");
		String access_token=jsonObject.getString("access_token");
		System.out.println(code);
		
		String infoUrl="https://api.weixin.qq.com/sns/userinfo"
				+ "?access_token="+access_token
				+ "&openid="+openid
				+ "&lang=zh_CN";
		String string = restTemplate.getForObject(infoUrl, String.class);
		JSONObject jsonObject2=JSONObject.fromObject(string);
		String nickname = jsonObject2.getString("nickname");
		System.out.println(nickname);
		System.out.println(jsonObject2.toString());
		
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}
}

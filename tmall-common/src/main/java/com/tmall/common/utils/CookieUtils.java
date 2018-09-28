/**
 * 
 */
package com.tmall.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class CookieUtils
{
	/**
	 * 
	 */
	public static Cookie getCookie(HttpServletRequest request, String key)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(key))
				{
					return cookie;
				}
			}
		}
		return null;
	}
	public static void setCookie(HttpServletResponse response,String name,String value,Integer expire)
	{
		Cookie cookie=new Cookie(name, value);
		cookie.setMaxAge(expire);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	public static void delCookie(String key,HttpServletRequest request,HttpServletResponse response)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0)
		{
			for (Cookie cookie : cookies)
			{
				String name=cookie.getName();
				if(name.equals(key))
				{
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}else {
			return;
		}
	}
	public static boolean checkIsContainsKey(String key,HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length==0)
		{
			for (Cookie cookie : cookies)
			{
				if(cookie.getName().equals(key))
				{
					return false;
				}
			}
		}else {
			return true;
		}
		return false;
		
	}
	
}

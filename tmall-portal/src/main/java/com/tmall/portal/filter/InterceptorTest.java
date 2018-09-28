/**
*
* @author joker 
* @date 创建时间：2018年4月30日 下午2:20:23
* 
*/
package com.tmall.portal.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
* 
* @author joker 
* @date 创建时间：2018年4月30日 下午2:20:23
*/
public class InterceptorTest implements HandlerInterceptor
{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		// TODO Auto-generated method stub
		
	}

}

/**
*
* @author joker 
* @date 创建时间：2018年5月20日 上午11:19:22
* 
*/
package com.rebuildtmall.tmall_sso_test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

/**
* 
* @author joker 
* @date 创建时间：2018年5月20日 上午11:19:22
*/
public  class LoginFilter implements Filter
{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		System.out.println("---");
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		Object isAuth = session.getAttribute("isAuth");
		String token=req.getParameter("token");
		System.out.println(session.getId());
		if(!StringUtils.isEmpty(token))
		{
			
		}else {
			resp.sendRedirect("http://localhost:8000/login"+"?redirectUrl="+"http://localhost:8002/test?");
		}
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}
	
}
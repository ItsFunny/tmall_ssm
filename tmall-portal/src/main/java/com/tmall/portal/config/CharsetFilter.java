/**
 * 
 */
package com.tmall.portal.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Administrator
 *
 */
public class CharsetFilter implements Filter
{

	private String encoding = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		if (encoding != null)
		{
			// 设置request编码格式
			request.setCharacterEncoding(encoding);
			// 设置response编码格式
			response.setContentType("text/html;charset=" + encoding);
		}
		// 传递给下个过滤器
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
		encoding = null;
	}

}

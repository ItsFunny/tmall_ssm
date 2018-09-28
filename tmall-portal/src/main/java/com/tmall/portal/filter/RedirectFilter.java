/**
 * 
 */
package com.tmall.portal.filter;

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
public class RedirectFilter implements Filter
{
	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException
	{
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
}

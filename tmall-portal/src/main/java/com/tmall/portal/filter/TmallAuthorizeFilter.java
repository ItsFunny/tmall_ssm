///**
// * 
// */
//package com.tmall.portal.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import com.tmall.common.constant.CookieConstant;
//import com.tmall.common.constant.RedisConstant;
//import com.tmall.common.exception.TmallAuthorizeException;
//import com.tmall.portal.dao.impl.JedisClient;
//import com.tmall.portal.utils.PortalUtils;
//
///**
// * @author Administrator
// *
// */
//public class TmallAuthorizeFilter implements Filter
//{
//
//	@Autowired
//	private JedisClient jedisClient;
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException
//	{
//	}
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException
//	{
//		HttpServletRequest request2=(HttpServletRequest) request;
//		Cookie cookie = PortalUtils.getCookie(request2, CookieConstant.TOKEN);
//		if(cookie==null)
//		{
//			throw new TmallAuthorizeException();
//		}
//		String redisValue = jedisClient.get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
//		if(StringUtils.isEmpty(redisValue))
//		{
//			throw new TmallAuthorizeException();
//		}
//		chain.doFilter(request2, response);
//	}
//	@Override
//	public void destroy()
//	{
//	}
//}

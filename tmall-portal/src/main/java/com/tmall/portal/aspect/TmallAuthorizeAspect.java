///**
// * 
// */
//package com.tmall.portal.aspect;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.tmall.common.constant.CookieConstant;
//import com.tmall.common.constant.RedisConstant;
//import com.tmall.common.exception.TmallAuthorizeException;
//import com.tmall.portal.dao.impl.JedisClient;
//import com.tmall.portal.utils.PortalUtils;
//
///**
// * @author joker
// * @version 创建时间：2018年1月12日 下午12:32:00
// * 
// */
//@Aspect
//@Component
//public class TmallAuthorizeAspect
//{
//	private Logger logger = org.slf4j.LoggerFactory.getLogger(TmallAuthorizeAspect.class);
//	@Autowired
//	private JedisClient jedisClient;
//
//
//	@Pointcut("execution(* com.tmall.portal.controller.*.*(..))")
//	public void overRall()
//	{
//	}
//	@Pointcut("execution (* com.tmall.portal.controller.StoreController.*(..))")
//	public void storeFunction() {}
//
//	/**
//	 * 显示所有页面总共访问次数
//	 * @author joker 
//	 * @date 创建时间：2018年1月13日 下午4:09:01
//	 */
//	@Before("overRall()")
//	public void addRecords()
//	{
//		String json = jedisClient.get(RedisConstant.PORTAL_TOTAL_COUNT);
//		if (StringUtils.isEmpty(json))
//		{
//			jedisClient.set(RedisConstant.PORTAL_TOTAL_COUNT, String.valueOf(1L));
//		} else
//		{
//			Long count = Long.parseLong(jedisClient.get(RedisConstant.PORTAL_TOTAL_COUNT));
//			synchronized (this)
//			{
//				count++;
//			}
//			jedisClient.set(RedisConstant.PORTAL_TOTAL_COUNT, count.toString());
//		}
//	}
//	/**
//	 * 添加store portal的访问记录
//	 * @author joker 
//	 * @date 创建时间：2018年1月13日 下午4:46:55
//	 */
//	@Before("storeFunction()")
//	public void addStoreRecords()
//	{
//		ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = requestAttributes.getRequest();
//		String url = request.getRequestURL().toString();
//		String storeId = url.substring(url.lastIndexOf('/')+1,url.lastIndexOf('/')+2);
//		String key=String.format(RedisConstant.PORTAL_STORE_TOTAL_COUNT, ":"+storeId);
//		String json = jedisClient.get(key);
//		if(StringUtils.isEmpty(json))
//		{
//			jedisClient.set(key,String.valueOf(1L));
//		}else {
//			Long count=Long.parseLong(json);
//			count++;
//			jedisClient.set(key, count.toString());
//		}
//	}
//}

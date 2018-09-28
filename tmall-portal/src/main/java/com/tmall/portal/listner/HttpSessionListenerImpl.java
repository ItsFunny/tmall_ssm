///**
// * 
// */
//package com.tmall.portal.listner;
//
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.http.HttpSessionAttributeListener;
//import javax.servlet.http.HttpSessionBindingEvent;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//import org.springframework.util.StringUtils;
//
//import com.tmall.common.constant.RedisConstant;
//import com.tmall.common.constant.ServletContextConstant;
//import com.tmall.common.utils.ConverterUtils;
//import com.tmall.common.utils.JsonUtils;
//import com.tmall.model.PortalUserConfig;
//import com.tmall.portal.TmallThread.BackThreadLogin;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
///**
// * 处理在线人数,至于访问次数在aop中设置
// * 
// * @author joker
// * @date 创建时间：2018年1月12日 下午5:18:35
// * 
// */
//public class HttpSessionListenerImpl
//		implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener
//{
//
//	// private static Map<String, String> PortalUserConfig.maxCountMap = new
//	// HashMap<>();
//	//
//	// private static Map<String, UserDTO>PortalUserConfig.onLineCountMap=new
//	// HashMap<>();
//	// static
//	// {
//	// PortalUserConfig.maxCountMap.put(ServletContextConstant.PORTAL_MAX_COUNT,
//	// String.valueOf(1L));
//	// PortalUserConfig.maxCountMap.put(RedisConstant.DATE_OF_MAX_COUNT,
//	// ConverterUtils.date2SimpleDateString(new Date()));
//	// }
//	//
//	@SuppressWarnings("unused")
//	@Override
//	public void sessionCreated(HttpSessionEvent se)
//	{
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent se)
//	{
//		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
//		Jedis jedis = jedisPool.getResource();
//
//		String onLineCountString = jedis.get(ServletContextConstant.PORTAL_ONLINE_COUNT);
//		Long onLineCount = onLineCountString == null ? 0L : Long.parseLong(onLineCountString);
//		jedis.set(ServletContextConstant.PORTAL_ONLINE_COUNT, (--onLineCount).toString());
//		jedis.close();
//	}
//
//	@Override
//	public void contextInitialized(ServletContextEvent sce)
//	{
//		// 启动后台等待用户线程
////		 new Thread(new BackThreadLogin()).start();
//		ExecutorService executorService = Executors.newFixedThreadPool(5);
//		executorService.execute(new BackThreadLogin());
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent sce)
//	{
//		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
//		Jedis jedis = jedisPool.getResource();
//		String t = String.valueOf(0L);
//		jedis.set(ServletContextConstant.PORTAL_ONLINE_COUNT, t);
//		jedis.set(RedisConstant.PORTAL_ONLINE_USER, null);
//		jedis.close();
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void attributeAdded(HttpSessionBindingEvent event)
//	{
//		String name = event.getName();
//		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
//		Jedis jedis = jedisPool.getResource();
//		try
//		{
//			if (name.equals(ServletContextConstant.PORTAL_USER_IN_SESSION))
//			{
//				// 采用原子操作来替代synchorized
//				// AtomicInteger onLineCountAtom = new
//				// AtomicInteger(PortalUserConfig.onLineCountMap.size());
//				AtomicInteger onLineCountAtom = new AtomicInteger(PortalUserConfig.OnLineUserQueue.size());
//				Integer onLineCount = onLineCountAtom.get();
//				String json = jedis.get(ServletContextConstant.PORTAL_MAX_COUNT);
//				if (StringUtils.isEmpty(json))
//				{
//					jedis.set(RedisConstant.PORTAL_MAX_COUNT,
//							String.valueOf(PortalUserConfig.getQueueSize(PortalUserConfig.OnLineUserQueue)));
//				} else
//				{
//					PortalUserConfig.maxCountMap = JsonUtils.jsonToPojo(json, Map.class);
//					Long maxCount = Long.parseLong(PortalUserConfig.maxCountMap.get(RedisConstant.PORTAL_MAX_COUNT));
//					if (onLineCount > maxCount)
//					{
//						PortalUserConfig.maxCountMap.put(RedisConstant.PORTAL_MAX_COUNT, onLineCount.toString());
//						PortalUserConfig.maxCountMap.put(RedisConstant.PORTAL_DATE_OF_MAX_COUNT,
//								ConverterUtils.date2SimpleDateString(new Date()));
//						jedis.set(ServletContextConstant.PORTAL_MAX_COUNT,
//								JsonUtils.objectToJson(PortalUserConfig.maxCountMap));
//					}
//				}
//				jedis.set(RedisConstant.PORTAL_ONLINE_USER, JsonUtils.objectToJson(PortalUserConfig.OnLineUserQueue));
//				jedis.set(RedisConstant.PORTAL_ONLINE_COUNT, onLineCount.toString());
//			}
//		} finally
//		{
//			jedis.close();
//		}
//
//	}
//
//	@Override
//	public void attributeRemoved(HttpSessionBindingEvent event)
//	{
//		String name = event.getName();
//		if (name.equals(ServletContextConstant.PORTAL_USER_IN_SESSION))
//		{
////			JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
//			// String sessionId = event.getSession().getId();
////			UserDTO userDTO = (UserDTO) event.getSession().getAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION);
////			String userId = userDTO.getUserId();
////			Jedis jedis = jedisPool.getResource();
//			// UserDTO userDTO = (UserDTO)
//			// event.getSession().getAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION);
//			// String json2 = jedis.get(RedisConstant.PORTAL_ONLINE_USER);
//			// if (StringUtils.isEmpty(json2))
//			// {
//			// }
////			PortalUserConfig.onLineCountMap.remove(userId);
////			String value = JsonUtils.objectToJson(PortalUserConfig.onLineCountMap);
////			jedis.set(RedisConstant.PORTAL_ONLINE_USER, value);   
////			jedis.close();
//		}
//		event.getSession().invalidate();
//	}
//
//	@Override
//	public void attributeReplaced(HttpSessionBindingEvent event)
//	{
//
//	}
//}

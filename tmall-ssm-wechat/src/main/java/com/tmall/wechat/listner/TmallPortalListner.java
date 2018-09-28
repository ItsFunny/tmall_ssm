/**
*
* @author joker 
* @date 创建时间：2018年2月1日 下午9:47:02
* 
*/
package com.tmall.wechat.listner;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.util.StringUtils;

import com.joker.library.QueueLogin.QueueConfig;
import com.joker.library.QueueLogin.service.BackLoginThread;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dto.UserDTO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author joker
 * @date 创建时间：2018年2月1日 下午9:47:02
 */
public class TmallPortalListner implements ServletContextListener, HttpSessionAttributeListener,HttpSessionListener
{

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		QueueConfig<UserDTO> config = QueueConfig.getQueueConfig();
		ExecutorService executorService = Executors.newFixedThreadPool(5,new DaemonThreadFactory());
		executorService.execute(new BackLoginThread<>(config));
		JedisPool jedisPool = new JedisPool("192.168.1.103", 6379);
		Jedis jedis = jedisPool.getResource();
		try
		{
			jedis.del(RedisConstant.PORTAL_ONLINE_COUNT);
			jedis.del(RedisConstant.PORTAL_ONLINE_USER);
		} finally
		{
			jedis.close();
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event)
	{
		String name = event.getName();
		JedisPool jedisPool = new JedisPool("192.168.1.103", 6379);
		Jedis jedis = jedisPool.getResource();
		Integer onLineCount = null;
		try
		{
			if (name.equals(ServletContextConstant.PORTAL_USER_IN_SESSION))
			{
				QueueConfig<UserDTO> config = QueueConfig.getQueueConfig();
				String count = jedis.get(RedisConstant.PORTAL_ONLINE_COUNT);
				if (!StringUtils.isEmpty(count))
				{
					onLineCount = Integer.parseInt(count);
					AtomicInteger integer = new AtomicInteger(onLineCount);
					onLineCount = integer.incrementAndGet();
					// jedis.incr(RedisConstant.PORTAL_ONLINE_COUNT);
					jedis.set(RedisConstant.PORTAL_ONLINE_COUNT, onLineCount.toString());
				} else
				{
					jedis.set(RedisConstant.PORTAL_ONLINE_COUNT, String.valueOf(1));
					onLineCount = 1;
				}
				String json = jedis.get(RedisConstant.PORTAL_MAX_ONLINE_RECORD);
				if (StringUtils.isEmpty(json)||json.length()==2)
				{
					Map<String, String> maxCountMap = config.getMaxCountMap();
					maxCountMap.put(RedisConstant.PORTAL_MAX_COUNT, onLineCount.toString());
					maxCountMap.put(RedisConstant.PORTAL_DATE_OF_MAX_COUNT,
							ConverterUtils.date2SimpleDateString(new Date()));
					jedis.set(RedisConstant.PORTAL_MAX_ONLINE_RECORD,
							JsonUtils.objectToJson(maxCountMap));
				} else
				{
					Map<String, String> maxCountMap = config.getMaxCountMap();
					maxCountMap = JsonUtils.jsonToPojo(json, Map.class);
					String maxCountString = maxCountMap.get(RedisConstant.PORTAL_MAX_COUNT);
					Long maxCount = null;
					if (!StringUtils.isEmpty(maxCountString))
					{
						maxCount = Long.parseLong(maxCountString);
						if (onLineCount >= maxCount)
						{
							maxCountMap.put(RedisConstant.PORTAL_MAX_COUNT, onLineCount.toString());
							maxCountMap.put(RedisConstant.PORTAL_DATE_OF_MAX_COUNT,
									ConverterUtils.date2SimpleDateString(new Date()));
							jedis.set(RedisConstant.PORTAL_MAX_ONLINE_RECORD,
									JsonUtils.objectToJson(maxCountMap));
						}
					} else
					{
						maxCountMap.put(RedisConstant.PORTAL_MAX_COUNT, onLineCount.toString());
						maxCountMap.put(RedisConstant.PORTAL_DATE_OF_MAX_COUNT,
								ConverterUtils.date2SimpleDateString(new Date()));
						jedis.set(RedisConstant.PORTAL_MAX_ONLINE_RECORD,
								JsonUtils.objectToJson(maxCountMap));
					}
				}
				jedis.set(RedisConstant.PORTAL_ONLINE_USER, JsonUtils.objectToJson(config.getOnLineUserQueue()));
			}
		} finally
		{
			jedis.close();
		}
	}
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event)
	{
		String name = event.getName();
		UserDTO userDTO= (UserDTO) event.getValue();
		JedisPool jedisPool = new JedisPool("192.168.1.103", 6379);
		Jedis jedis = jedisPool.getResource();
		if(name.equals(ServletContextConstant.PORTAL_USER_IN_SESSION))
		{
			try
			{
				jedis.decr(RedisConstant.PORTAL_ONLINE_COUNT);
				QueueConfig<UserDTO> config = (QueueConfig<UserDTO>) QueueConfig.getQueueconfig();
				LinkedBlockingQueue<UserDTO> onLineUserQueue = config.getOnLineUserQueue();
				 onLineUserQueue.remove(userDTO);
				jedis.set(RedisConstant.PORTAL_ONLINE_USER, JsonUtils.List2Json(onLineUserQueue));
			} finally
			{
				jedis.close();
			}
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event)
	{
		String name = event.getName();
		JedisPool jedisPool = new JedisPool("192.168.1.103", 6379);
		Jedis jedis = jedisPool.getResource();
		try
		{
			if (name.equals(ServletContextConstant.PORTAL_USER_IN_SESSION))
			{
				QueueConfig<UserDTO> config = (QueueConfig<UserDTO>) QueueConfig.getQueueconfig();
				LinkedBlockingQueue<UserDTO> onLineUserQueue = config.getOnLineUserQueue();
				jedis.set(RedisConstant.PORTAL_ONLINE_USER, JsonUtils.List2Json(onLineUserQueue));
			}
		} finally
		{
			jedis.close();
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		HttpSession session = se.getSession();
		String sessionId = session.getId();
		QueueConfig<UserDTO> config = (QueueConfig<UserDTO>) QueueConfig.getQueueconfig();
		LinkedBlockingQueue<UserDTO> onLineUserQueue = config.getOnLineUserQueue();
		for (UserDTO userDTO : onLineUserQueue)
		{
			if(userDTO.getSessionId().equals(sessionId))
			{
				session.removeAttribute(ServletContextConstant.PORTAL_USER_IN_SESSION);
				break;
			}
		}
	}
}

/**
* @author joker 
* @date 创建时间：2018年1月15日 下午3:47:22
* 
*/
package com.tmall.model;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import com.tmall.dto.UserDTO;

/**
 * 缓存前端在线人员的信息
 * 
 * @author joker
 * @date 创建时间：2018年1月15日 下午3:47:22
 * 
 */
public class PortalUserConfig
{
	volatile public static boolean isBackThreadRun=false;
	/**
	* 存放着最大在线人数,及其最大在线人数的时刻
	* @author joker 
	* @date 创建时间：2018年1月24日 下午5:25:42
	*/
	public static Map<String, String> maxCountMap = new ConcurrentHashMap<>();
	
	
//	public static Map<String, UserDTO> onLineCountMap = new ConcurrentHashMap<>();
	//key为用户id:独一无二并且不随操作环境而改变
	//125:打算去除,用户直接存放在queue中,用下面的代替,存放了key
//	public static Map<String, UserDTO>waitUserMap=new LinkedHashMap<String, UserDTO>();
	
	
	
	public static Map<String,UserDTO>waitUserKeyMap=new LinkedHashMap<>();
	public static Object lock=new Object();
	
	/**
	* 存放等待队列用户中的sessionId
	* @author joker 
	* @date 创建时间：2018年1月22日 下午3:00:19
	* 
	*/
	public static LinkedBlockingDeque<UserDTO>WaitLoginUserQueue;
	
	public static BlockingQueue<UserDTO>OnLineUserQueue;
	
//	@Value("${MAX_ONLINE_USER_COUNT}")
	public  static Integer MAX_ONLINE_USER_COUNT=1;
	
//	@Value("${MAX_WAIT_LOGIN_USER_COUNT}")
	public  static Integer MAX_WAIT_LOGIN_USER_COUNT=2;
	
	static
	{
		WaitLoginUserQueue=new LinkedBlockingDeque<>(MAX_WAIT_LOGIN_USER_COUNT);
		OnLineUserQueue=new LinkedBlockingDeque<>(MAX_ONLINE_USER_COUNT);
	}
	
	public synchronized static<T> Integer getQueueSize(Collection<T>queue)
	{
		return queue.size();
	}

	
}

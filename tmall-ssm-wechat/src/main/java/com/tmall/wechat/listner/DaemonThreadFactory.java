/**
*
* @author joker 
* @date 创建时间：2018年2月23日 下午3:15:02
* 
*/
package com.tmall.wechat.listner;

import java.util.concurrent.ThreadFactory;

/**
* 
* @author joker 
* @date 创建时间：2018年2月23日 下午3:15:02
*/
public class DaemonThreadFactory implements ThreadFactory
{

	@Override
	public Thread newThread(Runnable r)
	{
		Thread thread=newThread(r);
		thread.setDaemon(true);
		return thread;
	}

}

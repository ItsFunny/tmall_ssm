///**
//*
//* @author joker 
//* @date 创建时间：2018年1月22日 下午3:39:26
//* 
//*/
//package com.tmall.portal.TmallThread;
//
//import java.util.Date;
//
//import com.tmall.common.utils.KeyUtils;
//import com.tmall.dto.UserDTO;
//import com.tmall.model.PortalUserConfig;
//
///**
// * 等待队列中的自动登陆
// * 
// * @author joker
// * @date 创建时间：2018年1月22日 下午3:39:26
// * 
// */
//public class WaitUserLogin implements Runnable
//{
//	private Object obj=new Object();
//	@Override
//	public void run()
//	{
//		Thread thread = new Thread(new Init());
//		thread.start();
//		Thread[] threads = new Thread[100];
//		for (int i = 0; i < 100; i++)
//		{
//			threads[i] = new Thread(new Init());
//			threads[i].start();
//		}
//		Thread[] threads2 = new Thread[100];
//		for (int i = 0; i < 100; i++)
//		{
//			threads2[i] = new Thread(new InWait());
//			threads2[i].start();
//		}
//		Thread logOut = new Thread(new LogOut());
//		logOut.start();
//		// try
//		// {
//		// thread.join();
//		// } catch (InterruptedException e1)
//		// {
//		// // TODO Auto-generated catch block
//		// e1.printStackTrace();
//		// }
//		while (!Thread.currentThread().isInterrupted())
//		{
//			try
//			{
//				Integer size = PortalUserConfig.getQueueSize(PortalUserConfig.OnLineUserQueue);
//				// if(size<PortalUserConfig.MAX_ONLINE_USER_COUNT)
//				// {
//				synchronized (PortalUserConfig.lock)
//				{
//					String sessionId = PortalUserConfig.WaitLoginUserQueue.take();
////					if (sessionId != null)
////					{
//						System.out.println("取到了" + sessionId+new Date());
//						// 这里会发生重排序吗,当下一步需要上一步结果的时候
//						UserDTO userDTO = PortalUserConfig.waitUserMap.get(sessionId);
//						// if(userDTO!=null)
//						// {
//						System.out.println("取到了" + userDTO+new Date());
//						PortalUserConfig.OnLineUserQueue.put(userDTO);
//						System.out.println(userDTO + "登陆了```````````"+new Date());
//						PortalUserConfig.waitUserMap.remove(sessionId);
//						// }
////					}
//						PortalUserConfig.lock.notifyAll();
//				}
//				
////				Thread.sleep(2000);
//				// }
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
//
//	class Init implements Runnable
//	{
//
//		@Override
//		public void run()
//		{
//			try
//			{
//				UserDTO userDTO = new UserDTO();
//				// UserDTO userDTE
//				PortalUserConfig.OnLineUserQueue.put(userDTO);
//				System.out.println(userDTO + "登陆了"+new Date());
//
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
//
//	class InWait implements Runnable
//	{
//
//		@Override
//		public void run()
//		{
//			UserDTO userDTO = new UserDTO();
//			String ss = KeyUtils.generateRandomSequence();
//			try
//			{
//				synchronized (PortalUserConfig.lock)
//				{
//					boolean offer = PortalUserConfig.WaitLoginUserQueue.offer(ss);
//					if(offer)
//					{
//						System.out.println(userDTO + "进入等待队列"+new Date());
//						PortalUserConfig.waitUserMap.put(ss, userDTO);
//					}else {
//						System.out.println("等待队列人太多了");
//					}
//					PortalUserConfig.lock.wait();
//				}
//			} catch (InterruptedException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	class LogOut implements Runnable
//	{
//
//		@Override
//		public void run()
//		{
//			while (!Thread.currentThread().isInterrupted())
//			{
//				try
//				{
////					Thread.sleep(1000);
//					UserDTO user = PortalUserConfig.OnLineUserQueue.take();
//					System.out.println(user + "退出了"+new Date());
//				} catch (InterruptedException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	public static void main(String[] args)
//	{
//		new Thread(new WaitUserLogin()).start();
//	}
//
//}

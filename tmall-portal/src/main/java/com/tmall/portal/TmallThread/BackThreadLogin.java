/**
*
* @author joker 
* @date 创建时间：2018年1月22日 下午5:42:47
* 
*/
package com.tmall.portal.TmallThread;

import com.tmall.dto.UserDTO;
import com.tmall.model.PortalUserConfig;

/**
 *
 * @author joker
 * @date 创建时间：2018年1月22日 下午5:42:47
 * 
 */
public class BackThreadLogin implements Runnable
{

	@Override
	public void run()
	{
		// 确保只有一个组合占有这个锁(因为排队登陆是单线程的)
		while (true)
		{
			if (PortalUserConfig.isBackThreadRun)
			{
				synchronized (this)
				{
					try
					{
						UserDTO userDTO = PortalUserConfig.WaitLoginUserQueue.getFirst();
						if(userDTO!=null)
						{
							System.out.println("后台线程取到了userDTO:" + userDTO);
							PortalUserConfig.OnLineUserQueue.put(userDTO);
							System.out.println(userDTO + "在后台线程中登陆了");
							PortalUserConfig.WaitLoginUserQueue.remove(userDTO);
						}
						
						// }
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					// 125: 16:52 更改为offer,不改为offer的话会跟前端登陆一直冲突(获取不到 锁)
					// boolean offer = PortalUserConfig.OnLineUserQueue.offer(userDTO);
					// if (offer)
					// {
					// System.out.println(userDTO + "在后台线程中登陆了");
					// 因为取不到request,所以删除用户在排队队列中的操作交给check-index那里去做
					// ServletRequestAttributes attributes = (ServletRequestAttributes)
					// RequestContextHolder
					// .getRequestAttributes();
					// attributes.getRequest().getSession().setAttribute(ServletContextConstant.USER_IN_SESSION,
					// userDTO);
					// PortalUserConfig.waitUserMap.remove(userId);
					// PortalUserConfig.WaitLoginUserQueue.remove(userId);
					// }
				}
			}

		}
	}

}

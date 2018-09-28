///**
//*
//* @author joker 
//* @date 创建时间：2018年1月21日 下午7:11:13
//* 
//*/
//package com.tmall.portal.TmallThread;
//
//import java.util.concurrent.Callable;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.tmall.common.constant.ServletContextConstant;
//import com.tmall.common.enums.ResultEnums;
//import com.tmall.common.exception.TmallPortalException;
//import com.tmall.common.utils.JsonUtils;
//import com.tmall.dto.UserDTO;
//import com.tmall.model.PortalUserConfig;
//
///**
// *
// * @author joker
// * @date 创建时间：2018年1月21日 下午7:11:13
// * 
// */
//public class Login implements Callable<String>
//{
//	private UserDTO userDTO;
//	public Login(UserDTO userDTO)
//	{
//		this.userDTO=userDTO;
//	}
//
//	@Override
//	public String call() throws Exception
//	{
//		try
//		{
//			if (!PortalUserConfig.WaitLoginUserQueue.isEmpty())
//			{
//				enterOnLineUserQueue(userDTO);
//			} else
//			{
//				enterWaitUserQueue(userDTO);
//			}
//		} catch (TmallPortalException e)
//		{
//			// return e.getMessage();
//		}
//		return null;
//	}
//
////	@Override
////	public void run()
////	{
////		try
////		{
////			if (!PortalUserConfig.WaitLoginUserQueue.isEmpty())
////			{
////				enterOnLineUserQueue(userDTO);
////			} else
////			{
////				enterWaitUserQueue(userDTO);
////			}
////		} catch (TmallPortalException e)
////		{
////			// return e.getMessage();
////		}
////	}
//
//	public String enterWaitUserQueue(UserDTO userDTO) throws TmallPortalException
//	{
//		Integer size = PortalUserConfig.getQueueSize(PortalUserConfig.WaitLoginUserQueue);
//		if (size < PortalUserConfig.MAX_WAIT_LOGIN_USER_COUNT)
//		{
//			boolean offer = PortalUserConfig.WaitLoginUserQueue.offer(userDTO.getSessionId());
//			if (offer)
//			{
//				System.out.println("进入登陆队列");
//			} else
//			{
//				System.out.println("登陆失败");
//				throw new TmallPortalException(ResultEnums.USER_WAIT_LOGIN_OUT_OF_RANGE);
//			}
//		} else
//		{
//			throw new TmallPortalException(ResultEnums.USER_WAIT_LOGIN_OUT_OF_RANGE);
//		}
//
//		return null;
//	}
//
//	public void enterOnLineUserQueue(UserDTO userDTO) throws TmallPortalException
//	{
//		Integer size = PortalUserConfig.getQueueSize(PortalUserConfig.OnLineUserQueue);
//		if (size < PortalUserConfig.MAX_ONLINE_USER_COUNT)
//		{
//			boolean offer = PortalUserConfig.OnLineUserQueue.offer(userDTO);
//			if (offer)
//			{
//				System.out.println("登陆成功");
//				setSessionAttribute(userDTO);
//			} else
//			{
//				System.out.println("登陆失败,调入等待队列");
//				enterWaitUserQueue(userDTO);                                                                             
//			}
//		} else
//		{
//			enterWaitUserQueue(userDTO);
//		}
//	}
//	public void setSessionAttribute(UserDTO userDTO)
//	{
//		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		HttpSession session = request.getSession();
//		String value=JsonUtils.objectToJson(userDTO);
//		session.setAttribute(ServletContextConstant.USER_IN_SESSION, value);
//	}
//
//}

/**
 * 
 */
package com.tmall.portal.exception_handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallAuthorizeException;
import com.tmall.common.exception.TmallIllegalException;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.exception.TmallThirdPartException;

/**
 * @author Administrator
 *
 */
@ControllerAdvice // 
public class TmallExceptionHandler
{
	@ExceptionHandler(value=TmallThirdPartException.class)
	public ModelAndView handerThirdPartException(TmallThirdPartException exception)
	{
		ModelAndView modelAndView=null;
		if(exception.getCode().equals(ResultEnums.USER_IS_NOT_LOGIN.getCode()))
		{
			String redirect=exception.getParam();
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("error", "请先登录");
			modelAndView.addObject("redirect", ProjectURLConxtant.PORTAL_LOGIN_URL+"?redirect="+redirect);
		}
		return modelAndView;
	}
	@ExceptionHandler(value = TmallPortalException.class)
	public ModelAndView handlerTmallPortalException(TmallPortalException exception)
	{
		// ServletRequestAttributes attributes=(ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes();
		// String sessionId = attributes.getRequest().getSession().getId();
		ModelAndView modelAndView = null;
		if (exception.getCode().equals(ResultEnums.USER_NOT_EXIT.getCode()))
		{
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("error", "用户不存在,请先登陆");
			modelAndView.addObject("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
		} else if (exception.getCode().equals(ResultEnums.USER_WAIT_LOGIN_OUT_OF_RANGE.getCode()))
		{
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("error", "当前等待登陆人数过多,请稍后在试");
			modelAndView.addObject("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
		}
		return modelAndView;
	}

	@ExceptionHandler(value = IllegalStateException.class)
	public ModelAndView handlerQueueInterruptException()
	{
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "当前等待登陆人数过多,请稍后在试");
		modelAndView.addObject("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
		return modelAndView;
	}

	// 拦截登陆异常
	@ExceptionHandler(value = TmallAuthorizeException.class)
	public ModelAndView handlerTmallAuthorizeException()
	{
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String redirect = request.getParameter("redirect");
		String error = request.getParameter("error");
		ModelAndView modelAndView = new ModelAndView("redirect:"+ProjectURLConxtant.PORTAL_LOGIN_URL);
		if (!StringUtils.isEmpty(redirect))
		{
			modelAndView.addObject("redirect", request.getRequestURL());
		}
		if (!StringUtils.isEmpty(error))
		{
			modelAndView.addObject("error", error);
		}
		return modelAndView;
	}

	@ExceptionHandler(value = TmallIllegalException.class)
	public ModelAndView handleTmallIllegalException(TmallIllegalException exception)
	{
		ModelAndView modelAndView = null;
		Map<String, Object> params = new HashMap<String, Object>();
		// ServletRequestAttributes attributes=(ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes();
		// HttpServletRequest request = attributes.getRequest();
		if (exception.getCode().equals(ResultEnums.USER_ILLEGAL_OPERATION.getCode()))
		{
			params.put("error", ResultEnums.USER_ILLEGAL_OPERATION.getMsg());
			params.put("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
			modelAndView = new ModelAndView("temp", params);
		} else if (exception.getCode().equals(ResultEnums.USER_TRY_REPEIT_LOGIN.getCode()))
		{
			params.put("error", "请不要重复登陆");
			params.put("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
			modelAndView = new ModelAndView("temp", params);
		} else if (exception.getCode().equals(ResultEnums.USER_IS_DISABLED.getCode()))
		{
			params.put("error", "用户已被禁止登陆");
			params.put("redirect", ProjectURLConxtant.PORTAL_INDEX_URL);
			modelAndView = new ModelAndView("temp", params);
		}
		return modelAndView;
	}
}

/**
 * 
 */
package com.tmall.store.ExceptionHandler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallIllegalException;
import com.tmall.common.exception.TmallThirdPartException;

/**
 * @author Administrator
 *
 */
@ControllerAdvice
public class TmallSellerExceotionHandler
{
	@ExceptionHandler(TmallIllegalException.class)
	public ModelAndView handlerIllegalOperation(TmallIllegalException exception)
	{
		ModelAndView modelAndView=null;
		if(exception.getCode().equals(ResultEnums.SELLER_DONT_HAVE_AUTH_OF_THE_STORE.getCode()))
		{
			modelAndView=new ModelAndView("400");
		}
		return modelAndView;
	}
	@ExceptionHandler(TmallThirdPartException.class)
	public ModelAndView handlerThirdPartException(TmallThirdPartException exception)
	{
		ModelAndView modelAndView=null;
		if(exception.getCode().equals(ResultEnums.USER_IS_NOT_LOGIN.getCode()))
		{
			modelAndView=new ModelAndView("redirect:"+ProjectURLConxtant.STORE_LOGIN_URL);
			modelAndView.addObject("error","请先登录");
		}
		return modelAndView;
	}
}

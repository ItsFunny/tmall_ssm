/**
*
* @author joker 
* @date 创建时间：2018年2月3日 下午3:30:26
* 
*/
package com.tmall.wechat.ExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.joker.library.QueueLogin.Exception.QueueLoginRangeException;
import com.tmall.common.constant.ProjectURLConxtant;

/**
* 登陆注册模块全局异常处理
* @author joker 
* @date 创建时间：2018年2月3日 下午3:30:26
*/
@ControllerAdvice
public class LoginModuleExceptionHandler
{
	@ExceptionHandler(QueueLoginRangeException.class)
	public ModelAndView handlerQueueLoginOutOfRange(QueueLoginRangeException exception)
	{
		String msg=exception.getMessage();
		ModelAndView modelAndView=new ModelAndView("temp");
		modelAndView.addObject("error",msg);
		modelAndView.addObject("redirect",ProjectURLConxtant.PORTAL_INDEX_URL);
		return modelAndView;
	}

}

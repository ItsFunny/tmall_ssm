/**
*
* @author joker 
* @date 创建时间：2018年1月16日 下午9:27:43
* 
*/
package com.tmall.portal.config;

import org.springframework.web.servlet.DispatcherServlet;


/**
*
* @author joker 
* @date 创建时间：2018年1月16日 下午9:27:43
* 
*/
public class TmallServlet extends DispatcherServlet
{

	/**
	*
	* @author joker 
	* @date 创建时间：2018年1月16日 下午9:27:57
	* 
	*/
	private static final long serialVersionUID = 6857983001411576634L;

	@Override
	public void destroy()
	{
		System.out.println("-----------------------------------");
		System.out.println("销毁servlet");
		super.destroy();
	}


	
	
	
	
	
}

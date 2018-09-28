/**
*
* @author joker 
* @date 创建时间：2018年2月7日 下午6:11:03
* 
*/
package com.tmall.common.exception;

import com.tmall.common.enums.ResultEnums;

/**
* 第三方接口统一异常
* @author joker 
* @date 创建时间：2018年2月7日 下午6:11:03
*/
public class TmallThirdPartException extends Exception
{
	/**
	* 
	* @author joker 
	* @date 创建时间：2018年2月7日 下午6:11:50
	*/
	private static final long serialVersionUID = 3936056184384456094L;
	private String param;
	public TmallThirdPartException(ResultEnums resultEnums,String param)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
		this.param=param;
	}
	
	public TmallThirdPartException()
	{
		super();
	}
	private Integer code;
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getParam()
	{
		return param;
	}

	public void setParam(String param)
	{
		this.param = param;
	}
}

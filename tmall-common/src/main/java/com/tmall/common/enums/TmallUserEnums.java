/**
*
* @author joker 
* @date 创建时间：2018年1月28日 下午12:45:06
* 
*/
package com.tmall.common.enums;

/**
* 
* @author joker 
* @date 创建时间：2018年1月28日 下午12:45:06
*/
public enum TmallUserEnums
{
	ALLOWED(0,"允许登陆"),
	DISABLED(1,"禁止登陆"),
	;
	private Integer code;
	private String msg;
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	private TmallUserEnums(Integer code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}
	private TmallUserEnums()
	{
	}
	
}

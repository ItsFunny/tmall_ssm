/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum ProductStatusEnums
{
	UP(0, "上架"),
	DOWN(1, "下架"),
	DELETE(2,"彻底删除"),
	;
	private Integer status;
	private String msg;

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private ProductStatusEnums(Integer status, String msg)
	{
		this.status = status;
		this.msg = msg;
	}

	private ProductStatusEnums()
	{
	}

}

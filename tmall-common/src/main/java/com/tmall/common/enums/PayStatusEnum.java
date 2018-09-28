/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum PayStatusEnum
{
	//等待付款
	WAIT_PAY_MONEY(0,"wait_pay"),
	/*
	 * 成功付款
	 */
	SUCESS_PAY_MONEY(1,"sucess_pay"),
	/*
	 * 等待退款
	 */
	WAIT_REFUND(2,"wait_refund"),
	/*
	 * 成功退款
	 */
	SUCESS_REFUND(3,"sucess_refund"),
	;
	private Integer code;
	
	private String description;
	
	private PayStatusEnum(Integer code,String description)
	{
		this.description=description;
		this.code = code;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}

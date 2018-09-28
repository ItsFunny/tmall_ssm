/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum OrderStatusEnum
{
	NEW(0, "新建订单"),
	WAIT(1, "等待付款"), 
	CANCLE(2,"cancle","订单已取消"),
	WAIT_DELIVER(3,"wait_deliver","等待发货"),
	WAIT_RECEIPT_GOOS(4,"wait_receipt_goods","等待收货"),
	RECEIPT_GOODS(5,"receipt_goods","等待评价"),
	FINISH(6,"finished", "订单已完成,已评价"),
	;
	private Integer code;
	
	private String key;
	
	private String msg;
	
	OrderStatusEnum()
	{
	}

	OrderStatusEnum(Integer code, String key,String msg)
	{
		this.code = code;
		this.key=key;
		this.msg = msg;
	}
	OrderStatusEnum(Integer code,String msg)
	{
		this.code = code;
		this.msg = msg;
	}

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

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

}

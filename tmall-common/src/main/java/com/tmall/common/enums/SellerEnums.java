package com.tmall.common.enums;

public enum SellerEnums
{
	FOUNDER(0,"创始人"),
	
	
	
	
	
	
	
	//seller的状态
	ALLOWED(10,"账户可用"),
	DISABLE(11,"店铺已被封,账户被禁止登陆"),
	;
	private Integer code;
	
	private String description;

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

	private SellerEnums(Integer code, String description)
	{
		this.code = code;
		this.description = description;
	}

	private SellerEnums()
	{
	}
	
}

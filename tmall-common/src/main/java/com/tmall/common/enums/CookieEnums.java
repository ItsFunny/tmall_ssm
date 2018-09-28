/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum CookieEnums
{
	CART_COOKIES(1,"购物车列表");
	;
	private Integer code;
	private String comment;
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	private CookieEnums(Integer code, String comment)
	{
		this.code = code;
		this.comment = comment;
	}
	private CookieEnums()
	{
	}
	
	
	

}

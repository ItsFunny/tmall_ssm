/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum ResultEnums
{
	PRODUCT_NOTE_EXIT(0,"商品不存在"),
	
	
	
	
	
	
	
	
	
	
	USER_NOT_EXIT(10,"用户不存在"),
	USER_ILLEGAL_OPERATION(11,"用户异常操作"),
	USER_ONLINE_OUT_OF_RANGE(12,"在线用户数量超过承受范围"),
	USER_WAIT_LOGIN_OUT_OF_RANGE(13,"等待登陆用户人数过多"),
	USER_IS_NOT_LOGIN(14,"用户尚未登陆"),
	
	
	PRODUCT_EXIST_ILLEGAL(20,"商品所属哪家店铺未知"),//异常操作呢就是先直接将其修改为下架状态,然后计划后台呢整理出所有
													//下架商品,后面都应该有下架的原因
	
	SQL_SERVER_IS_DOWN(60,"myslq服务器挂了"),
	
	
	PICTURE_SERVER_IS_DOWN(70,"图片服务器挂了"),
	
	
	ORDER_NOT_EXIT(100,"订单详情不存在"),
	
	ORDERMASTER_NOT_EXIT(101,"主订单不存在"),
	ORDERDETAIL_NOT_EXIT(102,"订单详情不存在"),
	
	
	PROPERTY_NOT_EXIT(110,"商品父属性不存在,数据库错误"),
	
	SELLER_NOT_EXIT(111,"卖家不存在"),
	SELLER_NOT_LOGIN(112,"请先登陆"),
	SELLER_IS_DISABLED_STORE_DELETED(113,"用户被禁止登陆->店铺已下架"),
	SELLER_DONT_HAVE_AUTH_OF_THE_STORE(116,"用户无权访问该店铺"),
	
	USER_TRY_REPEIT_LOGIN(114,"请不要重复登陆"),
	USER_IS_DISABLED(115,"用户被禁止登陆"),
	
	SELLER_STORE_NAME_REPEAT(20,"店铺名字重复"),
	
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
	private ResultEnums(Integer code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}
	

}

/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum PictureEnums
{
	/*
	 * 点击商品后,就是左边的那些图片
	 */
	DETAIL_PICTURE(0,"detailPicture"),
	/*
	 * 天猫上往下翻出现的那些图片
	 */
	SHOW_PICTURE(1,"showPicture"),
	/*
	 * 这个一般是店铺的图片
	 */
	TOP_PICTURE(2,"topPicture"),
	/*
	 * 前台展示的单张页面
	 */
	INDEX_PICTURE(3,"indexPicture"),
	
	/*
	 * 图片crud操作时候的标志
	 */
	PICTURE_TYPE_CATEGORY(10,"category"),
	PICTURE_TYPE_PRODUCT(11,"product"),
	PICTURE_TYPE_STORE(12,"store"),
	PICTURE_TYPE_QRCODE(13,"qrCode"),
	
	/*
	 * 图片显示的地址设置
	 */
	PICTURE_SHOW_CATEGORY(20,"http://localhost:8094/pic/category/"),
	PICTURE_SHOW_PRODUCT(21,"http://localhost:8094/pic/product/"),
	PICTURE_SHOW_QRCODE(22,"http://localhost:8095/pic/qrCode/"),
	PICTURE_SHOW_STORE(23,"http://localhost:8094/pic/store/"),
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
	private PictureEnums(Integer code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}
	private PictureEnums()
	{
	}
	
	
}

/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum ServiceEnums
{
	/*
	 * url
	 */
	REST_BASE_URL("http://localhost:8091/rest","rest系统的基础url"),
	SEARCH_BASE_URL("http://localhost:8092/search","search服务的基础url"),
	PICTURE_CATEGORY_LOCATION("D:/JAVA/oxygen_workspace/tmall-store/src/main/webapp/pic/category","类目图片存放的地址"),
	PICTURE_PRODUCT_LOCATION("D:/JAVA/oxygen_workspace/tmall-store/src/main/webapp/pic/product","商品图片存放的地址"),
	PICTURE_QRCODE_LOCATION("D:/JAVA/oxygen_workspace/tmall-ssm-wechat/src/main/webapp/pic/qrCode","存放二维码的图片"),
	PICTURE_STORE_LOCATION("D:/JAVA/oxygen_workspace/tmall-store/src/main/webapp/pic/store","存放店铺的图片"),
	;
	private String url;
	private String description;

	private ServiceEnums()
	{
	}

	private ServiceEnums(String url, String description)
	{
		this.url = url;
		this.description = description;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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

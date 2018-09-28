/**
 * 
 */
package com.tmall.common.enums;

/**
 * @author Administrator
 *
 */
public enum RedisEnums
{
	/*
	 * 所有上架产品
	 * 单个类目下的所有商品
	 */
//	ALLUPPRODUCTS("ALLUPPRODUCTS"),
	ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE("ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE"),
	/*
	 * 所有上架商品的类目
	 * 所有类目
	 * 所有类目,附带图片相关
	 * 所有类目,及其对应的商品
	 */
//	ALLUPCATEGORIES("ALLUPCATEGORIES"),
	ALLCATEGORIES("ALLCATEGORIES"),
	ALLCATEGORIES_WITH_PICTURES("ALLCATEGORIES_WITH_PICTURES"),
	ALLCATEGORIES_WITH_PRODUCTS("ALLCATEGORIES_WITH_PRODUCTS"),
	/*
	 * 单个类目下的所有商品
	 * 单个类目下的所有商品的所有图片信息
	 */
//	SINGLECATEGORY("SINGLECATEGORY:DETAILS"),
	SINGLECATEGORY_PRODUCTS_ALLPICTURES("SINGELECATEGORY:PRODUCTS:ALLPICTURES"),
	/*
	 * 单个商品
	 * 单个商品的所有图片保存
	 * 单个商品的detail图片保存
	 * 单个商品的show图片保存
	 */
	PRODUCTDETAILS("PRODUCT:DETAILS"),
	PRODUCT_ALLPICTURES("PRODUCT:ALLPICTURES"),
	PRODUCT_DETAILPICTURES("PRODUCT:DETAILPICTURES"),
	PRODUCT_SHOWPICTURES("PRODUCT:SHOWPICTURES"),
	

	/*
	 * 商品属性
	 * 单个商品下的所有属性(是属性值)
	 * 商品下的所有属性(不是属性值) 这2者可以二合一
	 * 某个类目的所有属性,非属性值
	 */
	PRODUCT_PROPERTYVALUES("PRODUCT:PROPERTYVALUES"),
	PRODUCT_PROPERTIES("PRODUCT:PROPERTY"),
	CATEGORY_PROPERTY("CATEGORY_PROPERTY"),
	
	/*
	 * 单个商品的评论
	 */
	PRODUCTREVIEWS("PRODUCT:REVIEWS"),
	
	
	/*
	 * 存放用户信息
	 */
	USER("USER"),
	
	
	
	
	
	
	
	
	
	
	/*
	 * 图片,这些没必要存在
	 */
//	SINGLEPICTURE("PICTURE:SINGLE"),
//	DETAILPICTURE("PICTURE:DETAIL"),
//	SHOWPICTURE("PICTURE:SHOW"),
	
	
	/*
	 * 订单信息--->这些也没必要存放在redis,更新频繁
	 * 存放用户的订单信息--至于是订单详情还是ordermaster由后台决定
	 * 存放着所有订单,包含所有用户
	 */
//	USER_ORDERS("USER_ODERS"),
//	ALL_USERS_ALL_ORDERS("ALL_ORDERS"),
	;

	private String key;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	private RedisEnums(String key)
	{
		this.key = key;
	}

	private RedisEnums()
	{
	}
	
	

}

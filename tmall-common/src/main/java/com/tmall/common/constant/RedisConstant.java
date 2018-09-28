/**
 * 
 */
package com.tmall.common.constant;

/**
 * redis常量
 * @author Administrator
 *
 */
public interface RedisConstant
{
	String TOKEN_PREFIX="token_%s";
	Integer EXPIRE=7200;
	
	String SELLER_SESSIONID_PREFIX="SELLER_SESSIONID_%s";
	
	String STORE_SELLER_PREFIX="TMALL:STORE_SELLER_PREFIX:%s";
	
	Integer STORE_SELLER_EXPIRE=7200;
	
	
	
	/*保存着tmall所有用户的登陆信息*/
	/*
	 * 所有店铺
	 * 卖家的所有类目
	 * 卖家所有类目,附带图片信息
	 * 某个卖家某个类目下的所有商品
	 * 某个卖家某个类目下的所有属性
	 * 
	 */
	String ALL_STORES="ALL_STORES";
	String STORE_ALL_CATEGORIES="STORE_ALL_CATEGORIES%s";
	String STORE_ALL_CATEGORIES_WITH_PICTURES="STORE_ALL_CATEGORIES_WITH_PICTURES%s";
	String STORE_CATEGORY_ALL_PRODUCTS="STORE_CATEGORY:ALL_PRODUCTS%s";
	String STORE_CATEGORY_ALL_PROPERTIE="STORE_CATEGORY:ALL_PROPERTIES%S";
	String STORE_ALL_SELLERS="STORE_ALL_SELLERS%s";
	
	
	
	
	String PORTAL_USER_PREFIX="TMALL:PORTAL_USER:%s";
	
	Integer PORTAL_USER_EXPIRE=7200;
	
	/**
	 * 用于记录天猫前端在线人数
	 */
	String PORTAL_ONLINE_COUNT="PORTAIL_ONLINE_COUNT";
	
	/**
	*
	*记录天猫前端在线人员
	* @author joker 
	* @date 创建时间：2018年1月15日 下午1:28:40
	* 
	*/
	String PORTAL_ONLINE_USER="PORTAL_ONLINE_USER";
	
	String PORTAL_MAX_ONLINE_RECORD="PORTAL_MAX_ONLINE_RECORD";
	/**
	 * 用于记录天猫最高在线人数
	 */
	String PORTAL_MAX_COUNT="PORTAIL_MAX_COUNT";
	
	/**
	 * 在线人数达到最大时候的日期
	 */
	String PORTAL_DATE_OF_MAX_COUNT="DATE_OF_MAX_COUNT";
	
	/**
	 * portal访问次数
	* @author joker 
	* @date 创建时间：2018年1月13日 下午3:52:34
	*/
	String PORTAL_TOTAL_COUNT="PORTAL_TOTAL_COUNT";
	
	
	/**
	 * 店铺portal的访问次数
	* @author joker 
	* @date 创建时间：2018年1月13日 下午4:43:15
	* 
	*/
	String PORTAL_STORE_TOTAL_COUNT="PORTAL_STORE_TOTAL_COUNT%s";
	
	
	
}

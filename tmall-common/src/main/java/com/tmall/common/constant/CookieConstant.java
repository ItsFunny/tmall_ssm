/**
 * 
 */
package com.tmall.common.constant;

/**
 * cookie常量
 * @author Administrator
 *
 */
public interface CookieConstant
{
	String TOKEN="TOKEN";
	Integer EXPIRE=7200;
	
	String PORTAL_USER="PORTALUSERINFO";
	
	String STORE_SELLER="SELLERINFO";
	
	String TMALL_USER="TMALLUSER";
	
	Integer PORTAL_USER_EXPIRE=7200;
	Integer STORE_SELLER_EXPIRE=7200;
	/**
	 * 用于记录天猫前端在线人数
	 */
	String PORTAL_ONLINE_COUNT="PORTAIL_ONLINE_COUNT";
	
	/**
	 * 用于记录天猫最高人数
	 */
	String PORTAL_MAX_COUNT="PORTAIL_MAX_COUNT";
}

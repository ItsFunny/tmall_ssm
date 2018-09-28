/**
 * 
 */
package com.tmall.service.dao;

/**
 * @author Administrator
 *
 */
public interface RedisService
{
	String set(String key, String value);

	String get(String key);

	Long hSet(String hkey, String key, String value);

	String hGet(String hKey, String key);

	Long incr(String key);

	Long expire(String key, int second);

	Long ttl(String key);

	Long del(String key);

	Long hDel(String hKey, String key);
}

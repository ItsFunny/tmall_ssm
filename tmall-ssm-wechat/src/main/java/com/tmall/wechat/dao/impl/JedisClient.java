/**
 * 
 */
package com.tmall.wechat.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Administrator
 *
 */
@Component
public class JedisClient implements com.tmall.wechat.dao.RedisService
{

	@Autowired
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value)
	{
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String get(String key)
	{
		Jedis jedis = jedisPool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}

	@Override
	public Long hSet(String hkey, String key, String value)
	{
		Jedis jedis = jedisPool.getResource();
		Long res = jedis.hsetnx(hkey, key, value);
		jedis.close();
		return res;
	}

	@Override
	public String hGet(String hKey, String key)
	{
		Jedis jedis = jedisPool.getResource();
		String res = jedis.hget(hKey, key);
		jedis.close();
		return res;
	}

	@Override
	public Long incr(String key)
	{
		Jedis jedis = jedisPool.getResource();
		Long res = jedis.incr(key);
		jedis.close();
		return res;
	}

	@Override
	public Long expire(String key, int second)
	{
		Jedis jedis = jedisPool.getResource();
		Long expire = jedis.expire(key, second);
		jedis.close();
		return expire;
	}

	@Override
	public Long ttl(String key)
	{
		Jedis jedis = jedisPool.getResource();
		Long ttl = jedis.ttl(key);
		jedis.close();
		return ttl;
	}

	@Override
	public Long del(String key)
	{
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

	@Override
	public Long hDel(String hKey, String key)
	{
		Jedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(hKey, key);
		jedis.close();
		return hdel;
	}

	@Override
	public Long decr(String key)
	{
		Jedis jedis = jedisPool.getResource();
		Long decr = jedis.decr(key);
		if(decr.equals(0L)||decr<0L)
		{
			jedis.set(key, String.valueOf(0));
		}
		jedis.close();
		return decr;
	}

}

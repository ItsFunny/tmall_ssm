/**
 * 
 */
package com.tmall.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.service.dao.RedisService;

import redis.clients.jedis.JedisCluster;

/**
 * @author Administrator
 *
 */
public class JedisClusterClient implements RedisService
{

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String get(String key)
	{
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value)
	{
		return jedisCluster.set(key, value);
	}

	@Override
	public String hGet(String hkey, String key)
	{
		return jedisCluster.hget(hkey, key);
	}


	@Override
	public Long del(String key)
	{
		return jedisCluster.del(key);
	}

	@Override
	public Long hDel(String hkey, String key)
	{
		// TODO Auto-generated method stub
		return jedisCluster.hdel(hkey, key);
	}

	@Override
	public Long hSet(String hkey, String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incr(String key)
	{
		return jedisCluster.incr(key);
	}

	@Override
	public Long expire(String key, int second)
	{
		return jedisCluster.expire(key, second);
	}

	@Override
	public Long ttl(String key)
	{
		return jedisCluster.ttl(key);
	}

}

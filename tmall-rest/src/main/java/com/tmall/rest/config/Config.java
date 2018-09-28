package com.tmall.rest.config;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.tmall.rest.dao.impl.JedisClient;

import redis.clients.jedis.JedisPool;


/*
 * 配置信息
 */
@Configuration
public class Config
{

	@Value(value="${REDIS_SERVER_LOCATION}")
	private String REDIS_SERVER_LOCATION;
	@Bean(name = "dataSource")
	@Profile(value = "dev")
	public DruidDataSource dataSource()
	{
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("123");
		dataSource.setUrl("jdbc:mysql://localhost/rebuild_tmall?characterEncoding=utf-8");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(5);
		Collections.unmodifiableMap(new HashMap<Integer,Integer>());
		return dataSource;
	}

	@Bean(name = "dataSource")
	@Profile("prod")
	public DruidDataSource dataSourceProd()
	{
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://59.110.239.161/examination?characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(5);
		return dataSource;
	}

	@Bean
	public JedisPool jedisPool()
	{
		JedisPool jedisPool = new JedisPool(REDIS_SERVER_LOCATION, 6379);
		return jedisPool;
	}

	@Bean
	@Profile("dev")
	public JedisClient jedisClient()
	{
		JedisClient jedisClient = new JedisClient();
		return jedisClient;

	}

	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

}

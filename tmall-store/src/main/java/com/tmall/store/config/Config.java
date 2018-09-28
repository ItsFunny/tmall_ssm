/**
 * 
 */
package com.tmall.store.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.tmall.store.dao.impl.JedisClient;

import redis.clients.jedis.JedisPool;

/**
 * @author Administrator
 *
 */
@Configuration
@ComponentScan(basePackages="com.tmall.store")
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
		return dataSource;
	}

	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
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

	@SuppressWarnings("deprecation")
	@Bean
	public VelocityConfigurer velocityConfigurer()
	{
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setResourceLoaderPath("WEB-INF/templates");
		Properties properties = new Properties();
		properties.setProperty("input.encoding", "utf-8");
		properties.setProperty("output.encoding", "utf-8");
		velocityConfigurer.setVelocityProperties(properties);
		return velocityConfigurer;
	}
	@Bean
	public ViewResolver viewResolver()
	{
		@SuppressWarnings("deprecation")
		VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
		velocityViewResolver.setSuffix(".vm");
		velocityViewResolver.setContentType("text/html;charset=utf-8");
		return velocityViewResolver;
	}
	//文件解析器
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxInMemorySize(1024000);
		return multipartResolver;
	}
}

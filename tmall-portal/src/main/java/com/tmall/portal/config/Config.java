package com.tmall.portal.config;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.sun.tools.classfile.StackMapTable_attribute.same_frame;
import com.tmall.portal.dao.impl.JedisClient;

import redis.clients.jedis.JedisPool;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages="com.tmall.portal")
public class Config
{
	@Value(value="${REDIS_SERVER_LOCATION}")
	private String REDIS_SERVER_LOCATION;
	
	
	
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
		VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
		velocityViewResolver.setSuffix(".vm");
		return velocityViewResolver;
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
//	@Bean
//	public TmallAuthorizeException tmallAuthorizeException() 
//	{
//		TmallAuthorizeException tmallAuthorizeException=new TmallAuthorizeException();
//		return tmallAuthorizeException;
//	}
//	@Bean
//	public MyExceptionHandler myExceptionHandler()
//	{
//		MyExceptionHandler myExceptionHandler=new MyExceptionHandler();
//		return myExceptionHandler;
//	}

}

package com.tmall.wechat.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.joker.library.QueueLogin.QueueConfig;
import com.joker.library.QueueLogin.service.QueueLoginService;
import com.joker.library.QueueLogin.service.impl.QueueLoginServiceImpl;
import com.tmall.dto.UserDTO;
import com.tmall.wechat.dao.impl.JedisClient;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import redis.clients.jedis.JedisPool;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.tmall.wechat.config")
public class Config
{
	@Value(value = "${REDIS_SERVER_LOCATION}")
	private String REDIS_SERVER_LOCATION;
	@Value(value="${MAX_ONLINE_USER_COUNT}")
	private Integer MAX_ONLINE_USER_COUNT;
	@Value(value="${MAX_WAIT_LOGIN_USER_COUNT}")
	private Integer MAX_WAIT_LOGIN_USER_COUNT;
	@Autowired
	private WechatAccountConfig wechatAccountConfig;

	// 视图解析器
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
	public WxMpService wxMpService()
	{
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	@Bean
	public WxMpConfigStorage wxMpConfigStorage()
	{
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
		wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
		return wxMpInMemoryConfigStorage;
	}

	@Bean
	public QueueLoginService queueLoginService()
	{
		QueueLoginService queueLoginService = new QueueLoginServiceImpl();
		QueueConfig<UserDTO> config = QueueConfig.generate(MAX_ONLINE_USER_COUNT, MAX_WAIT_LOGIN_USER_COUNT);
		queueLoginService.setQueueConfig(config);
		return queueLoginService;
	}

	@Bean
	public JedisPool jedisPool()
	{
		JedisPool jedisPool = new JedisPool(REDIS_SERVER_LOCATION, 6379);
		return jedisPool;
	}

	@Bean
	// @Profile("dev")
	public JedisClient jedisClient()
	{
		JedisClient jedisClient = new JedisClient();
		return jedisClient;
	}

	// 文件解析器
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxInMemorySize(1024000);
		return multipartResolver;
	}
}

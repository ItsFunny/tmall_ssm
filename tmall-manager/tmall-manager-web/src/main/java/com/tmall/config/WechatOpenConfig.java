/**
 * 
 */
package com.tmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * @author Administrator
 *
 */
@Configuration
@ComponentScan(basePackages="com.tmall.config")
public class WechatOpenConfig
{
	@Autowired
	private WechatAccountConfig wechatAccountConfig;
	
	@Bean
	public WxMpService wxOpenMpService()
	{
		WxMpService wxOpenMpService=new WxMpServiceImpl();
		wxOpenMpService.setWxMpConfigStorage(wxOpenMpConfigStorage());
		return wxOpenMpService;
	}
	@Bean
	public WxMpConfigStorage wxOpenMpConfigStorage()
	{
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage=new WxMpInMemoryConfigStorage();
		return wxMpInMemoryConfigStorage;
	}

}

/**
 * 
 */
package com.tmall.wechat.config;

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
@ComponentScan(basePackages="com.tmall.wechat.config")
public class WechatConfig
{
	@Autowired
	private WechatAccountConfig wechatAccountConfig;
	
	@Bean
	public WxMpService wxMpService()
	{
		WxMpService wxMpService=new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}
	@Bean
	public WxMpConfigStorage wxMpConfigStorage()
	{
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage=new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
		wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
		return wxMpInMemoryConfigStorage;
	}

}

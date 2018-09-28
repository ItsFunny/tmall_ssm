/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午3:47:08
* 
*/
package com.tmall.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* 
* @author joker 
* @date 创建时间：2018年1月26日 下午3:47:08
*/
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatAccountConfig
{
	private String mpAppId;
	private String mpAppSecret;
	private String myOpenAppid;
	private String myOpenSecret;
	public String getMpAppId()
	{
		return mpAppId;
	}
	public void setMpAppId(String mpAppId)
	{
		this.mpAppId = mpAppId;
	}
	public String getMpAppSecret()
	{
		return mpAppSecret;
	}
	public void setMpAppSecret(String mpAppSecret)
	{
		this.mpAppSecret = mpAppSecret;
	}
	public String getMyOpenAppid()
	{
		return myOpenAppid;
	}
	public void setMyOpenAppid(String myOpenAppid)
	{
		this.myOpenAppid = myOpenAppid;
	}
	public String getMyOpenSecret()
	{
		return myOpenSecret;
	}
	public void setMyOpenSecret(String myOpenSecret)
	{
		this.myOpenSecret = myOpenSecret;
	}
	

}

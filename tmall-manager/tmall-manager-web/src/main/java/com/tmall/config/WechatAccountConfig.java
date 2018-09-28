/**
 * 
 */
package com.tmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class WechatAccountConfig
{
	@Value("${mpAppId}")
	private String mpAppId;
	@Value("${mpAppSecret}")
	private String mpAppSecret;
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
	
}

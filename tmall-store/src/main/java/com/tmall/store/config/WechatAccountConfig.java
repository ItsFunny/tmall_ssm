/**
 * 
 */
package com.tmall.store.config;

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
	
	@Value("${mpAppId}")
	private String myOpenAppid;
	@Value("${mpAppSecret}")
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

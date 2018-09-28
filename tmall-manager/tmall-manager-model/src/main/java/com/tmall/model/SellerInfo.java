/**
 * 
 */
package com.tmall.model;

/**
 * @author Administrator
 *
 */
public class SellerInfo
{
	private String sellerId;
	private String username;
	private String password;
	private Integer storeId;
	private String openId;

	public String getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(String sellerId)
	{
		this.sellerId = sellerId;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}

	public String getOpenId()
	{
		return openId;
	}

	public void setOpenId(String openId)
	{
		this.openId = openId;
	}

	@Override
	public String toString()
	{
		return "SellerInfo [sellerId=" + sellerId + ", username=" + username + ", password=" + password + ", storeId="
				+ storeId + ", openId=" + openId + "]";
	}

}

/**
 * 
 */
package com.tmall.dto;

import java.io.Serializable;

import com.tmall.model.TmallUser;

/**
 * @author Administrator
 *
 */
public class SellerDTO extends TmallUser implements Serializable
{
	/**
	* 
	* @author joker 
	* @date 创建时间：2018年1月28日 下午1:00:17
	*/
	private static final long serialVersionUID = -5842851004366552615L;
	
	
	
	
	private Integer storeId;
	private String storeName;
	
	
	private Integer level;
	

	public Integer getStoreId()
	{
		return storeId;
	}
	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}
	public String getStoreName()
	{
		return storeName;
	}
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	public Integer getLevel()
	{
		return level;
	}
	public void setLevel(Integer level)
	{
		this.level = level;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
}

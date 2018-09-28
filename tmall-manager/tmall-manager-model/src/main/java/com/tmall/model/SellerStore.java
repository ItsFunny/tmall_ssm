/**
*
* @author joker 
* @date 创建时间：2018年2月11日 下午2:48:23
* 
*/
package com.tmall.model;

/**
 * 
 * @author joker
 * @date 创建时间：2018年2月11日 下午2:48:23
 */
public class SellerStore
{
	private Long userId;
	private Integer storeId;
	private Integer level;

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

}

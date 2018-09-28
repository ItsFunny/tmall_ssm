/**
 * 
 */
package com.tmall.dto;

import java.util.Date;

import com.tmall.common.utils.ConverterUtils;

/**
 * @author Administrator
 *
 */
public class StoreDTO
{
	private Integer storeId;
	
	private String storeName;
	
	private String storePictureId;
	private String storePicturePath;
	
	
	private String userId;
	private Integer level;
	/*
	 * 电话号码
	 */
	private String phoneNumber;
	
	/*
	 * 创始人的名字
	 */
	private String realName;
	/*
	 * 创始人的账户名字
	 */
	private String username;
	
	
	private Date createDate;
	private String createDateStr;
	
	private Date updateDate;
	private String updateDateStr;
	


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



	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
		this.createDateStr=ConverterUtils.date2SimpleDateString(createDate);
	}

	public String getCreateDateStr()
	{
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr)
	{
		this.createDateStr = createDateStr;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
		this.updateDateStr=ConverterUtils.date2SimpleDateString(updateDate);
	}

	public String getUpdateDateStr()
	{
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr)
	{
		this.updateDateStr = updateDateStr;
	}

	public String getStorePictureId()
	{
		return storePictureId;
	}

	public void setStorePictureId(String storePictureId)
	{
		this.storePictureId = storePictureId;
	}

	public String getStorePicturePath()
	{
		return storePicturePath;
	}

	public void setStorePicturePath(String storePicturePath)
	{
		this.storePicturePath = storePicturePath;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

	@Override
	public boolean equals(Object obj)
	{
		StoreDTO storeDTO=(StoreDTO) obj;
		if(storeDTO.getStoreId().equals(this.storeId))
		{
			return true;
		}
		return false;
	}
	

}

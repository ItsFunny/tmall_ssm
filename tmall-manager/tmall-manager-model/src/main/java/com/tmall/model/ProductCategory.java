package com.tmall.model;

import java.util.Date;

public class ProductCategory
{
	private Integer categoryId;

	private String categoryName;

	private Integer categoryType;

	private Integer storeId;

	private Date createDate;

	private Date updateDate;

	public Integer getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public Integer getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(Integer categoryType)
	{
		this.categoryType = categoryType;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}
}
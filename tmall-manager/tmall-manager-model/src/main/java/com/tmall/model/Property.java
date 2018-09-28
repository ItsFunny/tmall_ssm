package com.tmall.model;

import java.util.Date;

public class Property
{
	private Integer propertyId;

	private Integer productCategoryId;

	private String propertyName;

	private Date createDate;

	private Date updateDate;

	public Integer getPropertyId()
	{
		return propertyId;
	}

	public void setPropertyId(Integer propertyId)
	{
		this.propertyId = propertyId;
	}

	public Integer getProductCategoryId()
	{
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId)
	{
		this.productCategoryId = productCategoryId;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName == null ? null : propertyName.trim();
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
}
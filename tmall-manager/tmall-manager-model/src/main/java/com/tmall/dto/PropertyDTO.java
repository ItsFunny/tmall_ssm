/**
 * 
 */
package com.tmall.dto;

import java.util.List;


/**
 * @author Administrator
 *
 */
public class PropertyDTO
{
	
	private Integer propertyId;
	private String propertyName;
//	private List<PropertyValue> propertyValues;
	private List<String> propertyValues;
	
	//在做manager的时候后增
	private Integer productCategoryId;
	//在做manager商品属性管理的时候重新设置property,将list改为string,计划通过逗号分隔,然后split
	private String propertyValue;
	
	
	public Integer getPropertyId()
	{
		return propertyId;
	}

	public void setPropertyId(Integer propertyId)
	{
		this.propertyId = propertyId;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}


	public Integer getProductCategoryId()
	{
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId)
	{
		this.productCategoryId = productCategoryId;
	}

	public String getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public List<String> getPropertyValues()
	{
		return propertyValues;
	}

	public void setPropertyValues(List<String> propertyValues)
	{
		this.propertyValues = propertyValues;
	}

}

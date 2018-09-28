/**
 * 
 */
package com.tmall.vo;



/**
 * @author Administrator
 *
 */
public class PropertyVO
{
	/*
	 * 重新修改下这个类的属性,采用多表查询的方式,而不是单一表查询
	 */
	// private Integer propertyId;
	// private String propertyName;
	//
	// private List<PropertyValue> propertyValues;
	private Integer propertyId;
	private String propertyName;
	private String productId;
	private String propertyValue;
	private Integer propertyValueId;

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

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public Integer getPropertyValueId()
	{
		return propertyValueId;
	}

	public void setPropertyValueId(Integer propertyValueId)
	{
		this.propertyValueId = propertyValueId;
	}

}

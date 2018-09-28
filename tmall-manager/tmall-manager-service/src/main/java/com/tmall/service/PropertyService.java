/**
 * 
 */
package com.tmall.service;

import java.util.List;

import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;

/**
 * @author Administrator
 *
 */
public interface PropertyService
{
	/*
	 * 查
	 * 查询某个分类下的所有属性
	 * 查询某个商品的所有属性和属性值
	 */
	List<Property>findCategoryAllProperties(Integer productCategoryType); 
	
	List<PropertyDTO> findProductAllPropertiesAndValues(String productId);
	
	void deletePropertyByPropertyId(Integer propertyId);
	
}

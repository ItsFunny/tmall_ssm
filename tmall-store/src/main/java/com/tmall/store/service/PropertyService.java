/**
 * 
 */
package com.tmall.store.service;

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
	 */
	/**
	 * @param productId  商品的id
	 * @return	返回这个商品下的所有属性(包含属性名称)
	 */
	List<PropertyDTO> findProductAllPropertyValues(String productId);
	
	/**
	 * @param categoryType	类目的type
	 * @param storeId	店铺的id
	 * @return	返回这个类目下的所有属性,不包含属性值
	 */
	List<Property>findAllPropertiesByCategoryType(Integer categoryType,Integer storeId);
	
	
	/*
	 * 改
	 */
	/**
	 * 更新属性的信息,通常都是更新名字
	 * @param property	更新的主体
	 * @param categoryType	这个属性属于哪个类目
	 * @param storeId	这个属性属于哪家店铺
	 */
	void updatePropertyByPropertyId(Property property,Integer categoryType,Integer storeId);

	
	/*
	 * 删
	 */
	/**
	 * 删除某个属性
	 * @param propertyId
	 * @param categoryType
	 * @param storeId
	 */
	void deletePropertyByPropertyId(Integer propertyId,Integer categoryType,Integer storeId);
	
	/*
	 * 增
	 */
	/**
	 * 增加某类目下的一个属性
	 * @param property	属性的对象	
	 * @param categoryType	类目的type
	 * @param storeId	店铺的id
	 */
	void addProperty(Property property,Integer categoryType,Integer storeId);
	
}

/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;

/**
 * @author Administrator
 *
 */
@Mapper
public interface PropertyDao
{
	/*
	 * 增加:
	 * 增加商品属性
	 */
	@Insert("insert into property (product_category_id,property_name) values (#{productCategoryId},#{propertyName})")
	void addProperty(Property property);
	
	/*
	 * 删除
	 * 删除这个分类下的某个属性,sql因为设置了CASCADE会自动将对应的删除
	 */
	@Delete("delete from property where property_id=#{propertyId}")
	void deletePropertyByPropertyId(Integer propertyId);
	
	/*
	 * 查找:
	 * 商品的所有属性
	 * 根据propertyvalue查找所有父id,得到propertyname
	 * 查找某个类目下的所有property
	 * 查找商品的所有属性,简化版
	 */
	
	List<PropertyDTO> findAllPropertiesByProductId(String productId);
	
	List<Property> findAllPropertiesInPropertyValusIds(List<Integer> valueIds);
	@Select("select * from property where product_category_id=#{productCategoryType}")
	List<Property>findCategoryAllProperties(Integer prouctCategoryType);
	
	List<PropertyDTO>findProductProperties(String productId);
	/*
	 * 更新
	 * 更新类目的单个属性
	 */
	void updatePropertyByPropertyId(Property property);
	

}

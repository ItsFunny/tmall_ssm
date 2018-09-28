/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.model.PropertyValue;

/**
 * @author Administrator
 *
 */
@Mapper
public interface PropertyValueDao
{
	/*
	 * 查找
	 * 查询productId的所有属性值
	 */
	@Select("select * from property_value where product_id=#{productId}")
	List<PropertyValue> findAllPropertyValuesByProductId(String productId);
	

}

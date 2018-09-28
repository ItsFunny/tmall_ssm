/**
 * 
 */
package com.tmall.rest.service;

import java.util.List;

import com.tmall.model.PropertyValue;


/**
 * @author Administrator
 *
 */
public interface PropertyValueService
{
	
	List<PropertyValue> findAllPropertyValues(String productId);
}

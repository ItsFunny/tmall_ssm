/**
 * 
 */
package com.tmall.rest.service;

import java.util.List;

import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;


/**
 * @author Administrator
 *
 */
public interface PropertyService
{
	List<PropertyDTO> findAllPropertiesByProductId(String productId);
	//简化版
	List<PropertyDTO>findProductPropertiesByProductId(String productId);
	
	
	
	List<Property> findAllProductPropertyByPropertyIds(List<Integer> ids,String productId);
	
	
}

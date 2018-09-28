/**
 * 
 */
package com.tmall.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.model.PropertyValue;
import com.tmall.rest.service.PropertyValueService;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/propertyvalue")
public class PropertyValueController
{
	@Autowired
	private PropertyValueService propertyValueService;

	@RequestMapping("/product/{productId}")
	public List<PropertyValue> findAllPropertyValues(@PathVariable("productId") String productId)
	{
		List<PropertyValue> values = propertyValueService.findAllPropertyValues(productId);
		return values;
	}

}

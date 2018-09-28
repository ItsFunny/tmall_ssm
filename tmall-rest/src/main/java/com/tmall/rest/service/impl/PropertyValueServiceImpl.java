/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.enums.RedisEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.PropertyValueDao;
import com.tmall.model.PropertyValue;
import com.tmall.rest.dao.impl.JedisClient;
import com.tmall.rest.service.PropertyValueService;

/**
 * @author Administrator
 *
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService
{
	@Autowired
	private PropertyValueDao propertyValueDao;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<PropertyValue> findAllPropertyValues(String productId)
	{
		String key = RedisEnums.PRODUCT_PROPERTYVALUES.getKey() + ":" + productId;
		String json = null;
		List<PropertyValue> values = new ArrayList<PropertyValue>();
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			values = JsonUtils.jsonToList(json, PropertyValue.class);
			return values;
		}
		values = propertyValueDao.findAllPropertyValuesByProductId(productId);
		try
		{
			String value = JsonUtils.List2Json(values);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return values;
	}

}

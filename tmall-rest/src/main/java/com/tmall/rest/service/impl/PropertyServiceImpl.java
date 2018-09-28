/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.PropertyDao;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;
import com.tmall.rest.dao.impl.JedisClient;
import com.tmall.rest.service.PropertyService;

/**
 * @author Administrator
 *
 */
@Service
public class PropertyServiceImpl implements PropertyService
{
	private Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);
	@Autowired
	private PropertyDao propertyDao;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<PropertyDTO> findAllPropertiesByProductId(String productId)
	{
		String key = RedisEnums.PRODUCT_PROPERTYVALUES.getKey() + ":" + productId;
		List<PropertyDTO> propertyDTOs = new ArrayList<PropertyDTO>();
		String json = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
			logger.error("[获取商品属性]redis服务器挂了");
		}
		if (!StringUtils.isEmpty(json))
		{
			propertyDTOs = JsonUtils.jsonToList(json, PropertyDTO.class);
			return propertyDTOs;
		}
		propertyDTOs = propertyDao.findAllPropertiesByProductId(productId);
		try
		{
			String value = JsonUtils.List2Json(propertyDTOs);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}

		return propertyDTOs;
	}

	@Override
	public List<Property> findAllProductPropertyByPropertyIds(List<Integer> ids, String productId)
	{
		if (ids.isEmpty())
		{
			throw new TmallException(ResultEnums.PROPERTY_NOT_EXIT);
		}
//		String key = RedisEnums.PROPERTY_PROPERTIES.getKey() + ":" + productId;
//		String json = null;
		List<Property> propertyList = new ArrayList<>();
//		try
//		{
//			json = jedisClient.get(key);
//		} catch (Exception e)
//		{
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			propertyList = JsonUtils.jsonToList(json, Property.class);
//			return propertyList;
//		}
		propertyList = propertyDao.findAllPropertiesInPropertyValusIds(ids);
//		try
//		{
//			String value = JsonUtils.List2Json(propertyList);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			logger.error("[查询父属性]{}", e.getMessage());
//		}
		return propertyList;
	}

	@Override
	public List<PropertyDTO> findProductPropertiesByProductId(String productId)
	{
		List<PropertyDTO> propertyDTOList = propertyDao.findProductProperties(productId);
		for (PropertyDTO propertyDTO : propertyDTOList)
		{
			if(propertyDTO.getPropertyValue().contains(","))
			{
				String[] strings = propertyDTO.getPropertyValue().split(",");
				List<String>values=new ArrayList<>();
				for(int i=0;i<strings.length;++i)
				{
					values.add(strings[i]);
				}
				propertyDTO.setPropertyValues(values);
			}
		}
		return propertyDTOList;
	}

}

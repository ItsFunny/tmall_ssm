/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.PropertyDao;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.PropertyService;

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
	public List<PropertyDTO> findProductAllPropertyValues(String productId)
	{
		return propertyDao.findAllPropertiesByProductId(productId);
	}

	@Override
	public List<Property> findAllPropertiesByCategoryType(Integer categoryType, Integer storeId)
	{
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PROPERTIE, ":" + storeId + ":" + categoryType);
		String json = null;
		List<Property> propertyList = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			propertyList = JsonUtils.jsonToList(json, Property.class);
			return propertyList;
		}
		propertyList = propertyDao.findCategoryAllProperties(categoryType);
		if (propertyList != null && propertyList.size() > 0)
		{
			try
			{
				String value = JsonUtils.List2Json(propertyList);
				jedisClient.set(key, value);
			} catch (Exception e)
			{
			}
		}
		return propertyList;
	}

	@Override
	public void updatePropertyByPropertyId(Property property, Integer categoryType, Integer storeId)
	{
		propertyDao.updatePropertyByPropertyId(property);
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PROPERTIE, ":" + storeId + ":" + categoryType);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}

	@Override
	public void deletePropertyByPropertyId(Integer propertyId, Integer categoryType, Integer storeId)
	{
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PROPERTIE, ":" + storeId + ":" + categoryType);
		propertyDao.deletePropertyByPropertyId(propertyId);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
			logger.error("[删除类目属性]{}", "redis服务器可能挂了");
		}
	}

	@Override
	public void addProperty(Property property, Integer categoryType, Integer storeId)
	{
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PROPERTIE, ":" + storeId + ":" + categoryType);
		propertyDao.addProperty(property);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}
}

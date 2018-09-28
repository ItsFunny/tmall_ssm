/**
 * 
 */
package com.tmall.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.PropertyDao;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;
import com.tmall.service.PropertyService;
import com.tmall.service.dao.impl.JedisClient;

/**
 * @author Administrator
 *
 */
@Service
public class PropertyServiceImpl implements PropertyService
{
	private Logger logger=LoggerFactory.getLogger(PropertyServiceImpl.class);
	@Autowired
	private PropertyDao propertyDao;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<Property> findCategoryAllProperties(Integer productCategoryType)
	{
		String key=RedisEnums.CATEGORY_PROPERTY.getKey()+":"+productCategoryType.toString();
		String json=null;
		List<Property>properties=new ArrayList<Property>();
		try
		{
			json=jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if(!StringUtils.isEmpty(json))
		{
			properties=JsonUtils.jsonToList(json, Property.class);
			return properties;
		}
		properties = propertyDao.findCategoryAllProperties(productCategoryType);
		try
		{
			String value=JsonUtils.List2Json(properties);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return properties;
	}

	/* (non-Javadoc)
	 * @see com.tmall.service.PropertyService#deletePropertyByPropertyId(java.lang.Integer)
	 */
	@Override
	public void deletePropertyByPropertyId(Integer propertyId)
	{
		propertyDao.deletePropertyByPropertyId(propertyId);
		String key=RedisEnums.CATEGORY_PROPERTY.getKey()+":"+propertyId.toString();
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
			logger.error("[manager获取类目属性]{}",e.getMessage());
		}
	}

	@Override
	public List<PropertyDTO> findProductAllPropertiesAndValues(String productId)
	{
		String url=ServiceEnums.REST_BASE_URL.getUrl()+"/property/product/values/{productId}";
		ResultVo<List<PropertyDTO>>resultVo=new ResultVo<>();
		resultVo=restTemplate.getForObject(url, ResultVo.class,productId);
		List<PropertyDTO> propertyDTO = resultVo.getData();
		return propertyDTO;
	}


}

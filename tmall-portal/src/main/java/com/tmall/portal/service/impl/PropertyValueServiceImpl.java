/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.PropertyValue;
import com.tmall.portal.service.PropertyValueService;

/**
 * @author Administrator
 *
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService
{
	private Logger logger = LoggerFactory.getLogger(PropertyValueServiceImpl.class);
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<PropertyValue> findAllPropertyValues(String productId)
	{
		String url = REST_BASE_URL + "/propertyvalue/product/{productId}";
		List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();
		ResultVo<PropertyValue> resultVo = new ResultVo<>();

		try
		{
			propertyValueList = restTemplate.getForObject(url, List.class, productId);
		} catch (Exception e)
		{
			// 自己查询
			logger.error("[查询商品属性值]rest服务器挂了");
		}
		return propertyValueList;
	}

}

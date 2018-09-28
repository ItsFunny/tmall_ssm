/**
 * 
 */
package com.tmall.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;
import com.tmall.portal.service.PropertyService;

/**
 * @author Administrator
 *
 */
@Service
public class PropertyServiceImpl implements PropertyService
{
	private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResultVo<PropertyDTO> findAllPropertyValusByProductId(String productId)
	{
		String url=REST_BASE_URL+"/property/product/{productId}";
		ResultVo<PropertyDTO> resultVo=new ResultVo<>();
		try
		{
			resultVo=restTemplate.getForObject(url, ResultVo.class,productId);
		} catch (Exception e)
		{
			logger.error("[portal系统,查询商品属性]远端rest服务器挂了");
			//远端rest服务器挂了的话,自己查询
			//限于服务器无法备多台服务器,所以自己查询
		}
		return resultVo;
	}

}

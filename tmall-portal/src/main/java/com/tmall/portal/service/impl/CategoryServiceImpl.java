/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.vo.ResultVo;
import com.tmall.dao.ProductCategoryDao;
import com.tmall.model.ProductCategory;
import com.tmall.portal.service.ProductCategoryService;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Service
public class CategoryServiceImpl implements ProductCategoryService
{
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResultVo<ProductCategoryVO> findOne(Integer categoryType,Integer storeId)
	{
		String url = REST_BASE_URL + "/category/detail/{categoryType}/{storeId}}";
//		String findOneUrl=REST_BASE_URL+"/category/single/{categoryType}";
		Map<String, Object> params = new HashMap<>();
		params.put("categoryType", categoryType);
		params.put("storeId", storeId);
		ResultVo<ProductCategoryVO> resultVo = new ResultVo<>();
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class, params);
		} catch (Exception e)
		{
			logger.error("[获取单个类目所有商品错误]远程rest服务器挂了");
			ProductCategoryVO productCategory = productCategoryDao.findOne(categoryType);
			resultVo = new ResultVo<ProductCategoryVO>(200, productCategory);
		}
		return resultVo;
	}

	@Override
	public ResultVo<ProductCategory> coverFindOne(Integer categoryType)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

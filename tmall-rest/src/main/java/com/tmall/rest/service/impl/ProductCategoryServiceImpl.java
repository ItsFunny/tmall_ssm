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
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.ProductCategoryDao;
import com.tmall.model.ProductCategory;
import com.tmall.rest.dao.impl.JedisClient;
import com.tmall.rest.service.PictureService;
import com.tmall.rest.service.ProductCategoryService;
import com.tmall.vo.ProductCategoryVO;

import net.sf.json.JSONArray;

/**
 * @author Administrator
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService
{
	private Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private PictureService pictureService;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public ProductCategoryVO findOne(Integer categoryType)
	{
//		String key = RedisEnums.SINGLECATEGORY.getKey() + ":" + categoryType.toString();
		ProductCategoryVO productCategory = new ProductCategoryVO();
//		String json = null;
//		try
//		{
//			json = jedisClient.get(key);
//
//		} catch (Exception e)
//		{
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			productCategory = JsonUtils.jsonToPojo(json, ProductCategoryVO.class);
//			return productCategory;
//		}
		productCategory = productCategoryDao.findOne(categoryType);
//		try
//		{
//			String value = JsonUtils.objectToJson(productCategory);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//		}
		return productCategory;
	}

	@Override
	public List<ProductCategoryVO> findAllCategories()
	{
		String key = RedisEnums.ALLCATEGORIES.getKey();
		List<ProductCategoryVO> productCategories = new ArrayList<>();
		String json = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
//			JSONArray jsonArray=JSONArray.fromObject(json);
//			productCategories=JSONArray.toList(jsonArray,ProductCategory.class);
			productCategories=JsonUtils.jsonToList(json, ProductCategoryVO.class);
			return productCategories;
		}
		productCategories = productCategoryDao.findAllProductCategories();
		try
		{
			String value = JsonUtils.List2Json(productCategories);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return productCategories;
	}
	@Override
	public List<ProductCategoryVO> findAllProductCategoriesInAllCategoryTypes(List<Integer> categoryTypeList)
	{
		/*
		 * 这里应该是上架商品的类目
		 */
//		String key = RedisEnums.ALLUPCATEGORIES.getKey();
		List<ProductCategoryVO> categories = new ArrayList<ProductCategoryVO>();
//		String json = null;
//		try
//		{
//			json = jedisClient.get(key);
//
//		} catch (Exception e)
//		{
//			logger.error("[展示首页]redis服务器挂了");
//		}
//		if (!StringUtils.isEmpty(json))
//		{
			// categories = JsonUtils.jsonToList(json, ProductCategory.class);
//			JSONArray jsonArray = JSONArray.fromObject(json);
//			categories = JSONArray.toList(jsonArray, ProductCategoryVO.class);
//			return categories;
//		}
		categories = productCategoryDao.findAllProductCategoriesInCategoryType(categoryTypeList);
//		try
//		{
//			String value = JsonUtils.List2Json(categories);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			logger.error("[展示首页]数据信息写入redis错误,redis挂了吧");
//		}
		return categories;
	}

	@Override
	public List<ProductCategoryVO> findAllCategoriesAndPictures()
	{
		String key = RedisEnums.ALLCATEGORIES_WITH_PICTURES.getKey();
		List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
		String json = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(json))
		{
			JSONArray jsonArray = JSONArray.fromObject(json);
			productCategoryVOList = JSONArray.toList(jsonArray, ProductCategoryVO.class);
			return productCategoryVOList;
		}
		List<ProductCategoryVO> categories = findAllCategories();
		List<Integer> categoryTypes = new ArrayList<>();
		for (ProductCategoryVO productCategory : categories)
		{
			categoryTypes.add(productCategory.getCategoryType());
		}
		productCategoryVOList = productCategoryDao.findAllCategoriesAndPictures(categoryTypes);
		try
		{
			String value = JsonUtils.List2Json(productCategoryVOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return productCategoryVOList;
	}

}

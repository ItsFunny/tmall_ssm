/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.sun.tools.jdeps.resources.jdeps;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.CategoryPictureDao;
import com.tmall.dao.ProductCategoryDao;
import com.tmall.model.ProductCategory;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.CategoryPictureService;
import com.tmall.store.service.ProductCategoryService;
import com.tmall.store.service.ProductInfoService;
import com.tmall.store.service.StoreCategoryService;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService
{
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private CategoryPictureService categoryPictureService;
	@Autowired
	private StoreCategoryService storeCategoryService;

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	private ProductInfoService productInfoService;


	@Override
	public List<ProductCategoryVO> findSellerCategoryList(List<Integer> categoryTypeList,Integer storeId)
	{
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES_WITH_PICTURES, ":"+storeId);
		String json=null;
		List<ProductCategoryVO>productCategoryVOList=new ArrayList<ProductCategoryVO>();
		try
		{
			json=jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if(!StringUtils.isEmpty(json))
		{
			productCategoryVOList=JsonUtils.jsonToList(json, ProductCategoryVO.class);
			return productCategoryVOList;
		}
		productCategoryVOList=productCategoryDao.findAllCategoriesAndPictures(categoryTypeList);
		try
		{
			String value=JsonUtils.List2Json(productCategoryVOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return productCategoryVOList;
	}
	@Transactional
	@Override
	public void updateSellerCategoryByCategoryType(ProductCategory productCategory, Integer storeId,String picturePath)
	{
		categoryPictureService.updateCategoryPictureByCategoryType(picturePath, productCategory.getCategoryType());
		productCategoryDao.updateProductCategoryName(productCategory.getCategoryName(), productCategory.getCategoryType());
	
	}

	@Override
	public void updateSellerCategoryAndUpdateRedis(ProductCategory productCategory, Integer storeId, String picturePath)
	{
		updateSellerCategoryByCategoryType(productCategory, storeId, picturePath);
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES_WITH_PICTURES, ":"+storeId);
		System.out.println(key);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Transactional
	@Override
	public void addCategory(ProductCategoryVO productCategory, Integer storeId)
	{
		productCategoryDao.addCategory(productCategory);
		//添加类目的图片
		categoryPictureService.addCategoryPicture(productCategory);
		//添加店铺对应类目
		storeCategoryService.addSellerStoreCategory(storeId, productCategory.getCategoryType());
	}


	@Override
	public void addCategoryAndUpdateRedis(ProductCategoryVO productCategory, Integer storeId)
	{
		addCategory(productCategory, storeId);
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES, ":"+storeId);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}


	@Transactional
	@Override
	public void deleteCategoryByCategoryType(Integer categoryType, Integer productStatus,Integer storeId)
	{
		productCategoryDao.deleteCategory(categoryType);
		//将商品信息改为下架
		productInfoService.updateProductStatusByCategoryType(categoryType, productStatus,storeId);
	}


	@Override
	public void deleteCategoryAndUpdateRedis(Integer categoryType, Integer productStatus,Integer storeId)
	{
		deleteCategoryByCategoryType(categoryType, productStatus, storeId);
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES, ":"+storeId);
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}
}

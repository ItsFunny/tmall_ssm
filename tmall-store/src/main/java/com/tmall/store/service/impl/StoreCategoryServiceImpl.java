/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.StoreCategoryDao;
import com.tmall.dto.SellerDTO;
import com.tmall.model.StoreCategory;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.SellerInfoService;
import com.tmall.store.service.StoreCategoryService;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Service
public class StoreCategoryServiceImpl implements StoreCategoryService
{
	Logger logger = LoggerFactory.getLogger(StoreCategoryServiceImpl.class);
	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private StoreCategoryDao storeCategoryDao;

	/**
	 * 根据卖家的店铺id获取卖家下有哪些类目
	 * 
	 * @param storeId
	 *            卖家的店铺id
	 * @return
	 */
	@Override
	public List<StoreCategory> findSellerCategoryList(SellerDTO sellerInfo)
	{
		List<StoreCategory> storeCategoryList = new ArrayList<StoreCategory>();
		storeCategoryList = storeCategoryDao.findStoreCategoryByStoreId(sellerInfo.getStoreId());
		return storeCategoryList;
	}

	@Override
	public void addSellerStoreCategory(Integer storeId, Integer categoryType)
	{
		StoreCategory storeCategory=new StoreCategory();
		storeCategory.setProductCategoryType(categoryType);
		storeCategory.setStoreId(storeId);
		storeCategoryDao.addStoreCategory(storeCategory);
	}

	@Override
	public List<ProductCategoryVO> findStoreCategoryList(Integer storeId)
	{
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES, ":"+storeId);
		String json=null;
		List<ProductCategoryVO>storeCategories=null;
		try
		{
			json=jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if(!StringUtils.isEmpty(json))
		{
			storeCategories=JsonUtils.jsonToList(json, ProductCategoryVO.class);
			return storeCategories;
		}
		storeCategories=storeCategoryDao.findStoreCategoryList(storeId);
		if(storeCategories!=null&&storeCategories.size()>0)
		{
			String value=JsonUtils.objectToJson(storeCategories);
			try
			{
				jedisClient.set(key, value);
			} catch (Exception e)
			{
			}
		}
		return storeCategories;
	}
}

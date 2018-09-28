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

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.StoreCategoryDao;
import com.tmall.model.SellerInfo;
import com.tmall.model.StoreCategory;
import com.tmall.service.StoreCategoryService;
import com.tmall.service.dao.impl.JedisClient;

/**
 * @author Administrator
 *
 */
@Service
public class StoreCategoryServiceImpl implements StoreCategoryService
{
	Logger logger=LoggerFactory.getLogger(StoreCategoryServiceImpl.class);
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private StoreCategoryDao storeCategoryDao;

	/**
	 * 根据卖家的店铺id获取卖家下有哪些类目
	 * @param storeId  卖家的店铺id
	 * @return
	 */
	@Override
	public List<StoreCategory> findSellerCategoryList(SellerInfo sellerInfo)
	{
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES, ":"+sellerInfo.getStoreId());
		String json=null;
		List<StoreCategory>storeCategoryList=new ArrayList<StoreCategory>();
		try
		{
			json=jedisClient.get(key);
		} catch (Exception e)
		{
			logger.error("{}",e.getMessage());
		}
		if(!StringUtils.isEmpty(json))
		{
			storeCategoryList=JsonUtils.jsonToList(json, StoreCategory.class);
			return storeCategoryList;
		}
		storeCategoryList=storeCategoryDao.findStoreCategoryByStoreId(sellerInfo.getStoreId());
		try
		{
			String value=JsonUtils.List2Json(storeCategoryList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return storeCategoryList;
	}


}

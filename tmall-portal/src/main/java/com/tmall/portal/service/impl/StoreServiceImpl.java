/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.ProductCategoryDao;
import com.tmall.dao.ProductInfoDao;
import com.tmall.dao.StoreCategoryDao;
import com.tmall.dao.StoreDao;
import com.tmall.dto.StoreDTO;
import com.tmall.model.StoreCategory;
import com.tmall.portal.dao.impl.JedisClient;
import com.tmall.portal.service.StoreService;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class StoreServiceImpl implements StoreService
{
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private StoreCategoryDao storeCategoryDao;
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductInfoDao productInfoDao;
	@Override
	public List<StoreDTO> findAllStores()
	{
		String key=RedisConstant.ALL_STORES;
		String json=null;
		List<StoreDTO>storeDTOList=null;
		try
		{
			json=jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if(!StringUtils.isEmpty(json))
		{
			storeDTOList=JsonUtils.jsonToList(json, StoreDTO.class);
			return storeDTOList;
		}
		storeDTOList=storeDao.findStores();
		try
		{
			String value = JsonUtils.List2Json(storeDTOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return storeDTOList;
	}
	@Override
	public List<ProductCategoryVO> findStoreAllCategoryVOs(Integer storeId)
	{
		String key=String.format(RedisConstant.STORE_ALL_CATEGORIES, ":"+storeId);
		String json=null;
		List<ProductCategoryVO>productCategoryVOList=null;
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
		List<StoreCategory> storeCategories = storeCategoryDao.findStoreCategoryByStoreId(storeId);
		if(storeCategories!=null&&storeCategories.size()>0)
		{
			List<Integer>categoryTypeList=new ArrayList<Integer>();
			for (StoreCategory storeCategory : storeCategories)
			{
				categoryTypeList.add(storeCategory.getProductCategoryType());
			}
			productCategoryVOList=productCategoryDao.findAllProductCategoriesInCategoryType(categoryTypeList);
		}
		try
		{
			String value = JsonUtils.List2Json(productCategoryVOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return productCategoryVOList;
	}
	@Override
	public List<ProductInfoVO> findStoreAllProducts(Integer storeId)
	{
		return productInfoDao.findStoreAllProducts(storeId);
	}
	@Override
	public List<ProductInfoAndCategoryVO> findStoreAllCategoryAndAllProducts(Integer storeId)
	{
		List<ProductInfoAndCategoryVO>productInfoAndCategoryVOs=new ArrayList<>();
		List<ProductCategoryVO> categoryVOs = findStoreAllCategoryVOs(storeId);
		List<ProductInfoVO> products = findStoreAllProducts(storeId);
		if(categoryVOs!=null&&categoryVOs.size()>0)
		{
			for (ProductCategoryVO productCategoryVO : categoryVOs)
			{
				ProductInfoAndCategoryVO productInfoAndCategoryVO=new ProductInfoAndCategoryVO();
				BeanUtils.copyProperties(productCategoryVO, productInfoAndCategoryVO);
				List<ProductInfoVO>productInfoVOList=new ArrayList<>();
				if(products!=null&&products.size()>0)
				{
					for (ProductInfoVO productInfoVO : products)
					{
						if(productInfoVO.getCategoryType().equals(productInfoAndCategoryVO.getCategoryType()))
						{
							productInfoVOList.add(productInfoVO);
						}
					}
					if(productInfoVOList!=null&&productInfoVOList.size()>0)
					{
						productInfoAndCategoryVO.setProductInfoList(productInfoVOList);
					}
				}
				productInfoAndCategoryVOs.add(productInfoAndCategoryVO);
			}
		}
		return productInfoAndCategoryVOs;
	}
}

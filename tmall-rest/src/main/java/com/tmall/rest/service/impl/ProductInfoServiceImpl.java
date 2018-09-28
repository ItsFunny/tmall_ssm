/**
 * 
 */
package com.tmall.rest.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.enums.RedisEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.ProductInfoDao;
import com.tmall.model.Picture;
import com.tmall.rest.dao.impl.JedisClient;
import com.tmall.rest.service.PictureService;
import com.tmall.rest.service.ProductInfoService;
import com.tmall.vo.ProductInfoVO;

import net.sf.json.JSONArray;

/**
 * @author Administrator
 *
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService
{
	private Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	@Autowired
	private ProductInfoDao productInfoDao;

	@Autowired
	private PictureService pictureService;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<ProductInfoVO> findUpAll()
	{
		/*
		 * 所有商品并不适合存放在redis中 而且查询所有商品吧,不是查询所有上架商品
		 */
		// String key = RedisEnums.ALLUPPRODUCTS.getKey();
		List<ProductInfoVO> productInfoVOs = new ArrayList<ProductInfoVO>();
		// String json = null;
		// try
		// {
		// json = jedisClient.get(key);
		// } catch (Exception e)
		// {
		//
		// }
		// if (!StringUtils.isEmpty(json))
		// {
		// productInfoVOs = JsonUtils.jsonToList(json, ProductInfoVO.class);
		// return productInfoVOs;
		// }
		productInfoVOs = productInfoDao.findUpAll();

		// try
		// {
		// String value = JsonUtils.List2Json(productInfoVOs);
		// jedisClient.set(key, value);
		// } catch (Exception e)
		// {
		// }
		return productInfoVOs;
	}

	/*
	 * 这个方法该修改下
	 * 108: 这里应该改为结合store查询 为了店铺的功能
	 * 
	 */
	@Override
	public List<ProductInfoVO> findAllProductsByCategoryTypeAndStoreId(Integer categoryType,Integer storeId)
			throws IllegalAccessException, InvocationTargetException
	{
//		String key = RedisEnums.ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE + ":" + categoryType.toString();
		List<ProductInfoVO> productInfoVOs = new ArrayList<>();
//		String json = null;
//		try
//		{
//			json = jedisClient.get(key);
//		} catch (Exception e)
//		{
//			logger.error("[获取单个商品类目下的所有商品]redis服务器挂了");
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			// productInfoVOs = JsonUtils.jsonToList(json, ProductInfoVO.class);
//			JSONArray jsonArray = JSONArray.fromObject(json);
//			productInfoVOs = JSONArray.toList(jsonArray, ProductInfoVO.class);
//			return productInfoVOs;
//		}
		productInfoVOs = productInfoDao.findAllProductsByCategoryTypeAndStoreId(categoryType, storeId);
//		if (productInfoVOs.isEmpty())
//		{
			// List<String> pictureIds = new ArrayList<>();
			// for (ProductInfoVO productInfo : productInfos)
			// {
			// if (!pictureIds.contains(productInfo.getPictureId()))
			// {
			// pictureIds.add(productInfo.getPictureId());
			// }
			// }
			// List<Picture> pictures =
			// pictureService.findPicturesByPictureIdList(pictureIds);
			// for (ProductInfoVO productInfo : productInfos)
			// {
			// for (Picture picture : pictures)
			// {
			// ProductInfoVO productInfoVO = new ProductInfoVO();
			// BeanUtils.copyProperties(productInfo, productInfoVO);
			// if (picture.getPictureId().equals(productInfo.getPictureId()))
			// {
			// productInfo.setProductPicturePath(picture.getPicturePath());
			// }
			// }
			// productInfoVOs.add(productInfo);
			// }
//		} else
//		{
//			return productInfoVOs;
//		}
//		try
//		{
//			String value = JsonUtils.List2Json(productInfoVOs);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			logger.error("[获取单个商品类目下的所有商品]写入redis错误,redis服务器可能挂了");
//		}
		return productInfoVOs;
	}

	@Override
	public ProductInfoVO findOne(String productId)
	{
		String key = RedisEnums.PRODUCTDETAILS.getKey() + ":" + productId;
		String json = null;
		ProductInfoVO productInfoVO = new ProductInfoVO();
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			productInfoVO = JsonUtils.jsonToPojo(json, ProductInfoVO.class);
			return productInfoVO;
		}
		productInfoVO = productInfoDao.findOne(productId);
		try
		{
			String value = JsonUtils.objectToJson(productInfoVO);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return productInfoVO;
	}

	@Override
	public List<ProductInfoVO> findProductsInProductIds(List<String> productIds)
	{
		return productInfoDao.findSomeProductsInIds(productIds);
	}

	@Override
	public List<ProductInfoVO> findAll(List<Integer> categoryTypeList)
	{
		return productInfoDao.findAll(categoryTypeList);
	}

	@Override
	public List<ProductInfoVO> findProductsByCategoryType(Integer categoryType)
	{
		return productInfoDao.findAllProductsByCategoryType(categoryType);
	}

}

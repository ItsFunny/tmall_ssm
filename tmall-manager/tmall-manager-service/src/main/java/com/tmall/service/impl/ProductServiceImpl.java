/**
 * 
 */
package com.tmall.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.ProductInfoDao;
import com.tmall.model.ProductInfo;
import com.tmall.service.PictureService;
import com.tmall.service.ProductInfoService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class ProductServiceImpl implements ProductInfoService
{

	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private RestTemplate restTemplate;

	@Transactional
	@Override
	public void addProductInfoAndPicture(ProductInfo productInfo, PictureDTO pictureDTO)
	{
		productInfo.setPictureId(pictureDTO.getPictureId());
		productInfoDao.addProductInfo(productInfo);
		pictureService.addProductPicture(pictureDTO.getPictureId(), productInfo.getProductId(),
				pictureDTO.getPicturePath(), PictureEnums.INDEX_PICTURE.getMsg());
	}
	@Override
	public void addProductInfoAndPictureAndUpdateRedis(ProductInfo productInfo, PictureDTO pictureDTO)
	{
		addProductInfoAndPicture(productInfo, pictureDTO);
		String key = RedisEnums.ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE.getKey() + ":"
				+ productInfo.getCategoryType().toString();
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}

	@Override
	public void updateProductsByCategoryType(Integer productStatus, Integer categoryType)
	{
		productInfoDao.updateProductByCategoryType(productStatus, categoryType);
		String key = RedisEnums.ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE.getKey() + ":" + categoryType.toString();
		String json = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			jedisClient.del(key);
		}
		// ProductInfoAndCategoryVO productInfos =
		// findAllProductsByCategoryType(categoryType);
		// try
		// {
		// String value = JsonUtils.objectToJson(productInfos);
		// jedisClient.set(key, value);
		// } catch (Exception e)
		// {
		// }
	}

	@Override
	public ProductInfoAndCategoryVO findAllProductsByCategoryType(Integer categoryType)
	{
		String url = ServiceEnums.REST_BASE_URL.getUrl() + "/category/detail/{categoryType}";
		ResultVo<ProductInfoAndCategoryVO> resultVo = new ResultVo<>();
		resultVo = restTemplate.getForObject(url, resultVo.getClass(), categoryType);
		Gson gson = new Gson();
		ProductInfoAndCategoryVO productInfoAndCategoryVO = gson.fromJson(gson.toJson(resultVo.getData()),
				ProductInfoAndCategoryVO.class);
		return productInfoAndCategoryVO;
	}

	@Override
	public boolean updateProductInfo(ProductInfo productInfo)
	{
		// String key = RedisEnums.SINGLECATEGORY.getKey() + ":" +
		// productInfo.getCategoryType().toString();
		try
		{
			productInfoDao.update(productInfo);
			// jedisClient.del(key);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void deleteOneProduct(String productId)
	{
		productInfoDao.deleteOneProduct(productId);
	}

	@Override
	public void deleteAndUpdateRedis(String productId, Integer categoryType)
	{
		deleteOneProduct(productId);
		String key = RedisEnums.ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE.getKey() + ":" + categoryType.toString();
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
//		ProductInfoAndCategoryVO productInfoAndCategoryVO = findAllProductsByCategoryType(categoryType);
//		try
//		{
//			String value = JsonUtils.objectToJson(productInfoAndCategoryVO);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//		}
	}
	@Override
	public List<ProductInfoVO> findAllProductByCategoryType(Integer categoryType, Integer storeId)
	{
		String key=String.format(RedisConstant.STORE_CATEGORY_ALL_PRODUCTS, ":"+storeId.toString()+":"+categoryType.toString());
		String json=null;
		List<ProductInfoVO>productInfoVOList=null;
		try
		{
			json=jedisClient.get(key);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(json))
		{
			productInfoVOList=JsonUtils.jsonToList(json, ProductInfoVO.class);
			return productInfoVOList;
		}
		productInfoVOList = productInfoDao.findAllProductsByCategoryTypeAndStoreId(categoryType, storeId);
		try
		{
			String value=JsonUtils.List2Json(productInfoVOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return productInfoVOList;
	}
	@Override
	public List<ProductInfoVO> findStoreAllProducts(Integer storeId, Integer productStatus)
	{
		return productInfoDao.findStoreAllProductsByStoreIdAndStatus(storeId, productStatus);
	}
	@Override
	public void updateProductStatusByIdList(Integer productStatus,List<String> productIdList)
	{
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("productStatus", productStatus);
		params.put("productIdList", productIdList);
		productInfoDao.updateProductStatusByProductList(params);
	}
}

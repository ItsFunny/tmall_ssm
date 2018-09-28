/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.utils.KeyUtils;
import com.tmall.dao.ProductInfoDao;
import com.tmall.model.ProductInfo;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.PictureService;
import com.tmall.store.service.ProductInfoService;
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

	@Override
	public boolean updateProductInfo(ProductInfoVO productInfoVO)
	{
		// String key = RedisEnums.SINGLECATEGORY.getKey() + ":" +
		// productInfo.getCategoryType().toString();
		ProductInfo productInfo = new ProductInfo();
		BeanUtils.copyProperties(productInfoVO, productInfo);
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PRODUCTS,
				":" + productInfoVO.getStoreId() + ":" + productInfoVO.getCategoryType());
		System.out.println(key);
		// SELLER_CATEGORY:ALL_PRODUCTS:1:1
		try
		{
			productInfoDao.update(productInfo);
			jedisClient.del(key);
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
	public void deleteAndUpdateRedis(String productId, Integer categoryType, Integer storeId)
	{
		deleteOneProduct(productId);
		// String key = RedisEnums.ALLPRODUCTS_WITH_SINGLE_CATEGORYTYPE.getKey() + ":" +
		// categoryType.toString();
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PRODUCTS,
				":" + storeId + ":" + categoryType.toString());
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}

	@Override
	public List<ProductInfoVO> findAllProductByCategoryType(Integer categoryType, Integer storeId)
	{
		List<ProductInfoVO> productInfoVOList = productInfoDao.findAllProductsByCategoryTypeAndStoreId(categoryType, storeId);
		return productInfoVOList;
	}

	@Override
	public ProductInfoVO findOneProduct(String productId)
	{
		ProductInfoVO productInfoVO = productInfoDao.findOne(productId);
		return productInfoVO;
	}

	@Override
	public void addOneProduct(ProductInfo productInfo, Integer storeId, PictureDTO pictureDTO)
	{
		String productId = KeyUtils.generateProductId();
		productInfo.setProductId(productId);
		String key = String.format(RedisConstant.STORE_CATEGORY_ALL_PRODUCTS,
				":" + storeId.toString() + ":" + productInfo.getCategoryType());
		productInfoDao.addProductInfo(productInfo);
		// 更新图片
		pictureService.addProductPicture(pictureDTO.getPictureId(), productInfo.getProductId(),
				pictureDTO.getPicturePath(), PictureEnums.INDEX_PICTURE.getMsg());
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}

	@Override
	public void updateProductStatusByCategoryType(Integer categoryType, Integer productStatus,Integer storeId)
	{
		if (productStatus.equals(ProductStatusEnums.DELETE.getStatus()))
		{
			productInfoDao.updateAllProductsByCategoryType(categoryType, storeId);
		} else
		{
			productInfoDao.updateProductByCategoryType(productStatus, categoryType);
		}

	}

}

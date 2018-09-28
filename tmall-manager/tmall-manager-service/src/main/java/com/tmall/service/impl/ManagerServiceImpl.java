/**
 * 
 */
package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.enums.SellerEnums;
import com.tmall.common.utils.FileUtils;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.SellerInfo;
import com.tmall.model.StoreCategory;
import com.tmall.service.ManagerService;
import com.tmall.service.ProductCategoryService;
import com.tmall.service.ProductInfoService;
import com.tmall.service.SellerInfoService;
import com.tmall.service.StoreCategoryService;
import com.tmall.service.StoreService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.ProductInfoVO;

import redis.clients.jedis.Jedis;

/**
 * @author Administrator
 *
 */
@Service
public class ManagerServiceImpl implements ManagerService
{
	@Autowired
	private SellerInfoService sellerInfoService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private JedisClient jedisClient;


	@Transactional
	@Override
	public String deleteOneStoreByStoreId(Integer storeId, StoreDTO storeDTO)
	{
		String error = null;
		String error2 = null;
		// 删除店铺之前先将员工给注销了(禁止登陆,因为在数据库中关系设置了setnull)因为还没做到员工这部分,所以先放着,直接先将创始人的status改为disable
		error2 = sellerInfoService.updateSellerStatusByStoreId(storeId, SellerEnums.DISABLE.getCode());
		FileUtils.deleteFile(storeDTO.getStorePicturePath(), PictureEnums.PICTURE_TYPE_STORE.getMsg());
		//删除店铺
		storeService.deleteStoreByStoreId(storeId);
	
		// 这里还得想办法将图片删除,并且在删除category之前应该先删除图片,因为数据库中已经设置过了CASCADE删除的时候会将product
		// 也一并删除
		List<ProductInfoVO> productInfoVOList = productInfoService.findStoreAllProducts(storeId,
				ProductStatusEnums.UP.getStatus());
		List<String> productIdList = new ArrayList<>();
		if(productInfoVOList.size()>0&&productInfoVOList!=null)
		{
			for (ProductInfoVO productInfoVO : productInfoVOList)
			{
				if (!productIdList.contains(productInfoVO.getProductId()))
				{
					productIdList.add(productInfoVO.getProductId());
				}
				FileUtils.deleteFile(productInfoVO.getProductPicturePath(), PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
			}
		}
		//修改商品状态:
		// 关于更新product的状态,决定更新状态,但是采用更新状态的话最好数据库不采用外键,set null的话无法获取到storeId了
		// 但是又如果不采用外键的话,更新又太麻烦.....但是product表只有一个storeId属性列,这个是不会变的,所以直接更新即可
		if (productIdList != null && productIdList.size() > 0)
		{
			productInfoService.updateProductStatusByIdList(ProductStatusEnums.DOWN.getStatus(), productIdList);
		}
		// 将所有分类删除---数据库会自动删除
//		if (sellerCategoryList != null && sellerCategoryList.size() > 0)
//		{
//			List<Integer> categoryTypeList = new ArrayList<Integer>();
//			for (StoreCategory storeCategory : sellerCategoryList)
//			{
//				if (!categoryTypeList.contains(storeCategory.getProductCategoryType()))
//				{
//					categoryTypeList.add(storeCategory.getProductCategoryType());
//				}
//			}
//			error = productCategoryService.deleteStoreCategory(storeId, categoryTypeList);
//		}
		if (!StringUtils.isEmpty(error))
		{
			if (!StringUtils.isEmpty(error2))
			{
				error = error + error2;
			}
		} else
		{
			error = "成功删除店铺";
		}
		return error;
	}
	@Override
	public String deleteOneStoreAndUpdateRedis(Integer storeId, StoreDTO storeDTO)
	{
		
		String resultStr=deleteOneStoreByStoreId(storeId, storeDTO);
		List<SellerDTO> sellerDTOList = sellerInfoService.findAllSellersByStoreId(storeId);
		if(sellerDTOList!=null&&sellerDTOList.size()>0)
		{
			for (SellerDTO sellerDTO : sellerDTOList)
			{
				String key7 = String.format(RedisConstant.STORE_SELLER_PREFIX, sellerDTO.getOpenid());
				jedisClient.del(key7);
			}
		}
		String key = String.format(RedisConstant.STORE_ALL_SELLERS, ":" + storeId);
		String key2 = RedisConstant.ALL_STORES;
		String key3 = String.format(RedisConstant.STORE_ALL_CATEGORIES, ":" + storeId);
		String key4 = String.format(RedisConstant.STORE_ALL_CATEGORIES_WITH_PICTURES, ":" + storeId);
		String key5 = String.format(RedisConstant.STORE_CATEGORY_ALL_PRODUCTS, ":" + storeId);
		String key6 = String.format(RedisConstant.STORE_CATEGORY_ALL_PROPERTIE, ":" + storeId);
		try
		{
			jedisClient.del(key6);
			jedisClient.del(key5);
			jedisClient.del(key4);
			jedisClient.del(key3);
			jedisClient.del(key2);
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
		return resultStr;
	}
}

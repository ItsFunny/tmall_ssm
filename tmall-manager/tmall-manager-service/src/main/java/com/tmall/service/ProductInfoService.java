/**
 * 
 */
package com.tmall.service;


import java.util.List;

import com.tmall.common.dto.PictureDTO;
import com.tmall.model.ProductInfo;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
public interface ProductInfoService
{
	
	/*
	 * 增
	 * 增加一个商品和图片
	 * 更新上一步
	 * 增加并且更新redis
	 */
	void addProductInfoAndPicture(ProductInfo productInfo,PictureDTO pictureDTO);
	void addProductInfoAndPictureAndUpdateRedis(ProductInfo productInfo,PictureDTO pictureDTO);
	
	void updateProductsByCategoryType(Integer productStatus,Integer categoryType);
	/*
	 * 查
	 * 查找某类目下的所有商品
	 * seller:查找某类目下的所有产品,附带其他东西
	 * 查找某店铺下的所有商品,类型可选
	 */
	ProductInfoAndCategoryVO findAllProductsByCategoryType(Integer categoryType);
	List<ProductInfoVO>findAllProductByCategoryType(Integer categoryType,Integer storeId);
	List<ProductInfoVO>findStoreAllProducts(Integer storeId,Integer productStatus);
	
	/*
	 * 更新
	 * 更新商品信息
	 * 批量更新商品状态
	 */
	boolean updateProductInfo(ProductInfo productInfo);
	
	void updateProductStatusByIdList(Integer productStatus,List<String>productIdList);
	
	
	
	
	/*
	 * 删
	 * 删除某个商品,(完全删除,不是上架下架)
	 * 删除某个商品,并且更新redis
	 */
	void deleteOneProduct(String productId);
	void deleteAndUpdateRedis(String productId,Integer categoryType);
}

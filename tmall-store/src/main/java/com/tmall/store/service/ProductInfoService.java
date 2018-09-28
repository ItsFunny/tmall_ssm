/**
 * 
 */
package com.tmall.store.service;


import java.util.List;

import com.tmall.common.dto.PageDTO;
import com.tmall.common.dto.PictureDTO;
import com.tmall.model.ProductInfo;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
public interface ProductInfoService
{
	
	/*
	 * 查
	 * 查找某类目下的所有商品
	 * seller:查找某类目下的所有产品,附带其他东西
	 */
	/**
	 * @param categoryType 店铺下的某个类目编号
	 * @param storeId 店铺的id 可以为空,为空就是查找这个类目下的所有商品,不限定于哪个店铺
	 * @return
	 */
	List<ProductInfoVO>findAllProductByCategoryType(Integer categoryType,Integer storeId);

	/**
	 * @param productId 商品的主键
	 * @return 返回查找到的商品
	 */
	ProductInfoVO findOneProduct(String productId);
	
	/**
	 * 更新商品信息
	 * @param productInfo 所需要更新的商品的主体
	 * @param storeId	这个商品属于哪个店铺
	 * @return 更新成功返回true 否则返回失败
	 */
	boolean updateProductInfo(ProductInfoVO productInfoVO);
	
	/**
	 * @param productInfo	要添加的主体信息
	 * @param storeId	这个商品属于哪家店铺
	 * @param picturePath	添加商品还需要更新图片
	 */
	void addOneProduct(ProductInfo productInfo,Integer storeId,PictureDTO pictureDTO);
	
	/*
	 * 删
	 * 删除某个商品,(完全删除,不是上架下架)
	 * 删除某个商品,并且更新redis
	 */
	void deleteOneProduct(String productId);
	void deleteAndUpdateRedis(String productId,Integer categoryType,Integer storeId);
	/*
	 * 改
	 */
	/**
	 * 修改某店铺下类目下的所有商品的状态,上架还是下架还是删除
	 * @param categoryType
	 * @param productStatus
	 * @param storeId
	 */
	void updateProductStatusByCategoryType(Integer categoryType,Integer productStatus,Integer storeId);
}

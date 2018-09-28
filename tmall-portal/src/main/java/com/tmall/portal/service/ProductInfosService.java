/**
 * 
 */
package com.tmall.portal.service;

import java.util.List;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.CartDTO;
import com.tmall.model.ProductInfo;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * 
 * @author Administrator
 *
 */
public interface ProductInfosService
{
	
	/*
	 * 查找
	 * 查找购物车上的商品 List
	 * 从数据库中查的一个商品
	 * 查询商品属于哪家店铺
	 */
	List<ProductInfoVO> findProductsInIds(List<String>productIds);
	ProductInfoVO findOneProductFromDB(String productId);
	Integer findProductStoreIdByProductId(String productId);
	/*
	 * 显示所有上架商品
	 */
	ResultVo<List<ProductInfoAndCategoryVO>> showAllProducts();
	
	/*
	 * 显示某个店铺下单个类目的所有商品
	 */
	ResultVo<ProductInfoAndCategoryVO> showAllProductsByCategoryTypeAndStoreId(Integer categoryType,Integer storeId);
	
	/*
	 * 查询某个类目下的所有商品
	 */
	ResultVo<List<ProductInfoVO>>findAllProductsByCategoryType(Integer categoryType);
	
	/*
	 * 显示一个商品
	 */
	ResultVo<ProductInfoVO> findOneProduct(String productId);
	/*
	 * 更新
	 * 更新商品的销售数量
	 */
	void update(ProductInfo productInfo);
	void updateProductSelledNumber(ProductInfoVO productInfo,Integer buyNum);
	/*
	 * 加库存
	 */
	void increateStock(List<CartDTO> cartDTO);
	
	/*
	 * 减库存
	 */
	void decreateStock(List<CartDTO> cartDTO);
}

/**
 * 
 */
package com.tmall.portal.service;

import java.util.List;

import com.tmall.dto.StoreDTO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
public interface StoreService
{
	/**
	 * 查询所有的店铺
	 * @return
	 */
	List<StoreDTO>findAllStores();
	
	/**
	 * 查询这个店铺下的所有类目
	 * @param storeId	店铺主键
	 * @return
	 */
	List<ProductCategoryVO>findStoreAllCategoryVOs(Integer storeId);
	
	/**
	 * 查询店铺下的所有商品,无论是上架还是下架
	 * @param storeId
	 * @return
	 */
	List<ProductInfoVO>findStoreAllProducts(Integer storeId);
	
	/**
	 * 用于展示首页,显示店铺下所有的类目和产品
	 * @param storeId
	 * @return
	 */
	List<ProductInfoAndCategoryVO>findStoreAllCategoryAndAllProducts(Integer storeId);
	
}

/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import com.tmall.dto.SellerDTO;
import com.tmall.model.StoreCategory;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
public interface StoreCategoryService
{
	/*
	 * 查
	 */
	/**
	 * 查找这个店铺的所有类目
	 * @param sellerInfo
	 * @return
	 */
	List<StoreCategory>findSellerCategoryList(SellerDTO sellerInfo);
	
	List<ProductCategoryVO>findStoreCategoryList(Integer storeId);
	/*
	 * 增
	 */
	void addSellerStoreCategory(Integer storeId,Integer categoryType);
}

/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import com.tmall.model.ProductCategory;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
public interface ProductCategoryService
{
	/*
	 * 查
	 */
	/**
	 * 查询卖家店铺的所有类目
	 * @param categoryTypeList
	 * @param storeId
	 * @return
	 */
	List<ProductCategoryVO>findSellerCategoryList(List<Integer>categoryTypeList,Integer storeId);
	
	/*
	 * 增
	 */
	/**
	 * 添加某店铺下的一个类目
	 * @param productCategory	添加的主体
	 * @param storeId	店铺的id
	 */
	void addCategory(ProductCategoryVO productCategory,Integer storeId);
	void addCategoryAndUpdateRedis(ProductCategoryVO productCategory,Integer storeId);
	
	/*
	 * 改
	 */
	/**
	 * 更新类目的名称和图片
	 * @param productCategory	
	 * @param storeId	店铺的id
	 * @param picturePath	更新的图片地址
	 */
	void updateSellerCategoryByCategoryType(ProductCategory productCategory,Integer storeId,String picturePath);
	void updateSellerCategoryAndUpdateRedis(ProductCategory productCategory,Integer storeId,String picturePath);
	/*
	 * 删
	 */
	void deleteCategoryByCategoryType(Integer categoryType,Integer productStatus,Integer storeId);
	void deleteCategoryAndUpdateRedis(Integer categoryType,Integer productStatus,Integer storeId);
	
}

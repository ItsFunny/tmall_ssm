/**
 * 
 */
package com.tmall.store.service;

import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
public interface CategoryPictureService
{
	/*
	 * 改
	 */
	void updateCategoryPictureByCategoryType(String categoryPicturePath,Integer categoryType);
	
	/*
	 * 增
	 */
	void addCategoryPicture(ProductCategoryVO productCategoryVO);

}

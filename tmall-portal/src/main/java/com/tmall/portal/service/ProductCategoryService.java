/**
 * 
 */
package com.tmall.portal.service;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.ProductCategory;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
public interface ProductCategoryService
{
	/*
	 * 找到一个分类
	 */
	ResultVo<ProductCategoryVO> findOne(Integer categoryType,Integer storeId);
	
	ResultVo<ProductCategory> coverFindOne(Integer categoryType);
}

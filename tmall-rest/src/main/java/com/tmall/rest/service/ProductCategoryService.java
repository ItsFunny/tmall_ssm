/**
 * 
 */
package com.tmall.rest.service;

import java.util.List;

import com.tmall.model.ProductCategory;
import com.tmall.vo.ProductCategoryVO;


/**
 * @author Administrator
 *
 */
public interface ProductCategoryService
{
	//查询单个分类的所有商品
	ProductCategoryVO findOne(Integer categoryType);
	
	//显示所有分类
	List<ProductCategoryVO> findAllCategories();
	//显示所有的分类,并且查询其所有的图片
	List<ProductCategoryVO>findAllCategoriesAndPictures();
	
	//显示上架的所有分类
	List<ProductCategoryVO> findAllProductCategoriesInAllCategoryTypes(List<Integer> categoryTypeList);

	
	//新增和更新
	
	
	
}

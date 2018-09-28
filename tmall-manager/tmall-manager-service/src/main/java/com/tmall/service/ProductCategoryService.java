/**
 * 
 */
package com.tmall.service;

import java.util.List;

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
	List<ProductCategoryVO>findSellerCategoryList(List<Integer>categoryTypeList,Integer storeId);
	/*
	 * 增加
	 * 增加一个类目
	 * 增加一个类目对应的图片
	 * 上述二合一
	 * 更新redis上的数据,为啥要另起一个呢,是因为上述的2个操作要使用@Transactional,所以不可以和redis一起,因此再写一个
	 */
	void addCategory(ProductCategoryVO productCategory);
	void addCategoryPicture(ProductCategoryVO productCategory);
	void addCategoryAndPicture(ProductCategoryVO productCategoryVO,String path);
	void addAndUpdateCategoryAndPicture(ProductCategoryVO productCategoryVO,String pathString);
	
	
	
	
	
	
	
	List<ProductCategoryVO> findAllCategories();
	
	/*
	 * 删除一个分类
	 * 批量删除分类
	 */
	void deleteCategory(Integer categoryType);
	String deleteStoreCategory(Integer storeId,List<Integer>categoryTypeList);
	/*
	 * 更新
	 * 更新类目的名称
	 * 更新图片地址
	 * 上述二合一
	 */
	void updateCategoryName(String categoryName,Integer categoryType);
	void updateCategoryPicture(String picturePath,Integer categoryType);
	void updateCategory(String categoryName,String picturePath,Integer categoryType);
}

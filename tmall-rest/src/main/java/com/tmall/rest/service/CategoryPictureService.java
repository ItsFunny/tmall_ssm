/**
 * 
 */
package com.tmall.rest.service;

import java.util.List;

import com.tmall.model.CategoryPicture;


/**
 * @author Administrator
 *
 */
public interface CategoryPictureService
{
	void updateCategoryPicture(String picturePath,Integer categoryType);
	/*
	 * 查
	 * 查找类目对应的图片
	 */
	List<CategoryPicture>findPicturesByCategoryTypeList(List<Integer>categoryTypeList);
}

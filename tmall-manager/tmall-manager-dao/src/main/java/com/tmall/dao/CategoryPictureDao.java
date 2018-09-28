/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tmall.model.CategoryPicture;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */

@Mapper
public interface CategoryPictureDao
{
	/*
	 * 更新
	 * 更新类目的商品图片地址
	 */
	void updateCategoryPicture(@Param("categoryPicturePath")String categoryPicturePath,@Param("categoryType")Integer categoryType);
	/*
	 * 增加
	 * 增加一个商品及其对应的图片存放地址
	 */
	@Insert("insert into category_picture (category_type,category_picture_path) values (#{categoryType},#{categoryPicturePath})")
	void addCategoryPicture(ProductCategoryVO productCategoryVO);
	/*
	 * 查
	 * 查找类目对应的图片 in查询
	 */
	List<CategoryPicture>findPicturesByCategoryTypeList(List<Integer>categoryType);
}

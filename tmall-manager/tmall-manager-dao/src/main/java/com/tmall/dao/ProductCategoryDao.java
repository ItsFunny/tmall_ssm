/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tmall.vo.ProductCategoryVO;


/**
 * @author Administrator
 *
 */
@Mapper
public interface ProductCategoryDao
{
	/*
	 * 增加
	 * 增加一个分类
	 */
	@Insert("insert into product_category (category_name,category_type) values (#{categoryName},#{categoryType})")
	void addCategory(ProductCategoryVO productCategory);
	/*
	 * 获取所有的分类
	 * 获取所有的分类,并且获取其对应的图片
	 */
	@Select("select * from product_category")//这里准备注释掉,别忘记在manager中也要添加一个xml,不过好像下面这个是我需要的已经有了
	List<ProductCategoryVO> findAllProductCategories();

	List<ProductCategoryVO>findAllCategoriesAndPictures(List<Integer> categoryTypes);
	
	
	/*
	 * 获取某个分类
	 */
	@Select("select * from product_category where category_type=#{categoryType}")
	ProductCategoryVO findOne(Integer categoryType);
	
	
	/**
	 * 查询参数中的类目
	 * @param categoryTypes
	 * @return
	 */
	List<ProductCategoryVO> findAllProductCategoriesInCategoryType(List<Integer> categoryTypes);
	
	/*
	 * 更新
	 * 更新类目的名称---->弃用
	 */
	@Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
	void updateProductCategoryName(@Param("categoryName")String categoryName,@Param("categoryType")Integer categoryType);
	/*
	 * 删除
	 * 删除某个分类
	 * 批量删除
	 */
	@Delete("delete from product_category where category_type=#{categoryType}")
	void deleteCategory(Integer categoryType);
	
	void deleteCategoryList(List<Integer>categoryTypeList);
	
}

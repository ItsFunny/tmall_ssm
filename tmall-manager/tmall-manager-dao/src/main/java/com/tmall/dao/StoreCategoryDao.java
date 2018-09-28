/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.model.StoreCategory;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Mapper
public interface StoreCategoryDao
{
	// del
	@Select("select * from store_category where store_id=#{storeId}")
	List<StoreCategory> findStoreCategoryByStoreId(Integer storeId);

	@Select("select a.*,b.*  from store_category a,product_category b where a.store_id=#{storeId} and a.product_category_type=b.category_type")
	List<ProductCategoryVO> findStoreCategoryList(Integer storeId);

	@Insert("insert into store_category (product_category_type,store_id) values (#{productCategoryType},#{storeId})")
	void addStoreCategory(StoreCategory storeCategory);
}

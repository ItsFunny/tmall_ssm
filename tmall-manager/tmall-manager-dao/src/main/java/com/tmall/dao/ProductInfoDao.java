package com.tmall.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tmall.model.ProductInfo;
import com.tmall.vo.ProductInfoVO;

/*
 * 
 */
@Mapper
public interface ProductInfoDao
{
	/*
	 * 新增一个产品
	 */
//	@Insert("insert into product_info (" + 
//			"		product_id,product_name,product_sub_title,product_price,product_promote_price,\r\n" + 
//			"		product_stock,product_description,picture_id,product_status,category_type\r\n" + 
//			"		)" + 
//			"		values(" + 
//			"		#{productId},#{productName},#{productSubTitle},#{productPrice},#{productPromotePrice},#{productStock},\r\n" + 
//			"		#{productDescription},#{pictureId},#{productStatus},#{categoryType}\r\n" + 
//			"		)")
	void addProductInfo(ProductInfo productInfo);
	
	
//	
//	/* 这是错误的,方向搞错了,应该是商品->类目  而不是类目->商品,,,因为有个条件:上架商品
//	 * 获取某个分类下的所有商品
//	 */
//	List<ProductInfo> findAllProductsInCategoryType(List<Integer> categoryTypes);
	/*
	 * 查询所有上架商品
	 * 查询所有商品,并且查询到他对应的图片
	 */
	@Select("select * from product_info where product_status=0")
	List<ProductInfoVO> findUpAll();
	List<ProductInfoVO>findAll(List<Integer>categoryTypeList);
	/*
	 * 查询单个产品
	 */
//	@Select("select * from product_info where product_id=#{productId}")
	ProductInfoVO findOne(String productId);  
	
	/*
	 * 查询一串商品
	 * 参数为list
	 */
	List<ProductInfoVO>findSomeProductsInIds(List<String>productIds);
	/*
	 * 查询某个类型下的所有产品
	 * 查询某个类目下的所有产品,并且附带图片等
	 * 查询商品属于哪家店铺(storeId)
	 * 查询某个店铺下的所有商品,可以按状态查询(上架下架)
	 * 查询所有的商品,不管状态
	 */
	List<ProductInfoVO> findAllProductsByCategoryType(Integer categoryType); 
	List<ProductInfoVO>findAllProductsByCategoryTypeAndStoreId(@Param("categoryType")Integer categoryType,@Param("storeId")Integer storeId);
	@Select("select store_id from product_info where product_id=#{productId}")
	Integer findProductStoreIdByProductId(String productId);
	@Select("select a.product_id,a.store_id,a.picture_id,a.product_status,b.picture_path as productPicturePath,b.picture_id from product_info a,picture b where a.store_id=#{storeId} and a.product_status=#{productStatus} and a.picture_id=b.picture_id")
	List<ProductInfoVO> findStoreAllProductsByStoreIdAndStatus(@Param("storeId")Integer storeId,@Param("productStatus")Integer productStatus);
	@Select("select a.*,b.picture_path as productPicturePath,b.picture_id from product_info a,picture b where a.store_id=#{storeId} and a.picture_id=b.picture_id")
	List<ProductInfoVO>findStoreAllProducts(Integer storeId);
	
	/*
	 * 更新
	 * 更新某个商品所有信息
	 * 更新某个类目下所有的商品状态
	 */
	void update(ProductInfo productInfo);
	@Update("update product_info set product_status=#{productStatus} where category_type=#{categoryType}")
	void updateProductByCategoryType(@Param("productStatus")Integer productStatus,@Param("categoryType")Integer categoryType);	
	
	void updateProductStatusByProductList(Map<String, Object>params);
	
	/*
	 * 删除某个产品
	 * 
	 */
	@Delete("delete from product_info where product_id=#{productId}")
	void deleteOneProduct(String productId);

	@Delete("delete from product_info where category_type=#{categoryType} and store_id=#{storeId}")
	void updateAllProductsByCategoryType(@Param("categoryType")Integer categoryType,@Param("storeId")Integer storeId);
	
}

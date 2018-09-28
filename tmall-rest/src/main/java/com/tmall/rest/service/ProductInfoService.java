/**
 * 
 */
package com.tmall.rest.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.tmall.vo.ProductInfoVO;


/**
 * @author Administrator
 *
 */
public interface ProductInfoService
{

	/*
	 * 查询所有上架商品
	 * 查询多个类目下的所有商品,并且附带图片
	 */
	List<ProductInfoVO>findUpAll();
	List<ProductInfoVO>findAll(List<Integer>categoryTypeList);
	/*
	 * 查询某店铺某类目的所有商品
	 */
	List<ProductInfoVO> findAllProductsByCategoryTypeAndStoreId(Integer categoryType,Integer storeId) throws IllegalAccessException, InvocationTargetException;
	/*
	 * 查询某类目下的所有商品,包括所有店铺
	 */
	List<ProductInfoVO>findProductsByCategoryType(Integer categoryType);
	
	/*
	 *查询单个商品
	 */
	ProductInfoVO findOne(String productId);
	
	/*
	 * 查询一串商品
	 * 参数为list
	 */
	List<ProductInfoVO> findProductsInProductIds(List<String>productIds);
	
}

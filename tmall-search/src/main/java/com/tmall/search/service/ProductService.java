/**
 * 
 */
package com.tmall.search.service;

import java.util.List;

import com.tmall.vo.ProductInfoAndCategoryVO;

/**
 * @author Administrator
 *
 */
public interface ProductService
{
	List<ProductInfoAndCategoryVO> findAll();
}

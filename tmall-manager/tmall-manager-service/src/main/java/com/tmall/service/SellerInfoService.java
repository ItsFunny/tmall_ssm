/**
 * 
 */
package com.tmall.service;

import java.util.List;

import com.tmall.dto.SellerDTO;

/**
 * @author Administrator
 *
 */
public interface SellerInfoService
{
	/*
	 * 查
	 */
	/**
	 * 
	 * @param storeId	店铺的id
	 * @param start	用于分页
	 * @param end	用于分页
	 * @return 这个店铺下的所有员工信息
	 */
	List<SellerDTO>findStoreAllSellers(Integer storeId,Integer start,Integer end);
	
	/**
	 * 查找这个店铺的所有人员
	 * @param storeId
	 * @return
	 */
	List<SellerDTO>findAllSellersByStoreId(Integer storeId);

	
	String updateSellerStatusByStoreId(Integer storeId,Integer status);
}

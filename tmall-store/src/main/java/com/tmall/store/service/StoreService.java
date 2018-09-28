/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;

/**
 * @author Administrator
 *
 */

public interface StoreService
{
	/*
	 * 增
	 */
	/**
	 * 添加店铺并且更新redis中的数据
	 * @param sellerDTO
	 * @param picture
	 */
	Integer addStoreAndUpdateRedis(SellerDTO sellerDTO,String picturePath);
	
	/*
	 * 查
	 */
	/**
	 * 查询所有的店铺
	 * @return
	 */
	List<SellerDTO>findAllStores();
	
	
	/**
	 * 依据店铺名称查询
	 * @param storeName
	 * @return
	 */
	SellerDTO findStoreByStoreName(String storeName);
	
	List<StoreDTO> findSellerStoreInfo(String userId);
	/*
	 * 删
	 */
	
	/*
	 * 改
	 */
}

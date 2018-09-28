/**
 * 
 */
package com.tmall.service;


import com.tmall.dto.StoreDTO;

/**
 * manager操作汇总
 * @author Administrator
 *
 */
public interface ManagerService
{
	/*
	 * 107  16:20 因为采用了数据库的设置方式,可以次级删除,所以不需要storeCategoryList了
	 */
//	String deleteOneStoreByStoreId(Integer storeId, StoreDTO storeDTO, List<StoreCategory> sellerCategoryList);
	String deleteOneStoreByStoreId(Integer storeId, StoreDTO storeDTO);
//	String deleteOneStoreAndUpdateRedis(Integer storeId, StoreDTO storeDTO, List<StoreCategory> sellerCategoryList);
	String deleteOneStoreAndUpdateRedis(Integer storeId, StoreDTO storeDTO);
}

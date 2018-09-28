/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import com.tmall.model.OrderMaster;

/**
 * @author Administrator
 *
 */
public interface OrderMasterService
{

	/*
	 * 查
	 * 查找某店铺的所有ordermaster
	 */
	List<OrderMaster>findStoreALLOrderMaster(Integer storeId);
	
}

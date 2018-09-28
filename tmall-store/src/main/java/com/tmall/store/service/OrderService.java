/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import com.tmall.dto.OrderDTO;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
public interface OrderService
{
	/*
	 * 查
	 * 查找某店铺的所有ordermaster
	 */
	
	List<OrderDTO>findStoreAllOrders(Integer storeId);

	

}

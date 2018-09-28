/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
public interface OrderDetailService
{
	/*
	 * 查
	 */
	/**
	 * 返回所有ordermaster下的所有orderdetail
	 * @param orderMasterIdList orderMaster的id集合
	 * @return
	 */
	List<OrderDetailVO>findOrderDetailListByOrderMasterIDList(List<String>orderMasterIdList);
	
	
	OrderDetailVO findOneOrderDetail(String orderDetailId);
	
	
	/*
	 * 改
	 */
	void updateOrderDetail(OrderDetailVO orderDetailVO);
}

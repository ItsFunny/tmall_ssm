/**
 * 
 */
package com.tmall.service;

import java.util.List;

import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
public interface OrderService
{
	/*
	 * 查
	 * 查找所有订单,是所有ordermaster订单
	 * 查找所有订单,包含了所有的orderMaster和对应的orderDetail
	 * 查找单个订单
	 */
	List<OrderMaster>findAllOrderMasters();
	List<OrderDTO>findAllOrders();
	OrderDetailVO findOneOrderDetail(String orderDetailId);
	
	/*
	 * 更新
	 * 更新一个商品详情
	 * 更新商品,并且更新redis
	 */
	void updateOrderDetail(OrderDetailVO orderDetailVO);
	void updateOrderDetailAndUpdateOrderRedis(OrderDetailVO orderDetailVO,String openid);

}

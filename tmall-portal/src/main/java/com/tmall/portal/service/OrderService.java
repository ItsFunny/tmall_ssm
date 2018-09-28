/**
 * 
 */
package com.tmall.portal.service;

import java.util.LinkedList;
import java.util.List;

import com.tmall.common.exception.TmallPortalException;
import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
public interface OrderService
{
	OrderDTO create(OrderDTO orderDTO,LinkedList<String>productIds,LinkedList<String>buyNums,LinkedList<String> contents);
	
	
	OrderDetailVO findOneOrderDetail(String orderDetailId);
	/*
	 * 查
	 * 查找一个主订单
	 * 查找主订单下的所有订单详情
	 * 查找单个用户的所有ordermaster
	 */
	OrderMaster findOneOrderMaster(String orderId);
	List<OrderDetailVO>findOrderDetailListrWithOrderMaster(String orderId) ;
	List<OrderDTO> findUserAllOrderMastersByOpenid(String openid);
	
	/**
	 * 查询一个订单,包括ordermaster和orderDetail
	 * @param orderId
	 * @return
	 * @author joker 
	 * @throws TmallPortalException 
	 * @date 创建时间：2018年1月12日 下午12:57:26
	 */
	OrderDTO findOneOrder(String orderId) throws TmallPortalException;
	
	/**
	 * @param openid
	 * @param start
	 * @param end
	 * @return
	 * @author joker 
	 * @date 创建时间：2018年1月12日 下午12:55:59
	 * 
	 */
	List<OrderDTO>findUserOrderMasterByOpenidAndBetween(String openid,Integer start,Integer end);
	
	/**
	 * 109:依据type和openid查询用户ordermaster下的orderdetail
	 * @param type
	 * @param openid
	 * @param orderIds
	 * @return
	 */
	List<OrderDetailVO>findUserOrderDetailVOListByOrderIds(String type, String openid, List<String> orderIds);
	
	/**
	 * 查找单个用户的所有orderdetail和ordermaster
	 * @param orderStatus
	 * @param openid
	 * @return
	 */
	List<OrderDTO> findUserALLOrders(String orderStatus,String openid);
	/*
	 * 更新
	 * 更新订单详情
	 * 更新主订单
	 * 更新订单详情和主订单 这种情况通常是master里只有一个detail
	 * 更新订单详情下的
	 */
	void updateOrderDetail(OrderDetailVO orderDetail,String openid);
	void updateOrderMaster(OrderMaster orderMaster);
	void updateOrderMasterAndOrderDetail(OrderMaster orderMaster,OrderDetailVO orderDetailVO);
	
}

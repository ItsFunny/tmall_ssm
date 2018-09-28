/**
 * 
 */
package com.tmall.rest.service;

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
	 * 查找单个用户的所有ordermaster
	 * 查找用户的所有特定类型的oredermaster
	 * 109:查找用户ordermaster对应的所有orderDetail
	 * 109:依据type和openid查询用户ordermaster下的orderdetail ---->打算删除 110:不可删除
	 * 查找单个用户的所有orderdetail
	 */
	List<OrderDTO> findUserAllOrderMastersByOpenid(String openid);
	List<OrderDTO>findUserAllOrderMaStersByOpenIdAndType(String openid,String type);
	List<OrderDetailVO> findUserAllOrderDetailsInOrderIds(String openid,List<String>orderIds);
	/*
	 * 110;取消方法,portal系统自己查询,不调用接口
	   List<OrderDetailVO>findUserOrderDetailVOListByOrderIds(String type, String openid, List<String> orderIds);
     * List<OrderDTO> findUserALLOrders(String orderStatus,String openid);
	 */
	
	/**
	 * 通过paystaus查询ordermaster,获取相同父状态的master(等待退款和成功退款的父状态都为已取消)
	 * @param openid	用户的openid
	 * @param start	起始条件
	 * @param end	终止条件
	 * @return
	 */
	List<OrderDTO>findUserOrderMasterByOpenidAndBetween(String openid,Integer start,Integer end);
}

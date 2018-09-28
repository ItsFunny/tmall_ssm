/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.OrderDetailDao;
import com.tmall.store.service.OrderDetailService;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService
{
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Override
	public List<OrderDetailVO> findOrderDetailListByOrderMasterIDList(List<String> orderMasterIdList)
	{
		return orderDetailDao.findAllDetailsWithAllOrderMasters(orderMasterIdList);
	}

	@Override
	public OrderDetailVO findOneOrderDetail(String orderDetailId)
	{
		return orderDetailDao.findOneOrderDetail(orderDetailId);
	}

	@Override
	public void updateOrderDetail(OrderDetailVO orderDetailVO)
	{
		orderDetailDao.updateOrderDetail(orderDetailVO);
	}

}

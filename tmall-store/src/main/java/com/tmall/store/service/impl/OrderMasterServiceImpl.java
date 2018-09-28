/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.OrderMasterDao;
import com.tmall.model.OrderMaster;
import com.tmall.store.service.OrderMasterService;

/**
 * @author Administrator
 *
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService
{
	@Autowired
	private OrderMasterDao orderMasterDao;
	@Override
	public List<OrderMaster> findStoreALLOrderMaster(Integer storeId)
	{
		List<OrderMaster> orderMasterList = orderMasterDao.findStoreOrderMasterByStoreId(storeId);
		return orderMasterList;
	}

}

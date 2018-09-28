/**
 * 
 */
package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.dao.OrderDetailDao;
import com.tmall.dao.OrderMasterDao;
import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;
import com.tmall.model.Picture;
import com.tmall.service.OrderService;
import com.tmall.service.PictureService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService
{
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderMasterDao orderMasterDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private PictureService pictureService;

	@Override
	public List<OrderMaster> findAllOrderMasters()
	{
		return orderMasterDao.findAllOrderMasters();
	}

	/*
	 * 这里有大问题
	 */
	@Override
	public List<OrderDTO> findAllOrders()
	{
//		String json = null;
//		String key = RedisEnums.ALL_USERS_ALL_ORDERS.getKey();
		List<OrderDTO> orderDTOList = new ArrayList<>();
//		try
//		{
//			json = jedisClient.get(key);
//		} catch (Exception e)
//		{
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			JSONArray jsonArray = JSONArray.fromObject(json);
//			orderDTOList = JSONArray.toList(jsonArray, OrderDTO.class);
//			return orderDTOList;
//		}
		List<OrderMaster> orderMasters = findAllOrderMasters();
		List<String> orderIdList = new ArrayList<String>();
		for (OrderMaster orderMaster : orderMasters)
		{
			orderIdList.add(orderMaster.getOrderId());
		}
		List<OrderDetailVO> orderDetails = orderDetailDao.findAllDetailsWithAllOrderMasters(orderIdList);
		List<String> orderDetailPictureIdList = new ArrayList<>();
		for (OrderDetailVO orderDetailVO : orderDetails)
		{
			if (!orderDetailPictureIdList.contains(orderDetailVO.getProductPitcureId()))
			{
				orderDetailPictureIdList.add(orderDetailVO.getProductPitcureId());
			}
		}
		List<Picture> detalPictureList = pictureService.findOrderDetalPicture(orderDetailPictureIdList);
		for (OrderMaster orderMaster : orderMasters)
		{
			OrderDTO orderDTO = new OrderDTO();
			BeanUtils.copyProperties(orderMaster, orderDTO);
			List<OrderDetailVO> orderDetailList = new ArrayList<>();
			if (!orderDetails.isEmpty())
			{
				for (OrderDetailVO orderDetail : orderDetails)
				{
					for (Picture picture : detalPictureList)
					{
						if (picture.getPictureId().equals(orderDetail.getProductPitcureId()))
						{
							orderDetail.setPicturePath(picture.getPicturePath());
						}
					}
					if (orderDetail.getOrderId().equals(orderMaster.getOrderId()))
					{
						orderDetailList.add(orderDetail);
					}
				}
			}
			orderDTO.setOrderDetailList(orderDetailList);
			orderDTOList.add(orderDTO);
		}
		// 格式化日期,这样格式化总感觉很蠢
		for (OrderDTO orderDTO : orderDTOList)
		{
			String date = ConverterUtils.date2SimpleDateString(orderDTO.getCreateDate());
			String payDate=ConverterUtils.date2SimpleDateString(orderDTO.getPayDate());
			orderDTO.setCreateDateStr(date);
			orderDTO.setPayDateStr(payDate);
			for (OrderDetailVO orderDetailVO : orderDTO.getOrderDetailList())
			{
				if (orderDetailVO.getDeliverDate() != null)
				{
					String dateT = ConverterUtils.date2SimpleDateString(orderDetailVO.getDeliverDate());
					orderDetailVO.setDeliverDateStr(dateT);
				}
				if (orderDetailVO.getReceiptDate() != null)
				{
					String dateT = ConverterUtils.date2SimpleDateString(orderDetailVO.getReceiptDate());
					orderDetailVO.setReceiptDateStr(dateT);
				}
				if (orderDetailVO.getOrderStatus().equals(OrderStatusEnum.CANCLE.getCode())
						&& orderDetailVO.getUpdateDate() != null)
				{
					String dateT = ConverterUtils.date2SimpleDateString(orderDetailVO.getUpdateDate());
					orderDetailVO.setUpdateDateStr(dateT);
				}
			}
		}
//		try
//		{
//			String value = JsonUtils.List2Json(orderDTOList);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//		}
		return orderDTOList;
	}

	@Override
	public OrderDetailVO findOneOrderDetail(String orderDetailId)
	{
		OrderDetailVO order = orderDetailDao.findOneOrderDetail(orderDetailId);
		if (order == null)
		{
			throw new TmallException(ResultEnums.ORDER_NOT_EXIT);
		}
		return order;
	}

	@Override
	public void updateOrderDetail(OrderDetailVO orderDetailVO)
	{
		orderDetailDao.updateOrderDetail(orderDetailVO);
	}

	/*
	 * 这样做感觉是不对的
	 * 因为如果订单数量太多,就太那啥了,感觉
	 */
	@Override
	public void updateOrderDetailAndUpdateOrderRedis(OrderDetailVO orderDetailVO,String openid)
	{
//		String key = RedisEnums.ALL_USERS_ALL_ORDERS.getKey();
//		String key2= RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" + "ORDER_DETAIL" + ":" + "all";
		updateOrderDetail(orderDetailVO);
//		try
//		{
////			jedisClient.del(key);
//			jedisClient.del(key2);
//		} catch (Exception e)
//		{
//			logger.error("[订单更新]删除所有订单的key出现错误");
//		}
//		List<OrderDTO> orderDTOList = findAllOrders();
//		try
//		{
//			String value = JsonUtils.List2Json(orderDTOList);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			logger.error("[订单更新]向redis写入所有订单时候出错");
//		}
	}

}

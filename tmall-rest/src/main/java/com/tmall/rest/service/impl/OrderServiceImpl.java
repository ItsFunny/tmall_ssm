/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmall.common.enums.PayStatusEnum;
import com.tmall.dao.OrderMasterDao;
import com.tmall.dto.OrderDTO;
import com.tmall.rest.service.OrderService;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService
{
	@Autowired
	private OrderMasterDao orderMasterDao;


	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public List<OrderDTO> findUserAllOrderMastersByOpenid(String openid)
	{
		List<OrderDTO> orderDTOs = null;
		orderDTOs = orderMasterDao.findUserOrderMastersByOpenid(openid);
		return orderDTOs;
	}

	// @Override
	// public List<OrderDetailVO> findUserAllOrderDetailsInOrderIds(String openid,
	// List<String> orderIds)
	// {
	//
	// String json = null;
	// List<OrderDetailVO> orderDetails = null;
	// String key = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" +
	// "ORDER_DETAIL";
	// try
	// {
	// json = jedisClient.get(key);
	// } catch (Exception e)
	// {
	// logger.error("[获取用户订单]redis挂了");
	// }
	// if (!StringUtils.isEmpty(json))
	// {
	// JSONArray jsonArray = JSONArray.fromObject(json);
	// orderDetails = JSONArray.toList(jsonArray, OrderDetail.class);
	// // orderDetails = JsonUtils.jsonToList(json, OrderDetail.class);
	// return orderDetails;
	// }
	// orderDetails = orderDetailDao.findUserAllOrderDetails(orderIds);
	// try
	// {
	// String value = JsonUtils.List2Json(orderDetails);
	// jedisClient.set(key, value);
	// } catch (Exception e)
	// {
	// }
	// return orderDetails;
	// }

//	@Override
//	public List<OrderDTO> findUserALLOrders(String type, String openid)
//	{
//
//		/*
//		 * 既然已经为颗粒度细的准备好了redis,这里就不重新设置了
//		 * 不然每次提交订单,要修改3个redis:一个是orderdetail一个是ordermaster一个是二合一
//		 * 1215:重新决定颗粒度只细分到用户,只保存用户的订单信息 11:00 但是又还是打算将这个取消,因为订单状态更新还是 挺频繁的
//		 * 109:因为在ordermaster修改了需求,ordermaster保留paystatus,前端提供type,
//		 * 先根据type找出ordermaset(未支付,其他情况直接else即可)
//		 */
//		List<OrderDTO> orderDTOs = findUserOrderMaster(type, openid);
//		if (orderDTOs != null && orderDTOs.size() > 0)
//		{
//			List<String> orderIds = new ArrayList<String>();
//			for (OrderDTO orderDTO : orderDTOs)
//			{
//				String orderId = orderDTO.getOrderId();
//				orderIds.add(orderId);
//			}
//			List<OrderDetailVO> orderDetailList = findUserOrderDetailVOListByOrderIds(type, openid, orderIds);
//			List<String> orderDetailPictureIdList = new ArrayList<>();
//			if (orderDetailList != null && orderDetailList.size() > 0)
//			{
//				for (OrderDetailVO orderDetailVO : orderDetailList)
//				{
//					if (!orderDetailPictureIdList.contains(orderDetailVO.getProductPitcureId()))
//					{
//						orderDetailPictureIdList.add(orderDetailVO.getProductPitcureId());
//					}
//				}
//				List<Picture> detailPictures = pictureService.findPicturesByPictureIdList(orderDetailPictureIdList);
//				for (OrderDTO orderDTO : orderDTOs)
//				{
//					List<OrderDetailVO> orderMasterOrderDetails = new ArrayList<>();
//					for (OrderDetailVO orderDetail : orderDetailList)
//					{
//						// 这里需要优化
//						for (Picture picture : detailPictures)
//						{
//							if (picture.getPictureId().equals(orderDetail.getProductPitcureId()))
//							{
//								orderDetail.setPicturePath(picture.getPicturePath());
//							}
//						}
//						if (orderDetail.getOrderId().equals(orderDTO.getOrderId()))
//						{
//							orderMasterOrderDetails.add(orderDetail);
//						}
//					}
//					orderDTO.setOrderDetailList(orderMasterOrderDetails);
//				}
//			}
//			
//		}
//		return orderDTOs;
//	}

//	@Override
//	public List<OrderDetailVO> findUserOrderDetailVOListByOrderIds(String type, String openid, List<String> orderIds)
//	{
//		List<OrderDetailVO> orderDetailList = new ArrayList<>();
//		Map<String, Object> params = new LinkedHashMap<>();
//		params.put("list", orderIds);
		// switch (type)
		// {
		// case "all":
		// orderDetailList = orderDetailDao.findUserAllOrderDetails(orderIds);
		// break;
		// // 等待付款
		// case "wait":
		// params.put("orderStatus", OrderStatusEnum.WAIT.getCode());
		// params.put("payStatus", PayStatusEnum.WAIT_PAY_MONEY.getCode());
		// orderDetailList =
		// orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		// break;
		// // 订单已取消
		// case "cancel":
		// params.put("orderStatus", OrderStatusEnum.CANCLE.getCode());
		// params.put("payStatus", PayStatusEnum.WAIT_PAY_MONEY.getCode());
		// orderDetailList =
		// orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		// Map<String, Object> params2 = new LinkedHashMap<>();
		// params2.put("orderStatus", OrderStatusEnum.CANCLE.getCode());
		// params2.put("payStatus", PayStatusEnum.WAIT_PAY_MONEY.getCode());
		// params2.put("list", orderIds);
		// List<OrderDetailVO> orderDetails2 =
		// orderDetailDao.findUserAllOrderDetailsByStatusInIds(params2);
		// orderDetailList.addAll(orderDetails2);
		// break;
		// // 等待发货
		// case "wait_delivery":
		// params.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.getCode());
		// params.put("payStatus", PayStatusEnum.SUCESS_PAY_MONEY.getCode());
		// orderDetailList =
		// orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		// break;
		// case "finished":
		// params.put("orderStatus", OrderStatusEnum.FINISH.getCode());
		// params.put("payStatus", PayStatusEnum.SUCESS_PAY_MONEY.getCode());
		// orderDetailList =
		// orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		// break;
		// }
//		if (type.equals(OrderStatusEnum.WAIT_DELIVER.getKey()))
//		{
//			params.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.getCode());
//			orderDetailList = orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
//		} else if (type.equals(OrderStatusEnum.FINISH.getKey()))
//		{
//			params.put("orderStatus", OrderStatusEnum.FINISH.getCode());
//			orderDetailList = orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
//		}else {
//			orderDetailList=orderDetailDao.findUserAllOrderDetails(orderIds);
//		}
//		return orderDetailList;
//	}

	@Override
	public List<OrderDTO> findUserAllOrderMaStersByOpenIdAndType(String openid, String type)
	{
		List<OrderDTO> orderDTOs = null;
		if (type.equals("all"))
		{
			orderDTOs = findUserAllOrderMastersByOpenid(openid);
		} else
		{
			int status = 0;
			if (type.equals(PayStatusEnum.WAIT_PAY_MONEY.getDescription()))
			{
				status = PayStatusEnum.WAIT_PAY_MONEY.getCode();
			} else if (type.equals(PayStatusEnum.SUCESS_PAY_MONEY.getDescription()))
			{
				status = PayStatusEnum.SUCESS_PAY_MONEY.getCode();
			} else if (type.equals(PayStatusEnum.WAIT_REFUND.getDescription()))
			{
				status = PayStatusEnum.WAIT_REFUND.getCode();
			} else
			{
				status = PayStatusEnum.SUCESS_REFUND.getCode();
			}
			orderMasterDao.findUserOrderMastersByOpenidAndType(openid, status);
		}
		return orderDTOs;
	}

	@Override
	public List<OrderDetailVO> findUserAllOrderDetailsInOrderIds(String openid, List<String> orderIds)
	{
		return null;
	}

	@Override
	public List<OrderDTO> findUserOrderMasterByOpenidAndBetween(String openid, Integer start, Integer end)
	{
		return orderMasterDao.findUserOrderMasterByOpenIdAndBetween(openid, start, end);
	}
}

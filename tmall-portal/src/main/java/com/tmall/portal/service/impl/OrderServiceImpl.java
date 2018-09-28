/**
 * 
 */
package com.tmall.portal.service.impl;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.utils.KeyUtils;
import com.tmall.dao.OrderDetailDao;
import com.tmall.dao.OrderMasterDao;
import com.tmall.dto.CartDTO;
import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;
import com.tmall.model.Picture;
import com.tmall.portal.service.OrderService;
import com.tmall.portal.service.PictureService;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.portal.utils.PortalUtils;
import com.tmall.vo.OrderDetailVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService
{
	@Autowired
	private OrderMasterDao orderMasterDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private ProductInfosService productInfosService;
	@Autowired
	private PictureService pictureService;

	// private Gson gson = new Gson();

	@Transactional
	@Override
	public OrderDTO create(OrderDTO orderDTO, LinkedList<String> productIds, LinkedList<String> buyNums,
			LinkedList<String> contents)
	{

		String orderId = KeyUtils.generateUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
		List<ProductInfoVO> productInfoVOs = productInfosService.findProductsInIds(productIds);
		List<OrderDetailVO> orderDetails = new ArrayList<OrderDetailVO>();
		for (int i = 0; i < productInfoVOs.size(); ++i)
		{
			Integer buyNum = Integer.parseInt(buyNums.get(i));
			OrderDetailVO orderDetail = PortalUtils.productInfo2OrderDetail(productInfoVOs.get(i));
			orderAmount = getOrderAmount(orderAmount, orderDetail.getProductPrice(), buyNum);
			orderDetail.setProductQuantity(buyNum);
			orderDetails.add(orderDetail);
			CartDTO cartDTO = new CartDTO();
			cartDTO.setProductId(orderDetail.getProductId());
			cartDTO.setProductQuantity(buyNum);
			cartDTOList.add(cartDTO);
		}
		orderDTO.setOrderDetailList(orderDetails);
		// for (OrderDetail orderDetail : orderDTO.getOrderDetailList())
		// {
		// //这里不要for循环一个一个去
		// ResultVo<ProductInfoVO> resultVo =
		// productInfosService.findOneProduct(orderDetail.getProductId());
		// if (resultVo.getData() == null)
		// {
		// throw new TmallException(ResultEnums.PRODUCT_NOTE_EXIT);
		// }
		// ProductInfoVO productInfoVO = gson.fromJson(gson.toJson(resultVo.getData()),
		// ProductInfoVO.class);
		// getOrderAmount(orderAmount, productInfoVO, orderDetail.getProductQuantity());
		// CartDTO cartDTO = new CartDTO();
		// cartDTO.setProductId(orderDetail.getProductId());
		// cartDTO.setProductQuantity(orderDetail.getProductQuantity());
		// cartDTOList.add(cartDTO);
		//
		// }
		/*
		 * 先对orderMaster进行保存
		 */
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.WAIT.getCode());
		orderMasterDao.save(orderMaster);
		/*
		 * 再对orderDetail进行保存
		 */
		for (int i = 0; i < orderDTO.getOrderDetailList().size(); ++i)
		{
			orderDTO.getOrderDetailList().get(i).setOrderDetailId(KeyUtils.generateUniqueKey());
			orderDTO.getOrderDetailList().get(i).setOrderId(orderId);
			String content = null;
			try
			{
				content = contents.get(i);
			} catch (Exception e)
			{
				content = null;
			}
			orderDTO.getOrderDetailList().get(i).setContent(content);
			orderDetailDao.save(orderDTO.getOrderDetailList().get(i));
		}
		// for (OrderDetail orderDetail : orderDTO.getOrderDetailList())
		// {
		// orderDetail.setOrderDetailId(KeyUtils.generateUniqueKey());
		// orderDetail.setOrderId(orderId);
		// orderDetail.setContent(orderDTO.getContent());
		// orderDetailDao.save(orderDetail);
		// }
		// 扣库存
		productInfosService.decreateStock(cartDTOList);
		orderDTO.setOrderAmount(orderAmount);
		orderDTO.setOrderId(orderId);

		// String key=RedisEnums.ALL_USERS_ALL_ORDERS.getKey();
		// String
		// key2=RedisEnums.USER_ORDERS+":"+orderDTO.getBuyerOpenid()+":"+"ORDER_MASTER";
		// String
		// key3=RedisEnums.USER_ORDERS+":"+orderDTO.getBuyerOpenid()+":"+"ORDER_DETAIL";
		// try
		// {
		// jedisClient.del(key3);
		// jedisClient.del(key2);
		// jedisClient.del(key);
		// } catch (Exception e)
		// {
		// // TODO: handle exception
		// }
		return orderDTO;
	}

	public boolean isOnPromote(ProductInfoVO productInfoVO)
	{
		if (StringUtils.isEmpty(productInfoVO.getProductPromotePrice()))
		{
			return false;
		}
		return true;
	}

	public BigDecimal getOrderAmount(BigDecimal orderAmount, BigDecimal price, Integer quantity)
	{
		// if (isOnPromote(productInfoVO))
		// {
		// BigDecimal price = new BigDecimal(p);
		orderAmount = price.multiply(new BigDecimal(quantity)).add(orderAmount);
		// } else
		// {
		// BigDecimal bigDecimal = new BigDecimal(productInfoVO.getProductPrice());
		// orderAmount.multiply(bigDecimal);
		// }

		return orderAmount;
	}

	@Override
	public OrderDetailVO findOneOrderDetail(String orderDetailId)
	{
		OrderDetailVO orderDetail = orderDetailDao.findOneOrderDetail(orderDetailId);
		if (orderDetail == null)
		{
			throw new TmallException(ResultEnums.ORDERDETAIL_NOT_EXIT);
		}
		return orderDetail;
	}

	@Override
	public void updateOrderDetail(OrderDetailVO orderDetail, String openid)
	{
		/*
		 * 这里同时需要修改redis 只需要删除,或者修改声明周期即可
		 */
		orderDetailDao.updateOrderDetail(orderDetail);
		// String key = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" +
		// "ORDER_DETAIL" + ":" + "all";
		// String key2 = null;
		//// String key3 = RedisEnums.ALL_USERS_ALL_ORDERS.getKey();
		// if (orderDetail.getOrderStatus().equals(OrderStatusEnum.WAIT.getCode()))
		// {
		// key2 = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" + "ORDER_DETAIL"
		// + ":" + "wait";
		// } else if
		// (orderDetail.getOrderStatus().equals(OrderStatusEnum.CANCLE.getCode()))
		// {
		// key2 = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" + "ORDER_DETAIL"
		// + ":" + "cancle";
		// } else if
		// (orderDetail.getOrderStatus().equals(OrderStatusEnum.WAIT_DELIVER.getCode()))
		// {
		// key2 = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" + "ORDER_DETAIL"
		// + ":" + "wait_delivery";
		// } else if
		// (orderDetail.getOrderStatus().equals(OrderStatusEnum.FINISH.getCode())
		// ||
		// orderDetail.getOrderStatus().equals(OrderStatusEnum.RECEIPT_GOODS.getCode())
		// || orderDetail.getOrderStatus().equals(OrderStatusEnum.FINISH.getCode()))
		// {
		// key2 = RedisEnums.USER_ORDERS.getKey() + ":" + openid + ":" + "ORDER_DETAIL"
		// + ":" + "finished";
		// }
		// try
		// {
		//// jedisClient.expire(key3, 0);
		// jedisClient.del(key);
		// jedisClient.del(key2);
		// // jedisClient.del(key3);
		// } catch (Exception e)
		// {
		// }
	}

	@Override
	public void updateOrderMaster(OrderMaster orderMaster)
	{
		/*
		 * 更新redis
		 */
		orderMasterDao.updateOrderMaster(orderMaster);
		// String key = RedisEnums.USER_ORDERS.getKey() + ":" +
		// orderMaster.getBuyerOpenid() + ":" + "ALL_ORDER_MASTER";
		// String key2 = RedisEnums.ALL_USERS_ALL_ORDERS.getKey();
		// try
		// {
		// jedisClient.del(key);
		// jedisClient.del(key2);
		// } catch (Exception e)
		// {
		// }
	}

	@Override
	public void updateOrderMasterAndOrderDetail(OrderMaster orderMaster, OrderDetailVO orderDetailVO)
	{
		updateOrderDetail(orderDetailVO, orderMaster.getBuyerOpenid());
		updateOrderMaster(orderMaster);
	}

	@Override
	public OrderMaster findOneOrderMaster(String orderId)
	{
		OrderMaster orderMaster = orderMasterDao.findOneOrderMaster(orderId);
		return orderMaster;
	}

	@Override
	public List<OrderDetailVO> findOrderDetailListrWithOrderMaster(String orderId)
	{
		List<String> orderIds = new ArrayList<>();
		orderIds.add(orderId);
		List<OrderDetailVO> orderDetailVOList = orderDetailDao.findAllDetailsWithAllOrderMasters(orderIds);
		return orderDetailVOList;
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<OrderDTO> findUserAllOrderMastersByOpenid(String openid)
	{
		List<OrderDTO> orderDTOs = null;
		orderDTOs = orderMasterDao.findUserOrderMastersByOpenid(openid);
		return orderDTOs;
	}

	@Override
	public List<OrderDTO> findUserOrderMasterByOpenidAndBetween(String openid, Integer start, Integer end)
	{
		return orderMasterDao.findUserOrderMasterByOpenIdAndBetween(openid, start, end);
	}

	public List<OrderDTO> findUserOrderMaster(String type, String openid)
	{
		List<OrderDTO> orderDTOs = null;
		int status = 0;
		if (type.equals("all"))
		{
			orderDTOs = findUserAllOrderMastersByOpenid(openid);
			return orderDTOs;
		} else if (type.equals(PayStatusEnum.WAIT_PAY_MONEY.getDescription()))
		{
			status = PayStatusEnum.WAIT_PAY_MONEY.getCode();
			orderDTOs = orderMasterDao.findUserOrderMastersByOpenidAndType(openid, status);
			return orderDTOs;
		} else if (type.equals("cancel"))
		{
			orderDTOs = findUserOrderMasterByOpenidAndBetween(openid, PayStatusEnum.WAIT_REFUND.getCode(),
					PayStatusEnum.SUCESS_REFUND.getCode());
			return orderDTOs;
		} else if (type.equals(OrderStatusEnum.WAIT_DELIVER.getKey()) || type.equals(OrderStatusEnum.FINISH.getKey())
				|| type.equals(OrderStatusEnum.RECEIPT_GOODS.getKey()))
		{
			status = PayStatusEnum.SUCESS_PAY_MONEY.getCode();
			orderDTOs = orderMasterDao.findUserOrderMastersByOpenidAndType(openid, status);
			return orderDTOs;
		}
		return null;
	}
	@Override
	public List<OrderDetailVO> findUserOrderDetailVOListByOrderIds(String type, String openid, List<String> orderIds)
	{
		List<OrderDetailVO> orderDetailList = new ArrayList<>();
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("list", orderIds);
		if (type.equals(OrderStatusEnum.WAIT_DELIVER.getKey()))
		{
			params.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.getCode());
			orderDetailList = orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		}else if(type.equals(OrderStatusEnum.RECEIPT_GOODS.getKey())) 
		{
			params.put("orderStatus", OrderStatusEnum.RECEIPT_GOODS.getCode());
			orderDetailList = orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		} else if (type.equals(OrderStatusEnum.FINISH.getKey()))
		{
			params.put("orderStatus", OrderStatusEnum.FINISH.getCode());
			orderDetailList = orderDetailDao.findUserAllOrderDetailsByStatusInIds(params);
		}else {
			orderDetailList=orderDetailDao.findAllDetailsWithAllOrderMasters(orderIds);
		}
		return orderDetailList;
	}

	@Override
	public List<OrderDTO> findUserALLOrders(String type, String openid)
	{
		/*
		 * 既然已经为颗粒度细的准备好了redis,这里就不重新设置了
		 * 不然每次提交订单,要修改3个redis:一个是orderdetail一个是ordermaster一个是二合一
		 * 1215:重新决定颗粒度只细分到用户,只保存用户的订单信息 11:00 但是又还是打算将这个取消,因为订单状态更新还是 挺频繁的
		 * 109:因为在ordermaster修改了需求,ordermaster保留paystatus,前端提供type,
		 * 先根据type找出ordermaset(未支付,其他情况直接else即可)
		 */
		List<OrderDTO> orderDTOs = findUserOrderMaster(type, openid);
		if (orderDTOs != null && orderDTOs.size() > 0)
		{
			List<String> orderIds = new ArrayList<String>();
			for (OrderDTO orderDTO : orderDTOs)
			{
				String orderId = orderDTO.getOrderId();
				orderIds.add(orderId);
			}
			List<OrderDetailVO> orderDetailList = findUserOrderDetailVOListByOrderIds(type, openid, orderIds);
			List<String> orderDetailPictureIdList = new ArrayList<>();
			if (orderDetailList != null && orderDetailList.size() > 0)
			{
				for (OrderDetailVO orderDetailVO : orderDetailList)
				{
					if (!orderDetailPictureIdList.contains(orderDetailVO.getProductPitcureId()))
					{
						orderDetailPictureIdList.add(orderDetailVO.getProductPitcureId());
					}
				}
				List<Picture> detailPictures = pictureService.findPicturesByPictureIdList(orderDetailPictureIdList);
				for (OrderDTO orderDTO : orderDTOs)
				{
					List<OrderDetailVO> orderMasterOrderDetails = new ArrayList<>();
					for (OrderDetailVO orderDetail : orderDetailList)
					{
						// 这里需要优化
						for (Picture picture : detailPictures)
						{
							if (picture.getPictureId().equals(orderDetail.getProductPitcureId()))
							{
								orderDetail.setPicturePath(picture.getPicturePath());
							}
						}
						if (orderDetail.getOrderId().equals(orderDTO.getOrderId()))
						{
							orderMasterOrderDetails.add(orderDetail);
						}
					}
					orderDTO.setOrderDetailList(orderMasterOrderDetails);
				}
			}
		}
		List<OrderDTO> resultOrderDTOList = new ArrayList<OrderDTO>();
		if (orderDTOs != null && orderDTOs.size() > 0)
		{
			for (OrderDTO orderDTO : orderDTOs)
			{
				if (orderDTO.getOrderDetailList() != null && orderDTO.getOrderDetailList().size() > 0)
				{
					resultOrderDTOList.add(orderDTO);
				}
			}
		}
		return resultOrderDTOList;
	}

	@Override
	public OrderDTO findOneOrder(String orderId) throws TmallPortalException
	{
		OrderMaster orderMaster = findOneOrderMaster(orderId);
		List<OrderDetailVO> orderDetailVOs = findOrderDetailListrWithOrderMaster(orderId);
		OrderDTO orderDTO=new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailVOs);
		return orderDTO;
	}
}

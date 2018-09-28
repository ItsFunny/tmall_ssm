/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.dto.OrderDTO;
import com.tmall.model.OrderMaster;
import com.tmall.model.Picture;
import com.tmall.store.service.OrderDetailService;
import com.tmall.store.service.OrderMasterService;
import com.tmall.store.service.OrderService;
import com.tmall.store.service.PictureService;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService
{
	@Autowired
	private OrderMasterService orderMasterService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private PictureService pictureService;
	
	@Override
	public List<OrderDTO> findStoreAllOrders(Integer storeId)
	{
		List<OrderMaster> orderMasterList = orderMasterService.findStoreALLOrderMaster(storeId);
		if(orderMasterList!=null&&orderMasterList.size()>0)
		{
			List<String>orderMasterIDList=new ArrayList<String>();
			for (OrderMaster orderMaster : orderMasterList)
			{
				orderMasterIDList.add(orderMaster.getOrderId());
			}
			List<OrderDetailVO> orderDetailVOList = orderDetailService.findOrderDetailListByOrderMasterIDList(orderMasterIDList);
			//拼接图片list
			List<String>pictureIdList=new ArrayList<>();
			for (OrderDetailVO orderDetailVO : orderDetailVOList)
			{
				if(!pictureIdList.contains(orderDetailVO.getProductPitcureId()))
				{
					pictureIdList.add(orderDetailVO.getProductPitcureId());
				}
			}
			List<Picture> pictureList = pictureService.findOrderDetalPicture(pictureIdList);
			List<OrderDTO>orderDTOList=new ArrayList<>();
			//拼接orderDTO
			for (OrderMaster orderMaster : orderMasterList)
			{
				OrderDTO orderDTO=new OrderDTO();
				BeanUtils.copyProperties(orderMaster, orderDTO);
				List<OrderDetailVO>orderDTOorderDetailVOList=new ArrayList<>();
				for (OrderDetailVO orderDetailVO : orderDetailVOList)
				{
					if(orderDetailVO.getOrderId().equals(orderMaster.getOrderId()))
					{
//						OrderDetailVO orderDetailVO2=new OrderDetailVO();
//						BeanUtils.copyProperties(orderDetailVO, orderDetailVO2);
						if(pictureList!=null)
						{
							for(Picture picture:pictureList)
							{
								if(picture.getPictureId().equals(orderDetailVO.getProductPitcureId()))
								{
									orderDetailVO.setPicturePath(picture.getPicturePath());
								}
							}
						}
						orderDTOorderDetailVOList.add(orderDetailVO);
					}
				}
				orderDTO.setOrderDetailList(orderDTOorderDetailVOList);
				orderDTOList.add(orderDTO);
			}
			/*
			 * 拼装订单日期部分,这里感觉很蠢,这么拼接
			 * ordermaster对应订单是否已经付款,付款日期
			 * orderdetail对应订单的收货日期,发货日期,以及取消日期
			 */
			for(OrderDTO orderDTO:orderDTOList)
			{
				String createDateStr=ConverterUtils.date2SimpleDateString(orderDTO.getCreateDate());
				String payDateStr=ConverterUtils.date2SimpleDateString(orderDTO.getPayDate());
				orderDTO.setCreateDateStr(createDateStr);
				orderDTO.setPayDateStr(payDateStr);
				for(OrderDetailVO orderDetailVO:orderDetailVOList)
				{
					if(orderDetailVO.getDeliverDate()!=null)
					{
						String deliverDateStr=ConverterUtils.date2SimpleDateString(orderDetailVO.getDeliverDate());
						orderDetailVO.setDeliverDateStr(deliverDateStr);
					}
					if(orderDetailVO.getReceiptDate()!=null)
					{
						String receiptDateStr=ConverterUtils.date2SimpleDateString(orderDetailVO.getReceiptDate());
						orderDetailVO.setReceiptDateStr(receiptDateStr);
					}
					if(orderDetailVO.getOrderStatus().equals(OrderStatusEnum.CANCLE.getCode())&&orderDetailVO.getUpdateDate()!=null)
					{
						String cancleDateStr=ConverterUtils.date2SimpleDateString(orderDetailVO.getUpdateDate());
						orderDetailVO.setUpdateDateStr(cancleDateStr);
					}
				}
				
			}
			
			return orderDTOList;
		}
		return null;
	}
}

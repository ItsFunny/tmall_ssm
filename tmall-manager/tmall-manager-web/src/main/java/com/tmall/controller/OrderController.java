/**
 * 
 */
package com.tmall.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.dto.PageDTO;
import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.utils.PageUtils;
import com.tmall.dto.OrderDTO;
import com.tmall.service.OrderService;
import com.tmall.vo.OrderDetailVO;

/**
 * 注意后端的操作都是需要admin角色的
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController
{
	@Autowired
	private OrderService orderService;

	/*
	 * 这里的redis需要好好设置
	 */
	@RequestMapping("/all")
	public ModelAndView showAllOrders(
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name="pageNum",required=false,defaultValue="1")Integer pageNum,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		List<OrderDTO> orderDTOList = orderService.findAllOrders();
		for (OrderDTO orderDTO : orderDTOList)
		{
			String date=ConverterUtils.date2SimpleDateString(orderDTO.getCreateDate());
			orderDTO.setCreateDateStr(date);
		}
		PageDTO<OrderDTO> pageDTO = PageUtils.pageHelper(pageSize, pageNum, orderDTOList);
		ModelAndView modelAndView = new ModelAndView("admin_order_view");

		modelAndView.addObject("orderDTOList", pageDTO.getResponseList());
		modelAndView.addObject("pageDTO",pageDTO);
		modelAndView.addObject("redirect",request.getRequestURL());
		return modelAndView;
	}
	/*
	 * 立即发货
	 */
	@RequestMapping("/edit-order-delivery")
	public ModelAndView deliverProduct(HttpServletRequest request,HttpServletResponse response) 
	{
		String openid=request.getParameter("openid");
		String orderDetailId=request.getParameter("orderDetailId");
		OrderDetailVO ordertailVO = orderService.findOneOrderDetail(orderDetailId);
		ordertailVO.setOrderStatus(OrderStatusEnum.WAIT_RECEIPT_GOOS.getCode());
		ordertailVO.setDeliverDate(new Date());
		orderService.updateOrderDetailAndUpdateOrderRedis(ordertailVO,openid);
		ModelAndView modelAndView=new ModelAndView("temp");
		modelAndView.addObject("error","发货成功");
		modelAndView.addObject("redirect",request.getParameter("redirect"));
		return modelAndView;
	}
	

}

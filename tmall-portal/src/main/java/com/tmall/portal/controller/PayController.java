/**
 * 
 */
package com.tmall.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.model.OrderMaster;
import com.tmall.portal.service.OrderService;

/**
 * 订单支付
 * 因为未拥有微信商户号,所以支付接口不选择接入
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController
{
	@Autowired
	private OrderService orderService;
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public void create(@RequestParam("orderId")String orderId,@RequestParam("returnUrl")String returnUrl)
	{
		OrderMaster orderMaster = orderService.findOneOrderMaster(orderId);
		if (orderMaster == null)
		{
			throw new TmallException(ResultEnums.ORDERMASTER_NOT_EXIT);
		}
		//发起支付
		
		
	}
}

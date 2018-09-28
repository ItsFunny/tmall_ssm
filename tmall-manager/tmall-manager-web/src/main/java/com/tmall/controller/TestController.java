/**
 * 
 */
package com.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.model.OrderMaster;
import com.tmall.service.OrderService;

/**
 * @author Administrator
 *
 */
@RestController
public class TestController
{
	@Autowired
	private OrderService orderService;
	@RequestMapping("/test")
	public List<OrderMaster>test()
	{
		return orderService.findAllOrderMasters();
	}

}

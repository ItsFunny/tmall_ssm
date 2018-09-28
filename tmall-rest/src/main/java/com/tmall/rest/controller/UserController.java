/**
 * 
 */
package com.tmall.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.User;
import com.tmall.rest.service.OrderService;
import com.tmall.rest.service.UserService;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public ResultVo<UserDTO> checkUser(@RequestParam("key") String key, @RequestParam("password") String password)
	{
		return userService.checkLoginUser(key, password);
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultVo<String> addUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}
//	@RequestMapping("/orders")
//	public ResultVo<List<OrderDTO>> findUserAllOrders(String type,String openid)
//	{
////		List<OrderDTO> orderDTOs = orderService.findUserALLOrders(type,openid);
//		/*
//		 * 我们首先是通过openid查找到用户所有的订单信息(ordermaster,因此如果不判断下的话是肯定会有值传过去的)
//		 * 然后再通过ordermaster的orderid获取所有需要的orderdetail
//		 * 如果不加判断
//		 * 比如获取用户所有已取消的订单,这个时候就会发现页面上还是会出现ordermaster,但是不会出现具体
//		 * 这就是不加判断带来的后果
//		 */
//		List<OrderDTO>resultOrderDTOList=new ArrayList<OrderDTO>();
//		if(orderDTOs!=null&&orderDTOs.size()>0)
//		{
//			for (OrderDTO orderDTO : orderDTOs)
//			{
//				if(orderDTO.getOrderDetailList()!=null&&orderDTO.getOrderDetailList().size()>0)
//				{
//					resultOrderDTOList.add(orderDTO);
//				}
//			}
//		}
//		return new ResultVo<>(200,"sucess",resultOrderDTOList);
//	}
}

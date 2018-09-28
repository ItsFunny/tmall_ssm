/**
 * 
 */
package com.tmall.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.form.FormUser;

/**
 * @author Administrator
 *
 */
public interface UserService
{
	UserDTO findUserByUserId(String userId);
	
	UserDTO findUserByUserNickname(String nickname);
	
	UserDTO findUserByPhoneNum(String phoneNum);
	
	UserDTO findUser(String key);
	
	boolean checkLoginUser(UserDTO userDTO,String password,HttpServletRequest request,HttpServletResponse response);
	
	ResultVo<String> checkRegisterUser(String phoneNum,String nickname);
	
	
	/*
	 * 查找
	 * 获取用户的订单列表  具体类型按url来判断
	 */
	List<OrderDTO>findUserOrders(String type,String openid);
	
	
	
	List<UserDTO> findUsersByUserIds(List<String> userIds);
	
	List<UserDTO> findUsersByNicknames(List<String> nicknames);
	
	/*
	 * 增加
	 */
	ResultVo<String> addUser(FormUser user);
	
	
}

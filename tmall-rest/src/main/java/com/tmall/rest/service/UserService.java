/**
 * 
 */
package com.tmall.rest.service;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.User;

/**
 * @author Administrator
 *
 */
public interface UserService
{
	UserDTO findUser(String nickNameOrPhone);
	
	ResultVo<UserDTO> checkLoginUser(String key,String password);
	
	UserDTO findUserByNickname(String nickName);
	
	UserDTO findUserByPhone(String phone);
	
	OrderDTO findUserOrderMasters(String productId);
	
	
	ResultVo<String>addUser(User user);
}

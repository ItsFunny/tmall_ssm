/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.UserDao;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.User;
import com.tmall.rest.service.UserService;

/**
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService
{
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	@Override
	public UserDTO findUser(String key)
	{
		UserDTO userDTO = null;
		try
		{
			Double.parseDouble(key);
			userDTO = findUserByPhone(key);
		} catch (Exception e)
		{
			userDTO = findUserByNickname(key);
		}
		if (userDTO == null)
		{
			throw new TmallException(ResultEnums.USER_NOT_EXIT);
		}
		logger.info("[用户登陆]{}于{}登陆", userDTO.getNickName(), ConverterUtils.date2SimpleDateString(new Date()));
		return userDTO;
	}

	@Override
	public UserDTO findUserByNickname(String nickName)
	{
		UserDTO userDTO = userDao.findUserByNickname(nickName);
		return userDTO;
	}

	@Override
	public UserDTO findUserByPhone(String phone)
	{
		UserDTO userDTO = userDao.findUserByPhone(phone);
		return userDTO;
	}

	@Override
	public ResultVo<UserDTO> checkLoginUser(String key, String password)
	{
		UserDTO userDTO = findUser(key);

		if(userDTO.getPassword().equals(password))
		{
			return new ResultVo<UserDTO>(200,"sucess",userDTO);
		}
		else {
			return new ResultVo<>(500,"failure");
		}
	}

	@Override
	public ResultVo<String> addUser(User user)
	{
		try
		{
			userDao.addUser(user);
		} catch (Exception e)
		{
			throw new TmallException(ResultEnums.SQL_SERVER_IS_DOWN);
		}
		return new ResultVo<>(200,"sucess");
	}

	@Override
	public OrderDTO findUserOrderMasters(String productId)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

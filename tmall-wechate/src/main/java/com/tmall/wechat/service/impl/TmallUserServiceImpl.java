/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:36:42
* 
*/
package com.tmall.wechat.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dto.UserDTO;
import com.tmall.model.User;
import com.tmall.wechat.dao.TmallUserDao;
import com.tmall.wechat.service.ITmallUserService;

/**
* 
* @author joker 
* @date 创建时间：2018年1月26日 下午1:36:42
*/
@Service
public class TmallUserServiceImpl implements ITmallUserService
{
	@Autowired
	private TmallUserDao userDao;

	
	@Override
	public UserDTO findOneUserByKey(String key)
	{
		UserDTO user=null;
		try
		{
			 Long.parseLong(key);
			user=findOneUserByPhoneNumber(key);
		} catch (Exception e)
		{
			if(key.contains("@"))
			{
				user=findOneUserByMailBox(key);
			}else {
				user=findOneUserByUserName(key);
			}
		}
		return user;
	}


	@Override
	public UserDTO findOneUserByUserName(String userName)
	{
		return userDao.findOneUserByUserName(userName);
	}


	@Override
	public UserDTO findOneUserByPhoneNumber(String phoneNumber)
	{
		return userDao.findOneUserByPhoneNumber(phoneNumber);
	}


	@Override
	public UserDTO findOneUserByMailBox(String mailBox)
	{
		return userDao.findOneUserByMailBox(mailBox);
	}


	@Override
	public UserDTO findOneUserByOpenid(String openid)
	{
		return userDao.findOneUserByOpenid(openid);
	}


}

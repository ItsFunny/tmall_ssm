/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:36:42
* 
*/
package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.TmallUserDao;
import com.tmall.dto.StoreDTO;
import com.tmall.model.TmallUser;
import com.tmall.service.ITmallUserService;

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
	public TmallUser findUserByKey(String key)
	{
		TmallUser user=null;
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
	public TmallUser findOneUserByUserName(String userName)
	{
		return userDao.findOneUserByUserName(userName);
	}


	@Override
	public TmallUser findOneUserByPhoneNumber(String phoneNumber)
	{
		return userDao.findOneUserByPhoneNumber(phoneNumber);
	}


	@Override
	public TmallUser findOneUserByMailBox(String mailBox)
	{
		return userDao.findOneUserByMailBox(mailBox);
	}


	@Override
	public TmallUser findOneUserByOpenid(String openid)
	{
		return userDao.findOneUserByOpenid(openid);
	}


	@Override
	public void addNormalUser(TmallUser user)
	{
		 userDao.addNormalUser(user);
	}


	@Override
	public List<StoreDTO> findSellerStoreInfo(String userId)
	{
		return userDao.findSellerStoreInfo(userId);
	}
	
}

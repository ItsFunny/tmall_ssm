/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:36:42
* 
*/
package com.tmall.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dto.StoreDTO;
import com.tmall.model.TmallUser;
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
	public String addNormalUser(TmallUser user)
	{
		String error=null;
		TmallUser user2 = findOneUserByUserName(user.getNickName());
		if(user2!=null)
		{
			error="名字重复";
			return error;
		}
		try
		{
			TmallUser findOneUserByMailBox = findOneUserByMailBox(user.getMailBox());
			if(findOneUserByMailBox!=null)
			{
				error="邮箱重复";
				return error;
			}
			TmallUser findOneUserByPhoneNumber = findOneUserByPhoneNumber(user.getPhoneNumber());
			if(findOneUserByPhoneNumber!=null)
			{
				error="电话号码已被注册";
				return error;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		userDao.addNormalUser(user);
		return null;
	}


	@Override
	public List<StoreDTO> findSellerStoreInfo(String userId)
	{
		return userDao.findSellerStoreInfo(userId);
	}
	
}

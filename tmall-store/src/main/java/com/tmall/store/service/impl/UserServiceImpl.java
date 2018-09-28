/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.UserDao;
import com.tmall.dto.UserDTO;
import com.tmall.store.service.UserService;

/**
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserDTO> findAllUsers()
	{
		List<UserDTO> userDTOList=userDao.findAllUsers();
		return userDTOList;
	}
}

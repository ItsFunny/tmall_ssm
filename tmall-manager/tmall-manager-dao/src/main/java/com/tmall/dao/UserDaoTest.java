/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午5:33:44
* 
*/
package com.tmall.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tmall.dto.UserDTO;

/**
* 
* @author joker 
* @date 创建时间：2018年1月27日 下午5:33:44
*/
@Mapper
public class UserDaoTest implements IBaseDao<UserDTO,String>
{

	@Override
	public UserDTO findOne(String key)
	{
		return null;
	}

	@Override
	public void save(UserDTO m)
	{
		
	}

	@Override
	public void update(UserDTO m)
	{
		
	}

}

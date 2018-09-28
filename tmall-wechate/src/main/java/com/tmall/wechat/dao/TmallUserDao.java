/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:23:44
* 
*/
package com.tmall.wechat.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.UserDTO;

/**
* 
* @author joker 
* @date 创建时间：2018年1月26日 下午1:23:44
*/
@Mapper
public interface TmallUserDao
{
	@Select("select * from user where nickname=#{nickName}")
	UserDTO findOneUserByUserName(String nickName);
	
	@Select("select * from user where phone_number=#{phoneNumber}")
	UserDTO findOneUserByPhoneNumber(String phoneNumber);
	
	@Select("select * from user where mail_box=#{mailBox}")
	UserDTO findOneUserByMailBox(String mailBox);
	
	@Select("select * from user where openid=#{openid}")
	UserDTO findOneUserByOpenid(String openid);
	
	
	
	

}

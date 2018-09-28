/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.UserDTO;
import com.tmall.model.User;

/**
 * @author Administrator
 *
 */
@Mapper
public interface UserDao
{
	/*
	 * 增加一名用户
	 */
	@Insert("insert into user (user_id,username,ID_Card,nickname,phone_number,password,openid) values (#{userId},#{username},#{idCard},#{nickname},#{phoneNumber},#{password},#{openid})")
	void addUser(User user);

	/*
	 * 删除一名用户
	 */
	/*
	 * 修改用户信息
	 */
	/*
	 * 查找一名用户,通过用户nickname或者用户的id,或者用户的openid
	 * 
	 * 查找所有的用户
	 */
	@Select("select * from user where nickname=#{nickname}")
	UserDTO findUserByNickname(String nickname);
	@Select("select * from user where user_id=#{userId}")
	UserDTO findUserByUserId(String userId);
	@Select("select * from user where phone_number=#{phoneNumber}")
	UserDTO findUserByPhone(String phoneNum);
	
	@Select("select * from user ")
	List<UserDTO> findAllUsers();

	/*
	 * 查找一堆用户
	 */
	List<UserDTO> findUsersInUserIDs(List<String> userIds);

	List<UserDTO> findUsersInNicknames(List<String> nicknames);
	/*
	 * 查找所有用户
	 */

}

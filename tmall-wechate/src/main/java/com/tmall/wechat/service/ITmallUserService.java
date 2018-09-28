/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:35:59
* 
*/
package com.tmall.wechat.service;

import com.tmall.dto.UserDTO;
import com.tmall.model.User;

/**
* 
* @author joker 
* @date 创建时间：2018年1月26日 下午1:35:59
*/
public interface ITmallUserService
{
	UserDTO findOneUserByUserName(String userName);
	UserDTO findOneUserByPhoneNumber(String phoneNumber);
	UserDTO findOneUserByMailBox(String mailBox);
	UserDTO findOneUserByOpenid(String openid);
	UserDTO findOneUserByKey(String key);
}

/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:35:59
* 
*/
package com.tmall.service;

import java.util.List;

import com.tmall.dto.StoreDTO;
import com.tmall.model.TmallUser;

/**
* 
* @author joker 
* @date 创建时间：2018年1月26日 下午1:35:59
*/
public interface ITmallUserService
{
	TmallUser findOneUserByUserName(String userName);
	TmallUser findOneUserByPhoneNumber(String phoneNumber);
	TmallUser findOneUserByMailBox(String mailBox);
	TmallUser findOneUserByOpenid(String openid);
	TmallUser findUserByKey(String key);
	
	void addNormalUser(TmallUser user);
	
	List<StoreDTO> findSellerStoreInfo(String userId);
}


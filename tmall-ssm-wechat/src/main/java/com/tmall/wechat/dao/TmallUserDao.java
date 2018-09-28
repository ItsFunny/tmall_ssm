/**
*
* @author joker 
* @date 创建时间：2018年1月26日 下午1:23:44
* 
*/
package com.tmall.wechat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.StoreDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.TmallUser;

/**
 * 
 * @author joker
 * @date 创建时间：2018年1月26日 下午1:23:44
 */
@Mapper
public interface TmallUserDao
{
	@Select("select * from tmall_user where nick_name=#{nickName}")
	UserDTO findOneUserByUserName(String nickName);

	@Select("select * from tmall_user where phone_number=#{phoneNumber}")
	UserDTO findOneUserByPhoneNumber(String phoneNumber);

	@Select("select * from tmall_user where mail_box=#{mailBox}")
	UserDTO findOneUserByMailBox(String mailBox);

	@Select("select * from tmall_user where openid=#{openid}")
	UserDTO findOneUserByOpenid(String openid);

	Long addNormalUser(TmallUser tmallUser);

	@Select("select a.*,b.user_id,b.store_id,b.level,c.store_picture_id,c.store_id,c.store_picture_path from tmall_user a, seller_store b, store_picture c where a.user_id=#{userId} and a.user_id= b.user_id and b.store_id=c.store_id")
	List<StoreDTO> findSellerStoreInfo(String userId);

}

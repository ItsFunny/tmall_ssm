/**
*
* @author joker 
* @date 创建时间：2018年2月11日 下午2:43:49
* 
*/
package com.tmall.wechat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.tmall.model.SellerStore;

/**
* 
* @author joker 
* @date 创建时间：2018年2月11日 下午2:43:49
*/
@Mapper
public interface SellerStoreDao
{
	@Insert("insert into seller_store (user_id,store_id,level) values (#{userId},#{storeId},#{level})")
	void addSellerStore(SellerStore sellerStore);

}

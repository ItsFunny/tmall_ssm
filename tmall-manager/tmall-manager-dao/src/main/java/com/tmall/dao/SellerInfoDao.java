/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tmall.dto.SellerDTO;

/**
 * @author Administrator
 *
 */
@Mapper
public interface SellerInfoDao
{
	/*
	 * 查 根据openid查找卖家信息这个方法是卖家必须已经有店铺了
	 * 根据openid查询卖家信息,单表查询
	 * 根据storeId查询这个店铺下的所有员工 分页查询
	 * 根据storeId查询所有员工,用于批量操作
	 */
	@Select("select a.seller_id,a.username,a.password,a.store_id,a.status,a.openid,b.store_id,b.store_name from seller_info a,store b where a.openid=#{openId} and a.store_id=b.store_id")
	SellerDTO findSellerByOpenId(String openid);

	@Select("select * from seller_info where openid=#{openId}")
	SellerDTO findOneSellerByOpenId(String openid);
	@Select("select * from seller_info where store_id=#{storeId} limit #{start},#{end}")
	List<SellerDTO> findStoreSellers(@Param("storeId") Integer storeId, @Param("start") Integer start,
			@Param("end") Integer end);
	@Select("select * from seller_info where store_id=#{storeId}")
	List<SellerDTO>findStoreAllSellersByStoreId(Integer storeId);
	
	
	
	/*
	 * 增
	 */
	@Insert("insert into seller_info (seller_id,real_name,id_card,username,password,store_id,openid,type,status) values (#{sellerId},#{realName},#{idCard},#{username},#{password},#{storeId},#{openId},#{type},#{status})")
	public void addSeller(SellerDTO sellerDTO);
	
	void updateSellerByStoreId(@Param("storeId")Integer storeId,@Param("status")Integer status);
	/*
	 * 改
	 */
	void updateOneSeller(SellerDTO sellerDTO);
}

/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;

/**
 * @author Administrator
 *
 */
@Mapper
public interface StoreDao
{
	void addStore(SellerDTO sellerDTO);

	/*
	 * 查 查询所有的店铺 根据店铺名称查询 查询所有店铺,与第一个有冲突,准备取舍
	 * 
	 */
	// 这个sql有问题
	@Select("select a.seller_id,a.username,a.password,a.store_id,a.openid,b.store_id,b.store_name from seller_info a,store b where a.store_id=b.store_id")
	List<SellerDTO> findAllStores();

	/**
	 * 根据店铺名称查询
	 * 
	 * @param storeName
	 * @return
	 */
	@Select("select * from store where store_name=#{storeName}")
	SellerDTO findStoreByStoreName(String storeName);

	/**
	 * 分页显示店铺
	 * 
	 * @param start
	 *            (页数减一)*每页显示的数量
	 * @param number
	 *            每页显示的数量
	 * @return
	 */
	@Select("select a.store_id,a.store_name,a.create_date,b.store_picture_id,b.store_picture_path as picturePath,c.seller_id,c.username,c.store_id,c.openid,c.real_name,c.phone_number,c.type from store a,store_picture b,seller_info c where a.store_id=b.store_id and a.store_id=c.store_id and c.type=0 limit #{start},#{number}")
	List<StoreDTO> findStoresLimited(@Param("start") Integer start, @Param("number") Integer number);

	/**
	 * 查询单个店铺
	 * 
	 * @param storeId
	 * @return
	 */
	@Select(" select a.store_id,a.store_name,b.store_picture_id,b.store_picture_path as picturePath,b.store_id from store a ,store_picture b where a.store_id=b.store_id and a.store_id=#{storeId};")
	StoreDTO findOneByStoreId(Integer storeId);

	/**
	 * 查询所有的店铺及其创始人信息
	 * 
	 * @return
	 *
	 */
	@Select("select a.store_id,a.store_name,a.create_date,b.store_picture_id,b.store_picture_path as picturePath,c.seller_id,c.username,c.store_id,c.openid,c.real_name,c.phone_number,c.type from store a,store_picture b,seller_info c where a.store_id=b.store_id and a.store_id=c.store_id and c.type=0")
	List<StoreDTO> findStores();

	/**
	 * 查询总共有多少家店铺
	 * 
	 * @return
	 */
	@Select("select count(store_id) from store")
	Integer getStoreCount();

	@Select("select\r\n" + "a.store_id,a.store_name,b.*,c.* from store a,store_picture b,seller_store c where \r\n"
			+ "c.user_id=#{userId} and c.store_id=b.store_id and b.store_id=a.store_id;")
	List<StoreDTO> findSellerStoreInfo(String userId);

	/*
	 * 删除
	 */
	@Delete("delete from store where store_id=#{storeId}")
	void deleteStoreByStoreId(Integer storeId);

}

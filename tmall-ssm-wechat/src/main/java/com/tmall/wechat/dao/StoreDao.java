/**
 * 
 */
package com.tmall.wechat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.StorePicture;

/**
 * @author Administrator
 *
 */
@Mapper
public interface StoreDao
{
	void addStore(SellerDTO sellerDTO);
	
	@Insert("insert into store_picture (store_id,store_picture_path) values (#{storeId},#{storePicturePath})")
	void addStorePicture(StorePicture storePicture);
	
	@Select("select * from store where store_name=#{storeName}")
	StoreDTO findStoreByName(String storeName);
}

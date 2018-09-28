/**
 * 
 */
package com.tmall.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.tmall.model.StorePicture;

/**
 * @author Administrator
 *
 */
@Mapper
public interface StorePictureDao
{
	@Insert("insert into store_picture (store_id,store_picture_path) values (#{storeId},#{storePicturePath})")
	void addStorePicture(StorePicture storePicture);

}

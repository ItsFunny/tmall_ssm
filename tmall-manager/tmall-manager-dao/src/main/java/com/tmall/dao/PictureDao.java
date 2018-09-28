/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tmall.model.Picture;

/**
 * @author Administrator
 *
 */
@Mapper
public interface PictureDao
{
	/*
	 * 增
	 * 增加商品的一张图片
	 */
	@Insert("insert into picture (picture_id,product_id,picture_path,picture_type) values (#{pictureId},#{productId},#{picturePath},#{pictureType})")
	void addOnePicture(Picture picture);

	/*
	 * 查询商品的图片地址
	 */
	List<Picture> findProductSinglePicturesByProductIds(List<String> productIds);
	/*
	 * 查询
	 * 依据提供的类型查询对应的图片类型
	 * 查询一堆商品的的一堆图片
	 * 查询一堆图片,参数为pictureId 并且为list
	 */
	@Select("select * from picture where picture_type=#{pictureType} and product_id=#{productId}")
	List<Picture> findProductPictureByType(@Param("pictureType")Integer pictureType,@Param("productId")String productId);
	List<Picture>findProductsAllPictures(List<String>productIds);
	List<Picture>findPicturesByPictureIdList(List<String>pictureIdList);
	/*
	 * 这个好像不需要,好像用上面的那个就行,上面那个增加图片好像包含这个
	 * 新增某个商品单张图片地址
	 */
	@Insert("insert into picture (product_id,picture_path,picture_type) values (#{productId},#{picturePath},#{pictureType})")
	void addSinglePictureByProductId(@Param("productId") String productId, @Param("picturePath") String picturePath,@Param("pictureType")Integer type);

	/*
	 * 新增某个商品的详情图片地址,参数为list<String>类型,存放了详情图片的所有地址 还未写好,
	 * 用到的时候再写
	 */
	@Insert("insert into picture (product_id,picture_path,picture_type) values (#{productId},#{picturePath},1)")
	void addDetailPicturesByProductId();
	/*
	 * 删除
	 * 删除某个商品的一张图片
	 */
	@Delete("delete from picture where picture_id=#{pictureId}")
	void deleteProductPictureByPictureId(String pictureId);
	/*
	 * 改
	 */
	void updatePictureByPictureId(Picture picture);
}

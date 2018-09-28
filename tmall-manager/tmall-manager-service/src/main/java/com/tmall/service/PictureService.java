/**
 * 
 */
package com.tmall.service;

import java.util.List;

import com.tmall.model.Picture;
import com.tmall.vo.PictureVO;

/**
 * @author Administrator
 *
 */
public interface PictureService
{
	/*
	 * 增
	 * 增加商品的一张图片
	 */
	void addProductPicture(String pictureId,String productId,String picturePath,String pictureType);
	
	/*
	 * 查
	 * 查找某商品的所有图片
	 * 查找订单详情对应的图片
	 */
	PictureVO findProductAllPictures(String productId);
	List<Picture>findOrderDetalPicture(List<String>orderDetailPictureIdList);
	
	/*
	 * 删除
	 * 删除商品的某张图片
	 * 删除商品的某张图片,并且更新redis
	 */
	void deleteProductOnePictureByPictureId(String pictureId);
	void deletePictureAndUpdateRedis(String pictureId,String productId);
}

/**
 * 
 */
package com.tmall.rest.service;

import java.util.LinkedList;
import java.util.List;


import com.tmall.common.vo.ResultVo;
import com.tmall.model.Picture;
import com.tmall.vo.PictureVO;

/**
 * @author Administrator
 *
 */
public interface PictureService
{
	/*
	 * 根据product的id显示单张图片,用于前台展示,参数为list采用in查询
	 */
	List<Picture> findProductPicturesInProductIds(List<Integer> productIds);

	/*
	 * 根据productId显示详情图片
	 */
	ResultVo<List<Picture>> findProductDetailPicturesByProductId(String productId);

	/*
	 * 根据productId显示商品展示图片
	 */
	List<Picture> findProductShowPicturesByProductId(String productId);
	
	/*
	 * 查
	 * 根据productId显示所有的图片 是所有的图片,包括detailPicture,showPicture和topPicture
	 * 显示参数中对应的图片(参数为一堆list,用于orderDetail中)
	 */
	PictureVO findAllPictures(String productId);
	List<Picture>findPicturesByPictureIdList(List<String>orderDetailPictureIdList);
	
	
	/*
	 * 显示一堆商品对应的一堆图片
	 */
	List<PictureVO> findProductsAllPictures(LinkedList<String>productIds);
}

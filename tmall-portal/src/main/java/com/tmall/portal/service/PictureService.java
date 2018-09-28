/**
 * 
 */
package com.tmall.portal.service;


import java.util.List;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.Picture;

/**
 * @author Administrator
 *
 */
public interface PictureService
{
	ResultVo<Picture> findProductDetailPicturesByPorductId(String productId);
	
	ResultVo<Picture> findProdutShowPicturesByProductId(String productId);
	
	//显示参数中对应的图片(参数为一堆list,用于orderDetail中)
	List<Picture>findPicturesByPictureIdList(List<String>orderDetailPictureIdList);
}

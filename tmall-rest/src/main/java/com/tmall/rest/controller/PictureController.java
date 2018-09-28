/**
 * 
 */
package com.tmall.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.Picture;
import com.tmall.rest.service.PictureService;
import com.tmall.vo.PictureVO;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("picture")
public class PictureController
{
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/product/detail/{productId}")
	public ResultVo<List<Picture>> getProductDetailPictures(@PathVariable("productId") String productId,HttpServletRequest request)
	{
		ResultVo<List<Picture>> resultVO = pictureService.findProductDetailPicturesByProductId(productId);
		return resultVO;
	}

	@RequestMapping("product/show/{productId}")
	public ResultVo<List<Picture>> findAllProductshowPictures(@PathVariable("productId") String productId)
	{
		List<Picture> pictureList = pictureService.findProductShowPicturesByProductId(productId);
		return new ResultVo<List<Picture>>(200, "sucess", pictureList);
	}

	@RequestMapping("/product/all/pictures/{productId}")
	public ResultVo<PictureVO> findAllPictures(@PathVariable("productId") String productId, HttpServletRequest request,
			HttpServletResponse response)
	{
		PictureVO pictureVO = pictureService.findAllPictures(productId);
		return new ResultVo<PictureVO>(200, "sucess", pictureVO);
	}
}

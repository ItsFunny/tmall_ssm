/**
 * 
 */
package com.tmall.portal.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.Picture;
import com.tmall.model.Review;
import com.tmall.portal.service.PictureService;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.portal.service.PropertyService;
import com.tmall.portal.service.ReviewService;
import com.tmall.vo.ProductInfoVO;
import com.tmall.vo.ReviewVO;

/**
 * 单个商品显示页面
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	private ProductInfosService productInfosService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private PictureService pictureService;
	@Autowired
	private PropertyService propertyService;
	@SuppressWarnings("unchecked")
	@RequestMapping("/detail/{productId}")
	public ModelAndView productDetail(@PathVariable("productId") String productId, Model model) throws ParseException
	{
		ResultVo<ProductInfoVO> resultVO = productInfosService.findOneProduct(productId);

		
		// 所有评论
		List<Review> reviewList = reviewService.findAllReviewsByProductId(productId);
		List<ReviewVO> reviewVOList = new ArrayList<ReviewVO>();
		for (Review review : reviewList)
		{
			ReviewVO reviewVO = new ReviewVO();
			BeanUtils.copyProperties(review, reviewVO);
			String createDate = ConverterUtils.date2SimpleDateString(review.getCreateDate());
			String nickName = ConverterUtils.hideKeyString(review.getNickname());
			reviewVO.setNickname(nickName);
			reviewVO.setCreateDate(createDate);
			reviewVOList.add(reviewVO);
		}
		
		// 所有详情图片.因为图片详情页面是打算用ajax来获得的,所以返回值都是以ResultVO来包装的
		ResultVo<Picture> result = pictureService.findProductDetailPicturesByPorductId(productId);
		List<Picture> pictureList = (List<Picture>) result.getData();
		
		// 所有商品显示图片
		ResultVo<Picture> resultVoPicture = pictureService.findProdutShowPicturesByProductId(productId);
	
		List<Picture> pictureShowList = (List<Picture>) resultVoPicture.getData();

		// 所有属性
		ResultVo<PropertyDTO> resultVOProperty = propertyService.findAllPropertyValusByProductId(productId);
		List<PropertyDTO> propertyDTOList = (List<PropertyDTO>) resultVOProperty.getData();
		
		// 因为想ajax请求来接受,所以就先单独单独传输到页面上先
		model.addAttribute("item", resultVO.getData());
		model.addAttribute("reviews", reviewVOList);
		model.addAttribute("reviewsTotalCount", reviewVOList.size());
		model.addAttribute("pictures", pictureList);
		model.addAttribute("propertyValues", propertyDTOList);
		model.addAttribute("showPictures",pictureShowList);
		ModelAndView modelAndView = new ModelAndView("ProductDetail");
		return modelAndView;
	}

}

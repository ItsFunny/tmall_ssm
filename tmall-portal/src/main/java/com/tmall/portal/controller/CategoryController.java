/**
 * 
 */
package com.tmall.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tmall.common.vo.ResultVo;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;


/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController
{

	@Autowired
	private ProductInfosService productInfosService;

	/*
	 * 显示某个店铺某个分类下的所有商品
	 */
	@RequestMapping("/detail/{storeId}/{categoryType}")
	public ModelAndView categoryDetail(@PathVariable(name = "categoryType") Integer categoryType,
			@PathVariable("storeId") Integer storeId, HttpServletRequest request, HttpServletResponse response)
	{
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResultVo<ProductInfoAndCategoryVO> resultVO = productInfosService
				.showAllProductsByCategoryTypeAndStoreId(categoryType, storeId);
		ModelAndView modelAndView = new ModelAndView("CategoryDetail");
		Gson gson = new Gson();
		ProductInfoAndCategoryVO productInfoAndCategoryVO = gson.fromJson(gson.toJson(resultVO.getData()),
				ProductInfoAndCategoryVO.class);
		if(productInfoAndCategoryVO!=null)
		{
			modelAndView.addObject("item", resultVO.getData());
			modelAndView.addObject("products", productInfoAndCategoryVO.getProductInfoList());
		}
		return modelAndView;
	}

	@RequestMapping("/detail/{categoryType}")
	public ModelAndView showCategoryProducts(@PathVariable("categoryType") Integer categoryType,
			HttpServletRequest request, HttpServletResponse response)
	{
		ResultVo<List<ProductInfoVO>> resultVo = productInfosService.findAllProductsByCategoryType(categoryType);
		ModelAndView modelAndView = new ModelAndView("CategoryDetail");
		modelAndView.addObject("products", resultVo.getData());
		return modelAndView;
	}

	/*
	 * 左侧列表商品的显示
	 */
	public void getLeftPartProducts(HttpServletRequest request, HttpServletResponse response)
	{
	}

}

/**
 * 
 */
package com.tmall.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.ServletContextConstant;
import com.tmall.dto.UserDTO;
import com.tmall.portal.service.StoreService;
import com.tmall.vo.ProductInfoAndCategoryVO;

/**
 * @author Administrator
 *
 */
@Controller
public class StoreController
{
	@Autowired
	private StoreService storeService;

	@RequestMapping("/store/{storeId}")
	public ModelAndView showStore(@PathVariable("storeId") Integer storeId, HttpServletRequest request,
			HttpServletResponse response)
	{


		List<ProductInfoAndCategoryVO> productInfoAndCategoryVOList = storeService
				.findStoreAllCategoryAndAllProducts(storeId);
		ModelAndView modelAndView = new ModelAndView("store_index");
		modelAndView.addObject("productInfoAndCategoryVOList", productInfoAndCategoryVOList);
		return modelAndView;
	}

}

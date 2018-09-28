/**
 * 
 */
package com.tmall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.dto.PageDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.SellerInfo;
import com.tmall.service.ManagerService;
import com.tmall.service.StoreService;

/**
 * 卖家管理页面
 * 
 * @author Administrator
 *
 */
@Controller
public class SellerController
{
	@Autowired
	private StoreService storeService;
	@Autowired
	private ManagerService managerService;

	/*
	 * 店铺管理->所有店铺
	 */
	@RequestMapping("all-stores")
	public ModelAndView showAllStores(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum)
	{
		ModelAndView modelAndView = new ModelAndView("admin_store_view");
		PageDTO<StoreDTO> pageDTO = storeService.findAllStoreInfos((pageNum - 1) * pageSize, pageSize);
		modelAndView.addObject("storeList", pageDTO.getResponseList());
		modelAndView.addObject("pageDTO", pageDTO);
		modelAndView.addObject("redirect", request.getRequestURL());
		return modelAndView;
	}

	/*
	 * 店铺管理->删除店铺(店铺下架) 店铺下架,106 22:58 里面的逻辑需要使用事务
	 */
	@RequestMapping("/store-delete/{storeId}")
	public ModelAndView deleteStore(@PathVariable("storeId") Integer storeId, HttpServletRequest request,
			HttpServletResponse response)
	{
		String redirect = request.getParameter("redirect");
//		String error = null;
//		String error2 = null;
//		// 删除店铺之前先将员工给注销了(禁止登陆,因为在数据库中关系设置了setnull)因为还没做到员工这部分,所以先放着,直接先将创始人的status改为disable
//		error2 = sellerInfoService.updateSellerStatusByStoreId(storeId, SellerEnums.DISABLE.getCode());
		Map<String, Object> params = new HashMap<String, Object>();
//		StoreDTO storeDTO = storeService.findStoreByStoreId(storeId);
//		FileUtils.deleteFile(storeDTO.getPicturePath(), PictureEnums.PICTURE_TYPE_STORE.getMsg());
//		storeService.deleteStoreByStoreId(storeId);
//
//		SellerInfo sellerInfo = new SellerInfo();
//		sellerInfo.setStoreId(storeId);
//		// 找到这个店铺下的所有分类
//		List<StoreCategory> sellerCategoryList = storeCategoryService.findSellerCategoryList(sellerInfo);
//		// 将所有分类删除
//		// 这里还得想办法将图片删除,并且在删除category之前应该先删除图片,因为数据库中已经设置过了CASCADE删除的时候会将product
//		// 也一并删除
//		List<ProductInfoVO> productInfoVOList = productInfoService.findStoreAllProducts(storeId,
//				ProductStatusEnums.UP.getStatus());
//		List<String> productIdList = new ArrayList<>();
//		for (ProductInfoVO productInfoVO : productInfoVOList)
//		{
////			productInfoVO.setProductStatus(ProductStatusEnums.DOWN.getStatus());
//			if (!productIdList.contains(productInfoVO.getProductId()))
//			{
//				productIdList.add(productInfoVO.getProductId());
//			}
//			FileUtils.deleteFile(productInfoVO.getProductPicturePath(), PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
//		}
//		//关于更新product的状态,决定更新状态,但是采用更新状态的话最好数据库不采用外键,set null的话无法获取到storeId了
//		//但是又如果不采用外键的话,更新又太麻烦.....但是product表只有一个storeId属性列,这个是不会变的,所以直接更新即可
//		if (productIdList != null && productIdList.size() > 0)
//		{
//			productInfoService.updateProductStatusByIdList(ProductStatusEnums.DOWN.getStatus(), productIdList);
//		}
//		if (sellerCategoryList != null && sellerCategoryList.size() > 0)
//		{
//			List<Integer> categoryTypeList = new ArrayList<Integer>();
//			for (StoreCategory storeCategory : sellerCategoryList)
//			{
//				if (!categoryTypeList.contains(storeCategory.getProductCategoryType()))
//				{
//					categoryTypeList.add(storeCategory.getProductCategoryType());
//				}
//			}
//			error = productCategoryService.deleteStoreCategory(storeId, categoryTypeList);
//		}
//		if (!StringUtils.isEmpty(error))
//		{
//			if (!StringUtils.isEmpty(error2))
//			{
//				error = error + error2;
//			}
//			params.put("error", error);
//		} else
//		{
//			params.put("error", "删除店铺成功");
//		}
//		params.put("redirect", redirect);
//		ModelAndView modelAndView = new ModelAndView("temp", params);
//		return modelAndView;
		StoreDTO storeDTO = storeService.findStoreByStoreId(storeId);
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setStoreId(storeId);
//		List<StoreCategory> sellerCategoryList = storeCategoryService.findSellerCategoryList(sellerInfo);
		String error = managerService.deleteOneStoreAndUpdateRedis(storeId, storeDTO);
		params.put("error", error);
		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("temp", params);
		return modelAndView;
	}
}

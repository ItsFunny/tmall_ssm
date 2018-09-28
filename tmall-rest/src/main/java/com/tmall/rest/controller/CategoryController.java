/**
 * 
 */
package com.tmall.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.Picture;
import com.tmall.rest.service.PictureService;
import com.tmall.rest.service.ProductCategoryService;
import com.tmall.rest.service.ProductInfoService;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/category")
public class CategoryController
{
	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/detail/{storeId}/{categoryType}")
	public ResultVo<ProductInfoAndCategoryVO> showSingleTypeProducts(@PathVariable("categoryType") Integer categoryType,@PathVariable("storeId")Integer storeId)
			throws Exception, InvocationTargetException
	{
		// 这里抛出的异常最好设置为自己定义的异常,方便出现错误的时候进行定位
		ProductCategoryVO productCategory = productCategoryService.findOne(categoryType);

		List<ProductInfoVO> productInfoVOList = productInfoService.findAllProductsByCategoryTypeAndStoreId(categoryType,storeId);
		ResultVo<ProductInfoAndCategoryVO>resultVo=new ResultVo<>();
		/*
		 * 这里的代码重复了,最忌讳的就是别写重复的代码
		 */
		if(!productInfoVOList.isEmpty())
		{
			//查询图片
			LinkedList<String>productIdList=new LinkedList<String>();
			List<String>pictureIdList=new ArrayList<String>();
			for (ProductInfoVO productInfoVO : productInfoVOList)
			{
				productIdList.add(productInfoVO.getProductId());
				if(!pictureIdList.contains(productInfoVO.getPictureId()))
				{
					pictureIdList.add(productInfoVO.getPictureId());
				}
			}
			List<Picture> pictures = pictureService.findPicturesByPictureIdList(pictureIdList);
			List<PictureVO> pictureList = pictureService.findProductsAllPictures(productIdList);
			for (ProductInfoVO productInfoVO : productInfoVOList)
			{
				for (PictureVO pictureVO : pictureList)
				{
					if(pictureVO.getProductId().equals(productInfoVO.getProductId()))
					{
						productInfoVO.setProductPictures(pictureVO);
					}
				}
				for (Picture picture : pictures)
				{
					if(picture.getProductId().equals(productInfoVO.getProductId()))
					{
						productInfoVO.setProductPicturePath(picture.getPicturePath());
					}
				}
			}
			ProductInfoAndCategoryVO productInfoAndCategoryVO = new ProductInfoAndCategoryVO();
			BeanUtils.copyProperties(productCategory, productInfoAndCategoryVO);
			productInfoAndCategoryVO.setProductInfoList(productInfoVOList);
			resultVo.setData(productInfoAndCategoryVO);
			resultVo.setMsg("sucess");
			resultVo.setCode(200);
		}
		resultVo.setCode(400);
		resultVo.setMsg("failure");
		return resultVo;
	}
	@RequestMapping("/detail/{categoryType}")
	public ResultVo<List<ProductInfoVO>> findCategoryAllProducts(@PathVariable("categoryType")Integer categoryType)
	{
		List<ProductInfoVO> productInfoVOs = productInfoService.findProductsByCategoryType(categoryType);
		ProductInfoAndCategoryVO productInfoAndCategoryVO=new ProductInfoAndCategoryVO();
		for (ProductInfoVO productInfoVO : productInfoVOs)
		{
			
		}
		return new ResultVo<List<ProductInfoVO>>(200, "sucess", productInfoVOs);
	}
	@RequestMapping("/all")
	public ResultVo<List<ProductCategoryVO>> showAllCategories()
	{
		List<ProductCategoryVO> productCategoryVOList = productCategoryService.findAllCategoriesAndPictures();
		return new ResultVo<>(200,"sucess",productCategoryVOList);
	}
	@RequestMapping("/single/{categoryType}")
	public ProductCategoryVO findOne(@PathVariable("categoryType") Integer categoryType)
	{
		ProductCategoryVO productCategory = productCategoryService.findOne(categoryType);
		return productCategory;
	}

}

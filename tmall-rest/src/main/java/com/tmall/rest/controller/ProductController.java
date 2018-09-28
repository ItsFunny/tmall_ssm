/**
 * 
 */
package com.tmall.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.common.vo.ResultVo;
import com.tmall.model.CategoryPicture;
import com.tmall.model.Picture;
import com.tmall.rest.service.CategoryPictureService;
import com.tmall.rest.service.PictureService;
import com.tmall.rest.service.ProductCategoryService;
import com.tmall.rest.service.ProductInfoService;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

import net.sf.json.JSONArray;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private PictureService pictureService;

	/*
	 * 这里我想错了,弄错了方向,查找所有的商品,方向应该 1.查出所有上架的商品 2.根据上架的商品,获取他们的category_type
	 * 3.再根据这些type得到所有上架的类目!!(有些类目下的商品是下架的,这点很关键)
	 * 
	 * 这里需要优化 1215:重新编写这段代码,采用查询所有商品,而非所有上架商品
	 */
	@RequestMapping("/show/all")
	public ResultVo<List<ProductInfoAndCategoryVO>> showAllProducts(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET");
		/*
		 * 查询所有类目
		 */
		List<ProductCategoryVO> productCategoryVOList = productCategoryService.findAllCategories();
		// 拼接类目的type
		List<Integer> categoryTypeList = new ArrayList<>();
		for (ProductCategoryVO productCategoryVO : productCategoryVOList)
		{
			if (!categoryTypeList.contains(productCategoryVO.getCategoryType()))
			{
				categoryTypeList.add(productCategoryVO.getCategoryType());
			}
		}
		// 查询类目对应的商品(所有商品)
		List<ProductInfoVO> productInfoVOList = productInfoService.findAll(categoryTypeList);
		// List<ProductInfoVO> productInfoUpList = productInfoService.findUpAll();
		LinkedList<String> productIdList = new LinkedList<>();
		// 收集所有商品的pictureid,为他匹配picturepath 重新修改之后不需要了,在上一步已经将图片也搜好了
		// List<Integer> pictureIdList = new ArrayList<>();
		for (ProductInfoVO productInfoVO : productInfoVOList)
		{
			// if (!categoryTypeList.contains(productInfoVO.getCategoryType()))
			// {
			// categoryTypeList.add(productInfoVO.getCategoryType());
			// }
			if (!productIdList.contains(productInfoVO.getProductId()))
			{
				productIdList.add(productInfoVO.getProductId());
			}
			// if (!pictureIdList.contains(productInfoVO.getPictureId()))
			// {
			// pictureIdList.add(productInfoVO.getPictureId());
			// }
		}
		List<PictureVO> pictureVOList = pictureService.findProductsAllPictures(productIdList);

		// List<ProductCategoryVO> productCategoryVOList = productCategoryService
		// .findAllProductCategoriesInAllCategoryTypes(categoryTypeList);
		// 数据库搜索所有的图片
		// List<Picture> picturesByPictureIdList =
		// pictureService.findPicturesByPictureIdList(pictureIdList);
		// List<CategoryPicture> picturesByCategoryType = categoryPictureService
		// .findPicturesByCategoryTypeList(categoryTypeList);
		List<ProductInfoAndCategoryVO> productInfoAndCategoryVOList = new ArrayList<>();
		for (ProductCategoryVO productCategory : productCategoryVOList)
		{
			ProductInfoAndCategoryVO productInfoAndCategoryVO = new ProductInfoAndCategoryVO();

			List<ProductInfoVO> productInfos = new ArrayList<>();
			for (ProductInfoVO productInfoVO : productInfoVOList)
			{
				if (productInfoVO.getCategoryType().equals(productCategory.getCategoryType()))
				{
					productInfos.add(productInfoVO);
				}
				for (PictureVO pictureVO : pictureVOList)
				{
					if (pictureVO.getProductId().equals(productInfoVO.getProductId()))
					{
						productInfoVO.setProductPictures(pictureVO);
						break;
					}
				}
				// for (Picture picture : picturesByPictureIdList)
				// {
				// if (productInfoVO.getPictureId().equals(picture.getPictureId()))
				// {
				// productInfoVO.setProductPicturePath(picture.getPicturePath());
				// break;
				// }
				// }
				// for (CategoryPicture categoryPicture : picturesByCategoryType)
				// {
				// if
				// (categoryPicture.getCategoryType().equals(productCategory.getCategoryType()))
				// {
				// productCategory.setCategoryPictureId(categoryPicture.getCategoryPictureId());
				// productCategory.setCategoryPicturePath(categoryPicture.getCategoryPicturePath());
				// break;
				// }
				// }
			}
			BeanUtils.copyProperties(productCategory, productInfoAndCategoryVO);
			productInfoAndCategoryVO.setProductInfoList(productInfos);
			productInfoAndCategoryVOList.add(productInfoAndCategoryVO);
		}
		ResultVo<List<ProductInfoAndCategoryVO>> resultVo = new ResultVo<>();
		resultVo.setCode(200);
		resultVo.setData(productInfoAndCategoryVOList);
		resultVo.setMsg("成功");
		return resultVo;
	}

	/*
	 * 延迟加载的那些个人感觉应该都是发布一些接口,然后再ajax调用 所以这里的很多东西都可以如此,比如说下面的页面评论这些,只需要先显示数目即可
	 * 当点进去后再加载也行的
	 */
	@RequestMapping("/detail/{productId}")
	public ResultVo<ProductInfoVO> showProductDetail(@PathVariable("productId") String productId)
	{
		ProductInfoVO productInfoVO = productInfoService.findOne(productId);
		ResultVo<ProductInfoVO> resultVo = new ResultVo<>();
		resultVo.setCode(200);
		resultVo.setMsg("sucess");
		resultVo.setData(productInfoVO);
		return resultVo;
	}

	/*
	 * 查找一串商品,用于购物车方面
	 */
	@RequestMapping(name = "/cart/products/{json}")
	public ResultVo<List<ProductInfoVO>> findProductsInIds(@PathVariable("json") String json)
	{
		JSONArray jsonArray = JSONArray.fromObject(json);
		@SuppressWarnings("unchecked")
		List<String> productIds = (List<String>) JSONArray.toCollection(jsonArray);
		List<ProductInfoVO> productInfoVOs = productInfoService.findProductsInProductIds(productIds);
		return new ResultVo<>(200, "sucess", productInfoVOs);
	}
}

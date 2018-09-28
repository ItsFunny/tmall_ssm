/**
 * 
 */
package com.tmall.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.dto.PageDTO;
import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.FileUtils;
import com.tmall.common.utils.KeyUtils;
import com.tmall.common.utils.PageUtils;
import com.tmall.dto.PropertyDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.ProductInfo;
import com.tmall.model.Property;
import com.tmall.service.PictureService;
import com.tmall.service.ProductCategoryService;
import com.tmall.service.ProductInfoService;
import com.tmall.service.PropertyService;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * 商城后台管理
 * 
 * @author Administrator
 *
 */
@Controller
public class AdminController
{
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private PropertyService propertyService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private PictureService pictureService;

	/*
	 * 获取所有商品分类列表
	 */
	@RequestMapping("/category/all")
	public ModelAndView showAllCategories(@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "pageNum", required = false) Integer pageNum, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		List<ProductCategoryVO> productCategoryList = productCategoryService.findAllCategories();
		pageSize = pageSize == null ? 5 : pageSize;
		pageNum = pageNum == null ? 1 : pageNum;
		PageDTO<ProductCategoryVO> pageDTO = PageUtils.pageHelper(pageSize, pageNum, productCategoryList);
		params.put("categoryList", pageDTO.getResponseList());
		params.put("pageDTO", pageDTO);
		params.put("redirect", request.getRequestURL());
		ModelAndView modelAndView = new ModelAndView("admin_category_view", params);
		return modelAndView;
	}

	/*
	 * 添加类目
	 */
	@RequestMapping("/category/add")
	public ModelAndView addCategory(@RequestParam("filepath") MultipartFile filepath,
			@RequestParam("categoryName") String categoryName, HttpServletRequest request, HttpServletResponse response)
	{
		Random random = new Random();
		Integer categoryType = random.nextInt(10000) + 10000;
		ProductCategoryVO productCategoryVO = new ProductCategoryVO();
		productCategoryVO.setCategoryName(categoryName);
		productCategoryVO.setCategoryType(categoryType);
		PictureDTO pictureDTO = FileUtils.generateFile(filepath, PictureEnums.PICTURE_TYPE_CATEGORY.getMsg());
		productCategoryVO.setCategoryPicturePath(pictureDTO.getPicturePath());
		// productCategoryService.addCategory(productCategory);
		productCategoryService.addAndUpdateCategoryAndPicture(productCategoryVO, pictureDTO.getPicturePath());
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "添加成功");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}

	@RequestMapping("/category/show/property")
	public ModelAndView showCategoryProperties(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		Map<String, Object> params = new HashMap<>();
		// 获取分类的type
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		String categoryName = new String(request.getParameter("categoryName").getBytes("iso-8859-1"), "UTF-8");
		List<Property> propertyList = propertyService.findCategoryAllProperties(categoryType);
		StringBuffer redirect = request.getRequestURL();
		redirect.append("?categoryType=" + categoryType + "&categoryName=" + categoryName);
		// redirect+="?categoryType="+categoryType+"&categoryName="+categoryName;
		params.put("propertyList", propertyList);
		params.put("categoryName", categoryName);
		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("admin_category_edit_property", params);
		return modelAndView;
	}

	@RequestMapping("/category/property/delete/{propertyId}")
	public ModelAndView deleteProperty(@PathVariable("propertyId") Integer propertyId, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException
	{
		propertyService.deletePropertyByPropertyId(propertyId);
		String propertyName = new String(request.getParameter("propertyName").getBytes("iso-8859-1"), "UTF-8");
		UserDTO user = (UserDTO) request.getSession().getAttribute("user");
		if (user != null)
		{
			logger.info("[修改类目属性]用户{},于{}删除{}属性", user.getNickName(), new Date(), propertyName);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("redirect", "http://127.0.0.1:8090/manager/category/all?pageNum=1");
		params.put("error", "删除成功");
		ModelAndView modelAndView = new ModelAndView("temp", params);
		return modelAndView;
	}

	/*
	 * 删除某一类目
	 */
	@RequestMapping("/category/delete/{categoryType}")
	public ModelAndView deleteCategory(@PathVariable("categoryType") Integer categoryType, HttpServletRequest request,
			HttpServletResponse response)
	{
		productCategoryService.deleteCategory(categoryType);
		// 将对应的商品改为下架
		productInfoService.updateProductsByCategoryType(ProductStatusEnums.DOWN.getStatus(), categoryType);
		UserDTO user = (UserDTO) request.getSession().getAttribute("user");
		if (user != null)
		{
			logger.info("[删除类目]用户{},于{}删除id为{}类目", user.getNickName(), new Date(), categoryType);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("redirect", "http://127.0.0.1:8090/manager/category/all?pageNum=1");
		params.put("error", "删除成功");
		ModelAndView modelAndView = new ModelAndView("temp", params);
		return modelAndView;
	}

	@RequestMapping("/category/edit")
	public ModelAndView editCategory(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		Map<String, Object> params = new HashMap<>();
		String categoryName = new String(request.getParameter("categoryName").getBytes("iso-8859-1"), "utf-8");
		String categoryType = request.getParameter("categoryType");
		// String categoryName=request.getParameter("categoryName");
		// Integer categoryType=Integer.parseInt(request.getParameter("categoryType"));
		params.put("categoryName", categoryName);
		params.put("categoryType", categoryType);
		params.put("redirect", request.getParameter("redirect"));
		ModelAndView modelAndView = new ModelAndView("admin_category_edit", params);
		return modelAndView;
	}

	/*
	 * 分类编辑
	 */
	@RequestMapping("/category/do_edit")
	public ModelAndView doEditCategory(@RequestParam("filepath") MultipartFile filepath, String categoryName,
			Integer categoryType, @RequestParam("redirect") String redirect, HttpServletRequest request,
			HttpServletResponse response)
	{
		if (!filepath.isEmpty())
		{
			try
			{
				File dir = new File(ServiceEnums.PICTURE_CATEGORY_LOCATION.getUrl());
				if (!dir.exists())
				{
					dir.mkdir();
				}
				PictureDTO pictureDTO = FileUtils.generateFile(filepath, PictureEnums.PICTURE_TYPE_CATEGORY.getMsg());
				productCategoryService.updateCategory(categoryName, pictureDTO.getPicturePath(), categoryType);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "更新成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 产品管理
	 */
	@RequestMapping("/category/porduct/{categoryType}")
	public ModelAndView showCategoryProducts(@PathVariable("categoryType") Integer categoryType,
			@RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> params = new HashMap<>();
		ProductInfoAndCategoryVO productInfoAndCategoryVO = productInfoService
				.findAllProductsByCategoryType(categoryType);
		// List<ProductInfoVO> productInfoList =
		// productInfoAndCategoryVO.getProductInfoList();
		PageDTO<ProductInfoVO> pageDTO = new PageDTO<ProductInfoVO>();
		if (productInfoAndCategoryVO != null)
		{
			pageDTO = PageUtils.pageHelper(pageSize, pageNum, productInfoAndCategoryVO.getProductInfoList());
			params.put("categoryName", productInfoAndCategoryVO.getCategoryName());
			params.put("categoryType", productInfoAndCategoryVO.getCategoryType());
			params.put("pageDTO", pageDTO);
			params.put("productList", pageDTO.getResponseList());
			params.put("redirect", request.getRequestURL());
		}
		ModelAndView modelAndView = new ModelAndView("admin_category_product_view", params);
		return modelAndView;
	}

	/*
	 * 产品管理->添加产品
	 */
	@RequestMapping("category/product/edit-product-add")
	public ModelAndView addCategoryProduct(ProductInfo productInfo, @RequestParam("picture") MultipartFile file,
			String redirect, HttpServletRequest request, HttpServletResponse response)
	{
		String productId = KeyUtils.generateProductId();
		// 这里前端传个参数,询问是否上架
		if (true)
		{
			productInfo.setProductStatus(ProductStatusEnums.UP.getStatus());
		}
		productInfo.setProductId(productId);
		productInfo.setStoreId(1);
		// 这里先直接设置下图片id
		PictureDTO pictureDTO = FileUtils.generateFile(file, PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
		productInfoService.addProductInfoAndPictureAndUpdateRedis(productInfo, pictureDTO);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "插入成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 产品管理->图片管理
	 * 
	 */
	@RequestMapping("/category/product/edit-pictures/{productId}")
	public ModelAndView editProductPictures(@RequestParam Map<String, Object> params,
			@PathVariable("productId") String productId, HttpServletRequest request, HttpServletResponse response)
	{
		// 找到这个商品的所有图片
		PictureVO pictureVO = pictureService.findProductAllPictures(productId);
		ModelAndView modelAndView = new ModelAndView("admin_category_product_picture", params);
		modelAndView.addObject("pictureVO", pictureVO);
		modelAndView.addObject("redirect", request.getRequestURL());
		return modelAndView;
	}

	/*
	 * 产品管理->图片管理->删除图片
	 */
	@RequestMapping("/category/product/edit-pictures-delete/{pictureId}")
	public ModelAndView deleteProductPicture(@PathVariable("pictureId") String pictureId, HttpServletRequest request,
			HttpServletResponse response)
	{
		String productId = request.getParameter("productId");
		String redirect = request.getParameter("redirect");
		pictureService.deletePictureAndUpdateRedis(pictureId, productId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "删除成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 产品管理->图片管理->增加图片
	 * 提示:根据type,传到service中,让其自行选择是什么类型的图片(可以判断插入的picture表中的picture_type)
	 * 还有别忘了redis的更新操作(先更新数据库,再更新redis) 至于图片上传,还得需要设置FileUtils的相关设置(类型等等)--->1210
	 * 21:49分留
	 */
	@RequestMapping("/category/product/edit-pictures-add")
	public ModelAndView addProductPicturesWithType(@RequestParam("filepath") MultipartFile filepath,
			@RequestParam("productId") String productId, @RequestParam("type") String type, HttpServletRequest request,
			HttpServletResponse response)
	{

		String redirect = request.getParameter("redirect");
		PictureDTO pictureDTO = FileUtils.generateFile(filepath, PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
		pictureService.addProductPicture(pictureDTO.getPictureId(), productId, pictureDTO.getPicturePath(), type);

		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "增加图片成功");
		modelAndView.addObject("redirect", redirect);

		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->删除产品
	 */
	@RequestMapping("/category/product/edit-product-delete/{productId}")
	public ModelAndView deleteProduct(@PathVariable("productId") String productId, HttpServletRequest request,
			HttpServletResponse response)
	{
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		ModelAndView modelAndView = new ModelAndView("temp");
		try
		{
			productInfoService.deleteAndUpdateRedis(productId, categoryType);
			modelAndView.addObject("error", "删除成功");
		} catch (Exception e)
		{
			modelAndView.addObject("error", "删除失败" + e.getMessage());
		}
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}

	/*
	 * 设置属性
	 */
	@RequestMapping("/category/product/property/{productId}")
	public ModelAndView editProductProperty(@PathVariable("productId") String productId, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException
	{
		String categoryName = new String(request.getParameter("categoryName").getBytes("iso-8859-1"), "utf-8");
		String productName = new String(request.getParameter("productName").getBytes("iso-8859-1"), "utf-8");
		response.setContentType("text/html;charset=utf-8");
		List<PropertyDTO> propertyDTO = propertyService.findProductAllPropertiesAndValues(productId);
		ModelAndView modelAndView = new ModelAndView("admin_category_product_property");
		modelAndView.addObject("propertyDTO", propertyDTO);
		modelAndView.addObject("categoryName", categoryName);
		modelAndView.addObject("productName", productName);
		return modelAndView;
	}

	/*
	 * 编辑
	 */
	@RequestMapping("/category/product/update")
	public ModelAndView EditCategory(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response)
	{

		ModelAndView modelAndView = new ModelAndView("admin_category_product_edit", params);
		return modelAndView;
	}

	@RequestMapping("/category/product/do-update")
	public ModelAndView doEditCategory(ProductInfo productInfo, String redirect, HttpServletRequest request,
			HttpServletResponse response)
	{
		ModelAndView modelAndView = null;
		if (productInfoService.updateProductInfo(productInfo))
		{
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("error", "修改成功");
			modelAndView.addObject("redirect", redirect);
		} else
		{
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("error", "遇到未知错误");
			modelAndView.addObject("redirect", request.getRequestURL());
		}
		return modelAndView;
	}
}

/**
*
* @author joker 
* @date 创建时间：2018年2月13日 下午9:02:15
* 
*/
package com.tmall.store.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.dto.PageDTO;
import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.common.utils.PageUtils;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.ProductCategory;
import com.tmall.model.ProductInfo;
import com.tmall.model.Property;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.OrderDetailService;
import com.tmall.store.service.OrderService;
import com.tmall.store.service.PictureService;
import com.tmall.store.service.ProductInfoService;
import com.tmall.store.service.PropertyService;
import com.tmall.store.service.SellerService;
import com.tmall.vo.OrderDetailVO;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
* 
* @author joker 
* @date 创建时间：2018年2月13日 下午9:02:15
*/
@Controller
public class StoreController
{
	@Autowired
	private SellerService sellerService;
	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private PictureService pictureService;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	/*
	 * 类目管理->所有商品页面
	 */
	@RequestMapping("/{storeId}/category-product-view/{categoryType}")
	public ModelAndView showSellerCategoryProduct(@PathVariable("categoryType") Integer categoryType,
			@PathVariable("storeId") Integer storeId, @RequestParam("categoryName") String categoryName,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, TmallThirdPartException
	{
		categoryName = new String(categoryName.getBytes(), "UTF-8");
		String pageSizeString = request.getParameter("pageSize");
		String pageNumString = request.getParameter("pageNum");
		pageSizeString = pageSizeString == null ? "5" : pageSizeString;
		pageNumString = pageNumString == null ? "1" : pageNumString;
		Integer pageSize = Integer.parseInt(pageSizeString);
		Integer pageNum = Integer.parseInt(pageNumString);
		List<ProductInfoVO> productInfoVOList = sellerService.findSellerProductsByCategoryType(categoryType, storeId);
		PageDTO<ProductInfoVO> pageDTO = null;
		ModelAndView modelAndView = new ModelAndView("seller_category_product_view");
		if (productInfoVOList.size() > 0 && productInfoVOList != null)
		{
			pageDTO = PageUtils.pageHelper(pageSize, pageNum, productInfoVOList);
			modelAndView.addObject("productList", pageDTO.getResponseList());
			modelAndView.addObject("pageDTO", pageDTO);
			modelAndView.addObject("storeId", storeId);
			modelAndView.addObject("categoryType", categoryType);
		}
		StringBuffer redirect = request.getRequestURL().append("?categoryName=" + categoryName);

		modelAndView.addObject("redirect", redirect);
		modelAndView.addObject("categoryName", categoryName);
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->添加产品
	 */
	@RequestMapping("/{storeId}/category-product-add")
	public ModelAndView addProduct(@PathVariable("storeId") Integer storeId, ProductInfo productInfo,
			@RequestParam("picture") MultipartFile file, String redirect, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		// 这里需要前台传个参数,询问是否上架
		if (true)
		{
			productInfo.setProductStatus(ProductStatusEnums.UP.getStatus());
		}
		// 同理,先默认storeId为1
		// 1230 完成微信登陆之后storeId改为动态的
		productInfo.setStoreId(storeId);
		sellerService.addProductAndPicture(productInfo, storeId, file);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "添加成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->图片管理
	 */
	@RequestMapping("/{storeId}/category-product-picture-edit/{productId}")
	public ModelAndView editProductPicture(@RequestParam Map<String, Object> params,
			@PathVariable("storeId") Integer storeId, @PathVariable("productId") String productId,
			HttpServletRequest request, HttpServletResponse response)
	{
		PictureVO pictureVO = sellerService.findProductAllPictures(productId);
		params.put("pictureVO", pictureVO);
		ModelAndView modelAndView = new ModelAndView("seller_category_product_picture", params);
		String queryStr=request.getQueryString();
		String redirect=request.getRequestURL()+"?"+queryStr;
		modelAndView.addObject("redirect", redirect);
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->图片管理->添加图片
	 */
	@RequestMapping("/{storeId}/category-product-picture-add")
	public ModelAndView addPicture(@RequestParam("filepath") MultipartFile file,String productId, String type,
			String redirect, HttpServletRequest request, HttpServletResponse response)
	{
		sellerService.addProductTypePicture(productId, type, file);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "添加成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->图片管理->设为首页展示图片
	 */
	@RequestMapping("/category-product-picture-set2index")
	public ModelAndView set2IndexPicture(HttpServletRequest request, HttpServletResponse response)
	{
		String pictureId = request.getParameter("pictureId");
		String productId = request.getParameter("productId");
		String error = null;
		try
		{
			sellerService.set2indexPicture(pictureId, productId);
		} catch (Exception e)
		{
			error = "设置失败" + e.getMessage();
			throw new TmallException(error);
		}
		error = "设置成功";
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", error);
		modelAndView.addObject("redirect",
				"http://localhost:8094/seller/category-info?openid=odoWxxB7Ivx6-BAfstIXL-Ervd0Y");
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->图片管理->删除图片
	 */
	@RequestMapping("/{storeId}/categpory-product-picture-delete")
	public ModelAndView deleteOneProductPicture(HttpServletRequest request, HttpServletResponse response)
	{
		String pictureId = request.getParameter("pictureId");
		String error = null;
		try
		{
			pictureService.deleteProductOnePictureByPictureId(pictureId);
		} catch (Exception e)
		{
			error = "删除出现异常";
			throw new TmallException(error);
		}
		error = "删除成功";
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", error);
		modelAndView.addObject("redirect", request.getParameter("redirect"));

		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->属性管理
	 */
	@RequestMapping("/{storeId}/category-product-property-edit/{productId}")
	public ModelAndView editProductProperty(@RequestParam Map<String, Object> params,
			@PathVariable("storeId") Integer storeId, @PathVariable("productId") String productId,
			HttpServletRequest request, HttpServletResponse response)
	{
		List<PropertyDTO> propertyDTOList = sellerService.findProductAllPropertyValues(productId);
		params.put("propertyDTO", propertyDTOList);
		ModelAndView modelAndView = new ModelAndView("seller_category_product_property", params);
		// 这里需要修改redirect的值
		modelAndView.addObject("redirect", request.getRequestURL());
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->编辑 因为这里是后台管理,首先并发量不会高,而且不可以采用url传值的形式展示参数
	 */
	@RequestMapping("/{storeId}/category-product-edit/{productId}")
	public ModelAndView editProduct(@PathVariable("productId") String productId, @PathVariable("storeId")Integer storeId,HttpServletRequest request,
			HttpServletResponse response)
	{
		ProductInfoVO productInfoVO = productInfoService.findOneProduct(productId);
		ModelAndView modelAndView = new ModelAndView("seller_category_product_edit");
		modelAndView.addObject("product", productInfoVO);
		System.out.println(request.getParameter("redirect"));
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		modelAndView.addObject("storeId",storeId);
		return modelAndView;
	}

	@RequestMapping("/{storeId}/category-product-do-edit")
	public ModelAndView doEditProduct(@PathVariable("storeId")Integer storeId,ProductInfoVO productInfoVO, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		// 关于这个storeId,是aop获取的,反正是需要显示姓名啥的
		// 所以,现阶段所有的storeId直接先固定好再说
		String error = null;
		productInfoVO.setStoreId(storeId);
		if (productInfoService.updateProductInfo(productInfoVO))
		{
			error = "更新成功";
		} else
		{
			// 这里可以绑定一些条件,然后如果错误的话显示在前端页面上
			error = "更新失败,未知错误";
		}
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", error);
		// 这里先直接写死吧,等aop完结之后,使得后面的openid能够动态生成
		// modelAndView.addObject("redirect",
		// "http://localhost:8094/seller/category-info?openid=odoWxxB7Ivx6-BAfstIXL-Ervd0Y");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}

	/*
	 * 类目管理->产品管理->删除产品 删除产品的同时还需要更新redis
	 */
	@RequestMapping("/{storeId}/category-product-delete/{productId}")
	public ModelAndView deleteProduct(@PathVariable("storeId")Integer storeId,@PathVariable("productId") String productId, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		// 这里也先直接
		productInfoService.deleteAndUpdateRedis(productId, categoryType,storeId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "删除成功");
		// modelAndView.addObject("redirect",
		// "http://localhost:8094/seller/category-info?openid=odoWxxB7Ivx6-BAfstIXL-Ervd0Y");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}
	/*
	 * --------------------------------------------------------------------------------------------------------------=
	 * ==============================================================================================================
	 */
	@RequestMapping("/{storeId}/index")
	public ModelAndView storeIndex(@PathVariable String storeId, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		ModelAndView modelAndView = null;
		String count = jedisClient.get(String.format(RedisConstant.PORTAL_STORE_TOTAL_COUNT, ":" + storeId));
		modelAndView = new ModelAndView("seller_index");
		modelAndView.addObject("storeId", storeId);
		modelAndView.addObject("count", count);
		return modelAndView;
	}

	/*
	 * 卖家类目管理
	 */
	@RequestMapping("/{storeId}/category-info")
	public ModelAndView showSellerCategory(@PathVariable("storeId") Integer storeId,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, TmallThirdPartException
	{
		ModelAndView modelAndView = new ModelAndView("seller_category_view");
		List<ProductCategoryVO> storeCategoryList = sellerService.findStoreCategoryList(storeId);
		if (storeCategoryList != null && storeCategoryList.size() > 0)
		{
			PageDTO<ProductCategoryVO> pageDTO = PageUtils.pageHelper(pageSize, pageNum, storeCategoryList);
			response.setContentType("text/html;charset=utf-8");
			modelAndView.addObject("categoryList", storeCategoryList);
			modelAndView.addObject("pageDTO", pageDTO);
		}
		modelAndView.addObject("redirect",
				new String((request.getRequestURL() + "?" + request.getQueryString()).getBytes("iso-8859-1"), "UTF-8"));
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->添加类目
	 */
	// @RequestMapping(value="/{storeId}/category-add",method=RequestMethod.POST)
	// public ModelAndView addCategory(ProductCategoryVO productCategory,
	// @RequestParam("picture") MultipartFile file,
	// @PathVariable("storeId") Integer storeId, String redirect, HttpServletRequest
	// request,
	// HttpServletResponse response) throws TmallThirdPartException
	// {
	// sellerService.addCategoryAndPicture(productCategory, storeId, file);
	// ModelAndView modelAndView = new ModelAndView("temp");
	// modelAndView.addObject("error", "添加成功");
	// modelAndView.addObject("redirect", redirect);
	// return modelAndView;
	// }
	public ModelAndView addCategory(ProductCategoryVO productCategory, @PathVariable("storeId") Integer storeId,
			String redirect, HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		sellerService.addCategoryAndPicture(productCategory, storeId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "添加成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}
	/*
	 * 类目管理->删除类目
	 */
	@RequestMapping("/{storeId}/category-delete")
	public ModelAndView deleteCategory(@PathVariable("storeId") Integer storeId, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		// 同样这里的storeId先默认为1,并且默认为删除所有商品
		// 108 动态修改storeId
		// 212修改逻辑,storeId从url中获取
		String redirect = request.getParameter("redirect");
		if (!StringUtils.isEmpty(redirect))
		{
			redirect = ProjectURLConxtant.STORE_INDEX_URL;
		}
		sellerService.deleteCategoryByCategoryType(categoryType, storeId, false);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "删除成功,对应的商品已经全改为下架");
		modelAndView.addObject("redirect", redirect);
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->编辑
	 */
	@RequestMapping("/{storeId}/category-edit")
	public ModelAndView editCategory(@PathVariable("storeId")Integer storeId,@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response)
	{
		params.put("storeId", storeId);
		ModelAndView modelAndView = new ModelAndView("seller_category_edit", params);
		return modelAndView;
	}

	/*
	 * 类目管理->编辑->提交form表单
	 */
	@RequestMapping("/{storeId}/category-do-edit")
	public ModelAndView doEditCategory(@PathVariable("storeId") Integer storeId,
			@RequestParam("picture") MultipartFile file, String oldPicturePath, ProductCategory productCategory,
			HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		sellerService.updateCategory(productCategory, storeId, file, oldPicturePath);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "修改成功");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}

	/*
	 * 类目管理->属性管理
	 */
	@RequestMapping("/{storeId}/category-property/{categoryType}")
	public ModelAndView editCategoryProperty(@PathVariable("categoryType") Integer categoryType,
			@PathVariable("storeId") Integer storeId, @RequestParam Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{

		List<Property> propertyList = sellerService.findPropertyListByCategoryType(categoryType, storeId);
		String reqString=request.getRequestURI();
		String queryStr=request.getQueryString();
		String redirect=reqString+"?"+queryStr;
		params.put("propertyList", propertyList);
		params.put("categoryType", categoryType);
		params.put("redirect", redirect);
		params.put("storeId", storeId);
		ModelAndView modelAndView = new ModelAndView("seller_category_property_view", params);
		return modelAndView;
	}

	/*
	 * 类目管理->属性管理->编辑
	 */
	@RequestMapping("/{storeId}/category-property-edit")
	public ModelAndView editCategoryProperty(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		String reqString=request.getRequestURI();
		String queryStr=request.getQueryString();
		String redirect=reqString+"?"+queryStr;
		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("seller_category_property_edit", params);
		return modelAndView;
	}

	/*
	 * 类目管理->属性管理->编辑->提交form表单
	 */
	@RequestMapping(value = "/{storeId}/category-property-do_edit", method = RequestMethod.POST)
	public ModelAndView doEditProperty(Property property, Integer categoryType,
			@PathVariable("storeId") Integer storeId, HttpServletRequest request, HttpServletResponse response)
			throws TmallThirdPartException
	{
		propertyService.updatePropertyByPropertyId(property, categoryType, storeId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "更新成功");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}

	/*
	 * 类目管理->属性管理->删除
	 */
	@RequestMapping("/{storeId}/category-property-delete")
	public ModelAndView deleteProperty(@PathVariable("storeId") Integer storeId, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		String redirect = request.getParameter("redirect");
		Integer propertyId = Integer.parseInt(request.getParameter("propertyId"));
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		propertyService.deletePropertyByPropertyId(propertyId, categoryType, storeId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "删除成功");
		// 这里还是先直接将返回的url写死
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	/*
	 * 类目管理->属性管理->添加属性
	 */
	@RequestMapping("/{storeId}/category-property-add")
	public ModelAndView addProperty(@PathVariable("storeId") Integer storeId, Property property, String redirect,
			HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		Integer categoryType = Integer.parseInt(request.getParameter("categoryType"));
		property.setProductCategoryId(categoryType);
		propertyService.addProperty(property, categoryType, storeId);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "添加成功");
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}
	/*
	 * =============================================================================================================
	 * =============================================================================================================
	 */
	/*
	 * 订单管理
	 */
	@RequestMapping("/{storeId}/order-all")
	public ModelAndView showStoreAllOrders(@PathVariable("storeId") Integer storeId,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		List<OrderDTO> orderDTOList = orderService.findStoreAllOrders(storeId);
		ModelAndView modelAndView = new ModelAndView("seller_order_view");
		if (orderDTOList != null && orderDTOList.size() > 0)
		{
			PageDTO<OrderDTO> pageDTO = PageUtils.pageHelper(pageSize, pageNum, orderDTOList);
			modelAndView.addObject("orderDTOList", pageDTO.getResponseList());
			modelAndView.addObject("pageDTO", pageDTO);
		}
		modelAndView.addObject("redirect", request.getRequestURL());
		modelAndView.addObject("storeId", storeId);
		return modelAndView;
	}
	/*
	 * 订单管理->立即发货
	 */
	@RequestMapping("/{storeId}/order-delivery-goods/{orderDetailId}")
	public ModelAndView deliveryGoods(@PathVariable("orderDetailId") String orderDetailId, HttpServletRequest request,
			HttpServletResponse response)
	{
		OrderDetailVO ordertailVO = orderDetailService.findOneOrderDetail(orderDetailId);
		ordertailVO.setOrderStatus(OrderStatusEnum.WAIT_RECEIPT_GOOS.getCode());
		ordertailVO.setDeliverDate(new Date());
		orderDetailService.updateOrderDetail(ordertailVO);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "发货成功");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}
}

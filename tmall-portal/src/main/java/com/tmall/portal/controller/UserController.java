/**
 * 
 */
package com.tmall.portal.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.CartDTO;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.OrderMaster;
import com.tmall.portal.dao.impl.JedisClient;
import com.tmall.portal.service.CartService;
import com.tmall.portal.service.OrderService;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.portal.service.ReviewService;
import com.tmall.portal.service.UserService;
import com.tmall.portal.utils.PortalUtils;
import com.tmall.vo.OrderDetailVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController
{
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ProductInfosService productInfosService;
	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;
	private Gson gson = new Gson();

	@RequestMapping("/order/confirm_order")
	public ModelAndView buyProduct(HttpServletRequest request, HttpServletResponse response, Model model) throws TmallThirdPartException
	{
		PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER, RedisConstant.PORTAL_USER_PREFIX, request);
		ModelAndView modelAndView = null;
		// UserDTO user = (UserDTO) session.getAttribute("user");
		Map<String, Object> params = new HashMap<String, Object>();
		// if (user == null)
		// {
		// StringBuffer redirect =
		// request.getRequestURL().append("?").append(request.getQueryString());
		// params.put("redirect", new String(redirect));
		// params.put("error", "请先登陆");
		// modelAndView = new ModelAndView("redirect:/login.html", params);
		// return modelAndView;
		// }
		String productId = request.getParameter("productId");

		ResultVo<ProductInfoVO> resultVOProduct = productInfosService.findOneProduct(productId);
		ProductInfoVO productInfoVO = gson.fromJson(gson.toJson(resultVOProduct.getData()), ProductInfoVO.class);
		double price = StringUtils.isEmpty(productInfoVO.getProductPromotePrice()) ? productInfoVO.getProductPrice()
				: productInfoVO.getProductPromotePrice();
		Integer buyNum = Integer.parseInt(request.getParameter("buyNum"));
		double totalMoney = price * buyNum;
		params.put("product", resultVOProduct.getData());
		params.put("buyNum", buyNum);
		params.put("totalMoney", totalMoney);
		modelAndView = new ModelAndView("user_buy_product_view", params);
		return modelAndView;
	}
	/*
	 * 下单
	 */

	@RequestMapping("/order/upload_confirm_order")
	public ModelAndView doConfirmOrder(OrderDTO orderDTO,
			@RequestParam(name = "pid", required = false) String productId,
			@RequestParam(name = "buyNum", required = false) String buyNum, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException, TmallPortalException, TmallThirdPartException
	{
		ModelAndView modelAndView = null;
		Map<String, Object> params = new HashMap<String, Object>();
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		String content = new String(orderDTO.getContent().getBytes("iso-8859-1"), "utf-8");
		LinkedList<String> contentList = PortalUtils.string2LinkedList(content);

		LinkedList<String> productIds = PortalUtils.string2LinkedList(productId);

		LinkedList<String> buyNums = PortalUtils.string2LinkedList(buyNum);

		orderDTO.setBuyerOpenid(user.getOpenid());
		//这其实是错误的做法,因为当coder购买然后在url上更改storeId的参数之后就会造成订单混乱,这里最好是自己去查
//		orderDTO.setStoreId(storeId);
		Integer storeId = productInfosService.findProductStoreIdByProductId(productId);
		if(storeId==null)
		{
			logger.error("[用户下单]商品所属店铺为空");
			throw new TmallPortalException(ResultEnums.PRODUCT_EXIST_ILLEGAL);
		}
		orderDTO.setStoreId(storeId);
		orderDTO = orderService.create(orderDTO, productIds, buyNums, contentList);
		/*
		 * 同时删除cookie里的商品 因为前端页面是别人的,而前端的一些操作都是基于oiid的 所以没办法,cookie操作也得基于oiid了
		 */
		List<String> oiidList = new ArrayList<>();
		for (String string : productIds)
		{
			StringBuffer newStr = new StringBuffer();
			newStr.append("usercart").append(string);
			oiidList.add(new String(newStr));
		}
		PortalUtils.deleteCookie(request, response, oiidList);
		params.put("orderDTO", orderDTO);
		modelAndView = new ModelAndView("user_pay_money", params);
		return modelAndView;
	}

	@RequestMapping("/cart/add")
	public ModelAndView addCart(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		String productId = request.getParameter("productId");
		// ProductInfoVO productInfoVO =
		// gson.fromJson(gson.toJson(productInfosService.findOneProduct(productId).getData()),
		// ProductInfoVO.class);
		// String value = gson.toJson(productInfoVO);
		// Cookie cookie=new Cookie(productId, value);
		cartService.write2Cookie(productId, request, response);
		ModelAndView modelAndView = new ModelAndView("redirect:/user/cart/view.html");
		return modelAndView;
	}

	@RequestMapping("/cart/view")
	public ModelAndView showShoppingCart(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> params = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
		{
		}
		List<CartDTO> cartList = new ArrayList<>();
		for (Cookie cookie : cookies)
		{
			CartDTO cartDTO = new CartDTO();
			String cookieName = cookie.getName();
			if (!cookieName.contains("usercart"))
			{
				continue;
			}
			cartDTO.setOoId(cookieName);
			String value = cookie.getValue();
			ProductInfoVO productInfoVO = gson.fromJson(value, ProductInfoVO.class);
			cartDTO.setProductInfoVO(productInfoVO);
			cartList.add(cartDTO);
		}
		params.put("cartList", cartList);
		params.put("redirect", request.getRequestURL());
		ModelAndView modelAndView = new ModelAndView("user_shopping_cart", params);
		return modelAndView;
	}

	@RequestMapping("/cart/delete")
	public ModelAndView deleteOneProductInShoppingCart(HttpServletRequest request, HttpServletResponse response)
	{
		String redirect = request.getParameter("redirect");
		String ooId = request.getParameter("oiid");
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals(ooId))
			{
				cookie.setValue(null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				break;
			}
		}
		return new ModelAndView("redirect:" + redirect);
	}

	/*
	 * 提交购物车的订单
	 */
	@RequestMapping("/cart/Settlement")
	public ModelAndView uploadOrderCart(@RequestParam(name = "oiid", required = false) String oiid,
			@RequestParam("buyNum") String buyNum, HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println(oiid);

		LinkedList<String> oiidList = PortalUtils.string2LinkedList(oiid);
		LinkedList<String> productIds = new LinkedList<>();
		for (int i = 0; i < oiidList.size(); ++i)
		{
			String string = oiidList.get(i).substring("usercart".length());
			productIds.add(string);
		}
		LinkedList<String> buyNumList = PortalUtils.string2LinkedList(buyNum);
		List<ProductInfoVO> productInfoVOs = productInfosService.findProductsInIds(productIds);
		for (int i = 0; i < productInfoVOs.size(); ++i)
		{
			for (int j = i; j < buyNumList.size();)
			{
				productInfoVOs.get(i).setProductQuantity(Integer.parseInt(buyNumList.get(j)));
				break;
			}
		}
		Map<String, Object> params = new HashMap<>();
		params.put("productList", productInfoVOs);
		// params.put("buyNumList", buyNumList);
		ModelAndView modelAndView = new ModelAndView("user_cart_product_view", params);
		return modelAndView;
	}

	@RequestMapping("/order/{type}")
	public ModelAndView showUserOrders(@PathVariable("type") String type, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		Map<String, Object> params = new HashMap<>();
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		String openid = user.getOpenid();
		List<OrderDTO> orderDTOList = orderService.findUserALLOrders(type, openid);
//		List<OrderDTO> userOrderDTOList = userService.findUserOrders(type, openid);
		params.put("orderDTOList", orderDTOList);
		params.put("redirect", request.getRequestURL());
		ModelAndView modelAndView = new ModelAndView("user_orders_view", params);
		return modelAndView;
	}

	/*
	 * 立即支付
	 */
	@RequestMapping("/order/pay")
	public ModelAndView orders_pay(HttpServletRequest request, HttpServletResponse response)
	{
		String orderDetailId = request.getParameter("orderDetailId");
		LinkedList<String> orderDetailIds = PortalUtils.string2LinkedList(orderDetailId);
		List<OrderDetailVO> orderDetailList = new ArrayList<>();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		// 这里需要改,不要一个一个查询
		for (String string : orderDetailIds)
		{
			OrderDetailVO orderDetail = orderService.findOneOrderDetail(string);
			BigDecimal temp = orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()));
			orderAmount = temp.add(orderAmount);
			orderDetailList.add(orderDetail);
		}
		Map<String, Object> params = new HashMap<>();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(orderDetailList.get(0).getOrderId());
		orderDTO.setOrderAmount(orderAmount);
		orderDTO.setOrderDetailList(orderDetailList);
		params.put("orderDTO", orderDTO);
		ModelAndView modelAndView = new ModelAndView("user_pay_money", params);
		return modelAndView;
	}

	/*
	 * 提交立即支付的form表单
	 */
	@RequestMapping("/order/do_pay")
	public ModelAndView do_orders_pay(String orderDetailId, HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{

		// String orderDetailId = params.get("orderDetailId").toString();

		Date payDate = new Date();
		LinkedList<String> orderDetailIdList = PortalUtils.string2LinkedList(orderDetailId);
		// UserDTO user=(UserDTO) request.getSession().getAttribute("user");
		// if (user == null)
		// {
		// ModelAndView modelAndView=new ModelAndView("redirect:/login.html");
		// modelAndView.addObject("error","请先登陆");
		// modelAndView.addObject("redirect",request.getParameter("redirect"));
		// return modelAndView;
		// }
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		// Cookie[] cookies = request.getCookies();
		// for (Cookie cookie : cookies)
		// {
		// if(cookie.getName().equals(CookieConstant.TOKEN))
		// {
		// String json = jedisClient.get(String.format(RedisConstant.TOKEN_PREFIX,
		// cookie.getValue()));
		// user=JsonUtils.jsonToPojo(json, UserDTO.class);
		// }
		// }
		for (String string : orderDetailIdList)
		{
			OrderDetailVO orderDetail = orderService.findOneOrderDetail(string);
			orderDetail.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.getCode());
			orderDetail.setPayStatus(PayStatusEnum.SUCESS_PAY_MONEY.getCode());
			orderService.updateOrderDetail(orderDetail, user.getOpenid());
		}
		String orderId = request.getParameter("orderId");
		OrderMaster orderMaster = orderService.findOneOrderMaster(orderId);
		orderMaster.setPayStatus(PayStatusEnum.SUCESS_PAY_MONEY.getCode());
		orderMaster.setPayDate(payDate);
		orderService.updateOrderMaster(orderMaster);
		// 这里最好是利用前端技术将/user/order/pay上的值传过来,而不是再去数据库查
		/*
		 * 这里需要调用微信支付接口 String productId=params.get("productId").toString(); Integer
		 * buyNum=Integer.parseInt(params.get("buyNum").toString());
		 */
		// OrderDetail orderDetail = orderService.findOneOrderDetail(orderDetailId);
		// orderDetail.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.getCode());
		// orderDetail.setPayStatus(PayStatusEnum.SUCESS.getCode());
		// orderService.updateOrderDetail(orderDetail);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "支付成功");
		modelAndView.addObject("redirect", "http://localhost:8093/user/order/all.html");
		return modelAndView;
	}

	/*
	 * 催促发货
	 */
	@RequestMapping("/order/urgency")
	public ModelAndView urgeSellerDeliver(HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		/*
		 * 这里应该通过orderDetail中的productId获取卖家 然后给他发个催促的信息,然后卖家收到之后回复信息, 然后修改状态为:已发货
		 * 但是,现在的话就只需要修改状态即可 这里之后是需要改的
		 */
		// UserDTO user=(UserDTO) request.getSession().getAttribute("user");
		// if (user == null)
		// {
		// ModelAndView modelAndView=new ModelAndView("redirect:/login.html");
		// modelAndView.addObject("error","请先登陆");
		// modelAndView.addObject("redirect",request.getParameter("redirect"));
		// return modelAndView;
		// }
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		// Cookie[] cookies = request.getCookies();
		// for (Cookie cookie : cookies)
		// {
		// if(cookie.getName().equals(CookieConstant.TOKEN))
		// {
		// String json = jedisClient.get(String.format(RedisConstant.TOKEN_PREFIX,
		// cookie.getValue()));
		// user=JsonUtils.jsonToPojo(json, UserDTO.class);
		// }
		// }
		Map<String, Object> params = new HashMap<>();
		String orderDetailId = request.getParameter("orderDetailId");
		OrderDetailVO orderDetail = orderService.findOneOrderDetail(orderDetailId);
		// 因为是单机版,所以直接设置为receipt-goods操作
		orderDetail.setOrderStatus(OrderStatusEnum.RECEIPT_GOODS.getCode());
		/*
		 * 这里当买家收货之后要将商品的销售数量更新,但是redis中的更新的话通过expire让其自动更新即可
		 * 并且这里得从数据库中查,不能去redis中查---其实这里是不需要这个操作的,但是单机版默认已接收
		 */
		ProductInfoVO productInfoVO = productInfosService.findOneProductFromDB(orderDetail.getProductId());
		productInfosService.updateProductSelledNumber(productInfoVO, orderDetail.getProductQuantity());

		orderDetail.setDeliverDate(new Date());
		orderDetail.setReceiptDate(new Date());
		orderService.updateOrderDetail(orderDetail, user.getOpenid());
		params.put("error", "已发货,并且默认已接收~~");
		params.put("redirect", request.getParameter("redirect"));
		return new ModelAndView("/temp", params);
	}

	/*
	 * 退款操作,取消订单 这里有大问题感觉,感觉跟数据库设置有关系
	 * ordermaster中orderstatus,paystatus和orderDetail中的orderstatus和paystatus的取舍问题
	 * 做个取舍吧,ordermaster中保留payStatus 而orderdetail中保留orderstatus
	 */
	@RequestMapping("/order/refund")
	public ModelAndView cancleOrderAndRefund(HttpServletRequest request, HttpServletResponse response) throws TmallPortalException, TmallThirdPartException
	{
		BigDecimal refundMoney = new BigDecimal(BigInteger.ZERO);
		String orderId = request.getParameter("orderId");
		List<OrderDetailVO> orderDetailVOList = orderService.findOrderDetailListrWithOrderMaster(orderId);
		if(orderDetailVOList==null||orderDetailVOList.size()<=0)
		{
			throw new TmallPortalException(ResultEnums.ORDERDETAIL_NOT_EXIT);
		}
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		// if (user == null)
		// {
		// ModelAndView modelAndView=new ModelAndView("redirect:/login.html");
		// modelAndView.addObject("error","请先登陆");
		// modelAndView.addObject("redirect",request.getParameter("redirect"));
		// return modelAndView;
		// }
		// 修改主订单的状态
		OrderMaster orderMaster = orderService.findOneOrderMaster(orderId);
		if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCESS_PAY_MONEY.getCode()))
		{
			// 退款操作,并且设置为等待退款
			orderMaster.setPayStatus(PayStatusEnum.WAIT_REFUND.getCode());
		}
		// 这里最好不要for循环更新
		for (OrderDetailVO orderDetailVO : orderDetailVOList)
		{
			if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCESS_PAY_MONEY.getCode()))
			{
				// 计算退款金钱
				refundMoney = orderDetailVO.getProductPrice()
						.multiply(new BigDecimal(orderDetailVO.getProductQuantity())).add(refundMoney);
			}
			orderDetailVO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
			orderService.updateOrderDetail(orderDetailVO, user.getOpenid());

		}

		// 因为是单机版,所以直接设置为已退款
		orderMaster.setPayStatus(PayStatusEnum.SUCESS_REFUND.getCode());
		orderService.updateOrderMaster(orderMaster);
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "申请退款成功");
		// redirect的先这样写
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;
	}

	/*
	 * 这应该是删除订单,而不是取消订单,再改~~~~主要是不知道咋改,是改状态让买家看不到呢还是咋样 17:40
	 * :这个不能将数据库中删除,因为卖家还是需要看的哪些人买了啥东西
	 */
	@RequestMapping("/order/delete")
	public ModelAndView deleteOrder(@RequestParam(name = "orderId", required = false) String orderId,
			@RequestParam(name = "orderDetailId", required = false) String orderDetailIds, HttpServletRequest request,
			HttpServletResponse response) throws TmallThirdPartException
	{
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		// if (user == null)
		// {
		// ModelAndView modelAndView=new ModelAndView("redirect:/login.html");
		// modelAndView.addObject("error","请先登陆");
		// modelAndView.addObject("redirect",request.getParameter("redirect"));
		// return modelAndView;
		// }
		LinkedList<String> orderDetailList = PortalUtils.string2LinkedList(orderDetailIds);
		List<CartDTO> cartDTOList = new ArrayList<>();
		for (String orderDetailId : orderDetailList)
		{
			OrderDetailVO orderDetail = orderService.findOneOrderDetail(orderDetailId);
			orderDetail.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
			// if (orderDetail.getPayStatus() == PayStatusEnum.SUCESS_PAY_MONEY.getCode())
			// {
			// // 退款---这个逻辑不应该出现在这里,既然按死了决定ordermaster保留paystatus,那样的话退款都交给他
			// //并且退款是点击orderId的那个退款(退master下的所有orderId)
			// // BigDecimal money=orderDetail.getProductPrice().multiply(new
			// // BigDecimal(orderDetail.getProductQuantity()));
			// orderDetail.setPayStatus(PayStatusEnum.WAIT_REFUND.getCode());
			// }
			orderService.updateOrderDetail(orderDetail, user.getOpenid());
			CartDTO cartDTO = new CartDTO();
			cartDTO.setProductId(orderDetail.getProductId());
			cartDTO.setProductQuantity(orderDetail.getProductQuantity());
			cartDTOList.add(cartDTO);
		}
		OrderMaster orderMaster = orderService.findOneOrderMaster(orderId);
		if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCESS_PAY_MONEY.getCode()))
		{
			orderMaster.setPayStatus(PayStatusEnum.WAIT_REFUND.getCode());
		}
		orderService.updateOrderMaster(orderMaster);
		productInfosService.increateStock(cartDTOList);
		/*
		 * 同理,这里适合用list批量操作 同理,只需要修改状态就行 如果已经付钱了payStatus==1的话就需要给买家退款 并且商品的库存++
		 */
		// String orderDetailId = request.getParameter("orderDetailId");
		// OrderDetail orderDetail = orderService.findOneOrderDetail(orderDetailId);
		// orderDetail.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
		// if (orderDetail.getPayStatus() == PayStatusEnum.SUCESS.getCode())
		// {
		// 退款
		// BigDecimal money=orderDetail.getProductPrice().multiply(new
		// BigDecimal(orderDetail.getProductQuantity()));
		// }
		// 增加库存
		// List<CartDTO> cartDTOList = new ArrayList<>();
		// CartDTO cartDTO = new CartDTO();
		// cartDTO.setProductId(orderDetail.getProductId());
		// cartDTO.setProductQuantity(orderDetail.getProductQuantity());
		// cartDTOList.add(cartDTO);
		// productInfosService.increateStock(cartDTOList);
		ModelAndView modelAndView = new ModelAndView("/temp");
		modelAndView.addObject("error", "取消订单成功");
		modelAndView.addObject("redirect", "http://127.0.0.1:8093/user/order/all.html");
		logger.info("[取消订单],用户于{}取消了{}订单", new Date(), orderMaster.getOrderId());
		return modelAndView;
	}

	/*
	 * 直接删除一个orderMaster
	 */
	@RequestMapping("/order/delete/type")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> params = new HashMap<>();

		ModelAndView modelAndView = new ModelAndView("/temp", params);

		return modelAndView;
	}

	/*
	 * 确认收货
	 */
	@RequestMapping("/order/confirm-receipt-goods")
	public ModelAndView receiptGoods(HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		// if (user == null)
		// {
		// ModelAndView modelAndView=new ModelAndView("redirect:/login.html");
		// modelAndView.addObject("error","请先登陆");
		// modelAndView.addObject("redirect",request.getParameter("redirect"));
		// return modelAndView;
		// }
		String orderDetailId = request.getParameter("orderDetailId");
		OrderDetailVO orderDetailVO = orderService.findOneOrderDetail(orderDetailId);
		orderDetailVO.setOrderStatus(OrderStatusEnum.RECEIPT_GOODS.getCode());
		orderDetailVO.setReceiptDate(new Date());
		orderService.updateOrderDetail(orderDetailVO, user.getOpenid());

		/*
		 * 这里当买家收货之后要将商品的销售数量更新,但是redis中的更新的话通过expire让其自动更新即可 并且这里得从数据库中查,不能去redis中查
		 */
		ProductInfoVO productInfoVO = productInfosService.findOneProductFromDB(orderDetailVO.getProductId());
		productInfosService.updateProductSelledNumber(productInfoVO, orderDetailVO.getProductQuantity());
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", "确认收货成功");
		modelAndView.addObject("redirect", request.getParameter("redirect"));
		return modelAndView;

	}

	@RequestMapping("/product/review")
	public ModelAndView productReview(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> params = new HashMap<>();

		String redirect=request.getParameter("redirect");
		String productId = request.getParameter("productId");
		String orderDetailId = request.getParameter("orderDetailId");
		OrderDetailVO orderDetail = orderService.findOneOrderDetail(orderDetailId);
		ResultVo<ProductInfoVO> resultVo = productInfosService.findOneProduct(productId);
		Integer reviewTotalCount = reviewService.getTotalReviewCount(productId);
		ProductInfoVO productInfoVO = gson.fromJson(gson.toJson(resultVo.getData()), ProductInfoVO.class);

		params.put("reviewTotalCount", reviewTotalCount);
		params.put("orderDetail", orderDetail);
		params.put("productInfoVO", productInfoVO);
		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("user_product_review", params);
		return modelAndView;
	}

	@RequestMapping("/product/do_review")
	public ModelAndView doProductReview(String productId, String orderDetailId, String content,String redirect,
			HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{

		UserDTO user = PortalUtils.findUserFromCookieAndRedis(CookieConstant.PORTAL_USER,RedisConstant.PORTAL_USER_PREFIX,request);
		Map<String, Object> params = new HashMap<>();
		reviewService.addReview(productId, content, user.getUserId(), user.getNickName());
		// if(!isSucess)
		// { boolean isSucess=
		// StringBuffer url = request.getRequestURL();
		// params.put("redirect", url);
		// params.put("error", "评论失败,请稍后再试");
		// return new ModelAndView("/temp",params);
		// }
		// 直接修改状态就好了,不需要先查找,不过从安全角度的话是先查好点
		OrderDetailVO orderDetail = orderService.findOneOrderDetail(orderDetailId);
		orderDetail.setOrderStatus(OrderStatusEnum.FINISH.getCode());
		orderService.updateOrderDetail(orderDetail, user.getOpenid());
		params.put("error", "评论成功");
		params.put("redirect", redirect);
		ModelAndView modelAndView = new ModelAndView("temp", params);
		return modelAndView;
	}

}

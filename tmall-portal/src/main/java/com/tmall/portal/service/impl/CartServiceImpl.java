/**
 * 
 */
package com.tmall.portal.service.impl;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tmall.common.vo.ResultVo;
import com.tmall.portal.service.CartService;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class CartServiceImpl implements CartService
{
	@Autowired
	private ProductInfosService productInfosService;
	private static Gson gson = new Gson();

	@Override
	public void write2Cookie(String productId, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		String ooid = "usercart" + productId;
		response.setContentType("text/html;charset=UTF-8");
		// Cookie[] cookies = request.getCookies();
		//
		// if (cookies != null)
		// {
		//
		// for (Cookie cookie : cookies)
		// {
		// if (cookie.getName().equals(productId))
		// {
		// // 删除cookie
		// cookie.setMaxAge(0);
		// response.addCookie(cookie);
		// }
		// // String json = cookie.getValue();
		// // ProductInfoVO productInfoVO = gson.fromJson(json, ProductInfoVO.class);
		// }
		// }

		ResultVo<ProductInfoVO> resultVo = productInfosService.findOneProduct(productId);
		ProductInfoVO productInfoVO = gson.fromJson(gson.toJson(resultVo.getData()), ProductInfoVO.class);
		String value = gson.toJson(productInfoVO);
		Cookie cookie = new Cookie(ooid, value);
		cookie.setComment(productId);
		cookie.setPath("/");
		cookie.setMaxAge(24 * 60 * 60);
		response.addCookie(cookie);
	}

}

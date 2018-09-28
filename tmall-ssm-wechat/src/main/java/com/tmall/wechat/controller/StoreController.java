/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午1:26:18
* 
*/
package com.tmall.wechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.TmallUserEnums;
import com.tmall.common.utils.CookieUtils;
import com.tmall.common.utils.FileUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.TmallUser;
import com.tmall.wechat.dao.impl.JedisClient;
import com.tmall.wechat.service.ITmallUserService;
import com.tmall.wechat.service.StoreService;

/**
 * 
 * @author joker
 * @date 创建时间：2018年1月27日 下午1:26:18
 */
@Controller
@RequestMapping("/store")
public class StoreController
{
	@Autowired
	private ITmallUserService tmallUserService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private JedisClient jedisClient;

	@RequestMapping("/do_login")
	public ModelAndView storeDoLogin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = null;
		Map<String, String> params = new HashMap<String, String>();
		String key = request.getParameter("key");
		String password = request.getParameter("password");
		TmallUser seller = tmallUserService.findUserByKey(key);
		String error = "";
		if (seller != null)
		{
			if (!seller.getPassword().equals(password))
			{
				error = "账户不存在或者密码错误";
			}else {
				modelAndView=new ModelAndView("redirect:"+ProjectURLConxtant.STORE_INDEX_URL);
				String uuid = UUID.randomUUID().toString();
				String value=JsonUtils.objectToJson(seller);
				CookieUtils.setCookie(response, CookieConstant.STORE_SELLER, uuid, CookieConstant.STORE_SELLER_EXPIRE);;
				String key2 = String.format(RedisConstant.STORE_SELLER_PREFIX, uuid);
				jedisClient.set(key2, value);
				jedisClient.expire(key2, RedisConstant.STORE_SELLER_EXPIRE);
			}
		} else
		{
			error = "账户不存在或者密码错误";
		}
		if (!"".equals(error))
		{
			params.put("error", error);
			params.put("redirect", ProjectURLConxtant.STORE_LOGIN_URL);
			modelAndView = new ModelAndView("temp", params);
		}
		return modelAndView;
	}

	@RequestMapping("do_register")
	public ModelAndView doRegister(SellerDTO sellerDTO, String redirect, @RequestParam("picture") MultipartFile picture,
			HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = null;
		// 需经人工审核
		sellerDTO.setStatus(TmallUserEnums.ALLOWED.getCode());
		if (StringUtils.isEmpty(redirect))
		{
			redirect = ProjectURLConxtant.STORE_INDEX_URL;
		}
		if (!storeService.checkIsTheStoreExit(sellerDTO.getStoreName()))
		{
			TmallUser tmallUser = sellerDTO;
			String error = tmallUserService.addNormalUser(tmallUser);
			if(error!=null)
			{
				modelAndView=new ModelAndView("redirect:/wechat/store/register?openid="+sellerDTO.getOpenid()+"&type=store");
				modelAndView.addObject("error",error);
				return modelAndView;
			}
			PictureDTO pictureDTO = FileUtils.generateFile(picture, PictureEnums.PICTURE_TYPE_STORE.getMsg());
			storeService.addStoreAndUpdateRedis(sellerDTO, pictureDTO.getPicturePath());
			modelAndView = new ModelAndView("temp");
			modelAndView.addObject("redirect","/wechat/store/login");
			modelAndView.addObject("error","注册成功,请登陆");
		} else
		{
			modelAndView=new ModelAndView("redirect:/wechat/store/register?openid="+sellerDTO.getOpenid()+"&type=store");
			modelAndView.addObject("error","店铺名字重复");
		}
		return modelAndView;
	}
}

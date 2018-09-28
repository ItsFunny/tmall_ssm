/**
 * 
 */
package com.tmall.store.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.StoreService;
import com.tmall.store.utils.StoreUtils;

/**
 * @author Administrator
 *
 */
@Controller
public class StoreWelcomeController
{
	private Logger logger = LoggerFactory.getLogger(StoreWelcomeController.class);
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private StoreService storeService;

	@RequestMapping("/welcome")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws TmallThirdPartException
	{
		ModelAndView modelAndView = null;
		SellerDTO sellerDTO = StoreUtils.findUserFromCookieAndRedis(CookieConstant.STORE_SELLER,
				RedisConstant.STORE_SELLER_PREFIX, request);
		List<StoreDTO> sellerStoreInfoList = storeService.findSellerStoreInfo(sellerDTO.getUserId());
		if (sellerStoreInfoList != null && sellerStoreInfoList.size() > 0)
		{
			
//			HashSet<Integer>storeIds=new HashSet<>();
//			for (StoreDTO storeDTO : sellerStoreInfoList)
//			{
//				storeIds.add(storeDTO.getStoreId());
//			}
//			Map<String, HashSet<Integer>>maps=(Map<String, HashSet<Integer>>) request.getServletContext().getAttribute(ServletContextConstant.STORE_SELLER_COLLECTION_OF_STORES);
//			if(maps==null)
//			{
//				maps=new HashMap<String, HashSet<Integer>>();
//				maps.put(sellerDTO.getUserId(), storeIds);
//				request.getServletContext().setAttribute(ServletContextConstant.STORE_SELLER_COLLECTION_OF_STORES, maps);
//			}else {
//				if(!maps.containsKey(sellerDTO.getUserId()))
//				{	
//					maps.put(sellerDTO.getUserId(), storeIds);
//				}
//			}
			String count = jedisClient
					.get(String.format(RedisConstant.PORTAL_STORE_TOTAL_COUNT, ":" + sellerDTO.getStoreId()));
			modelAndView = new ModelAndView("welcome");
			modelAndView.addObject("sellerDTO", sellerDTO);
			modelAndView.addObject("count", count);
			modelAndView.addObject("storeList", sellerStoreInfoList);
		} else
		{
			modelAndView = new ModelAndView("redirect:http://localhost:8095/wechat/store/register?openid="
					+ sellerDTO.getOpenid() + "&error=用户尚未在任一店铺中任职,请先注册店铺或者申请任职");
		}
		return modelAndView;
	}
	
}

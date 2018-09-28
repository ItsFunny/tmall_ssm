/**
 * 
 */
package com.tmall.store.aop;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallIllegalException;
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
@Aspect
@Component
public class SellerAuthorizeAOP
{
	private Logger logger = LoggerFactory.getLogger(SellerAuthorizeAOP.class);
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private StoreService storeService;

	@Pointcut("execution(* com.tmall.store.controller.StoreController.*(..))")
	public void verify()
	{
	}

	@Before("verify()")
	public void doVerify() throws TmallThirdPartException, TmallIllegalException
	{
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String uri = request.getRequestURI();
		String storeIdStr = uri.substring(7, 8);
		SellerDTO sellerDTO = StoreUtils.findUserFromCookieAndRedis(CookieConstant.STORE_SELLER,
				RedisConstant.STORE_SELLER_PREFIX, request);
		Integer storeId = Integer.parseInt(storeIdStr);
		Map<String, HashSet<Integer>> map = (HashMap<String, HashSet<Integer>>) request.getServletContext()
				.getAttribute(ServletContextConstant.STORE_SELLER_COLLECTION_OF_STORES);
		if (map == null)
		{
			map = new HashMap<>();
			List<StoreDTO> list = storeService.findSellerStoreInfo(sellerDTO.getUserId());
			map=set2ServletContext(sellerDTO, storeId, list, map);
			request.getServletContext().setAttribute(ServletContextConstant.STORE_SELLER_COLLECTION_OF_STORES, map);
		} else
		{
			HashSet<Integer> set = map.get(sellerDTO.getUserId());
			if (set != null && set.size() > 0)
			{
				boolean isContains = set.contains(storeId);
				if (!isContains)
				{
					logger.error("用户{}试图于{}违法访问{}店铺", sellerDTO.getNickName(), new Date(), storeId);
					throw new TmallIllegalException(ResultEnums.SELLER_DONT_HAVE_AUTH_OF_THE_STORE);
				}
			} else
			{
				List<StoreDTO> list = storeService.findSellerStoreInfo(sellerDTO.getUserId());
				set2ServletContext(sellerDTO, storeId, list, map);
			}
		}
	}

	public Map<String, HashSet<Integer>> set2ServletContext(SellerDTO sellerDTO, Integer storeId, List<StoreDTO> list, 
			Map<String, HashSet<Integer>> map) throws TmallIllegalException
	{
		HashSet<Integer> storeIds=new HashSet<>();
		if (list != null && list.size() > 0)
		{
			for (StoreDTO storeDTO : list)
			{
				storeIds.add(storeDTO.getStoreId());
			}
			map.put(sellerDTO.getUserId(), storeIds);
			boolean isContains = storeIds.contains(storeId);
			if (!isContains)
			{
				logger.error("用户{}试图于{}违法访问{}店铺", sellerDTO.getNickName(), new Date(), storeId);
				throw new TmallIllegalException(ResultEnums.SELLER_DONT_HAVE_AUTH_OF_THE_STORE);
			}
		} else
		{
			logger.error("用户{}试图于{}违法访问{}店铺", sellerDTO.getNickName(), new Date(), storeId);
			throw new TmallIllegalException(ResultEnums.SELLER_DONT_HAVE_AUTH_OF_THE_STORE);
		}
		return map;

	}
}

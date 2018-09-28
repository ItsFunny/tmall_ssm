/**
 * 
 */
package com.tmall.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.dto.SellerDTO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Administrator
 *
 */
public class ManagerUtils
{
	
	public static SellerDTO findSellerFromCookieAndRedis(HttpServletRequest request)
	{
		//这里是可以跟js联合,当多个seller登陆过之后将这些seller的信息都取出让用户选择登哪个用户
		List<String>uuidList=new ArrayList<String>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		{
			for (Cookie cookie : cookies)
			{
				if(cookie.getName().equals(CookieConstant.STORE_SELLER))
				{
					uuidList.add(cookie.getValue());
				}
			}
			JedisPool jedisPool=new JedisPool("127.0.0.1",6379);
			Jedis jedis=jedisPool.getResource();
			List<SellerDTO>sellerDTOList=new ArrayList<>();
			for(String key:uuidList)
			{
				String json = jedis.get(String.format(RedisConstant.SELLER_SESSIONID_PREFIX, key));
				if(!StringUtils.isEmpty(json))
				{
					Gson gson=new Gson();
					SellerDTO seller = gson.fromJson(json, SellerDTO.class);
					sellerDTOList.add(seller);
				}
			}
			//这里有大大滴问题
			if(sellerDTOList.size()>0&&sellerDTOList!=null)
			{
				return sellerDTOList.get(0);
			}
		}
		return null;

	}
}

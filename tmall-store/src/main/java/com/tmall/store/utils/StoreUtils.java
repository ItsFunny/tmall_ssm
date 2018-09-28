/**
 * 
 */
package com.tmall.store.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.library.utils.CommonUtils;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dto.SellerDTO;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Administrator
 *
 */
public class StoreUtils
{
	public static ByteArrayOutputStream createQRgen(String url)
	{
		// 如果有中文，可使用withCharset("UTF-8")方法

		// 设置二维码url链接，图片宽度500*500，JPG类型
		return QRCode.from(url).withSize(500, 500).to(ImageType.JPG).stream();
	}

	public static String getQRPath(String url, UUID sessionId)
	{
		// UUID randoUUID = UUID.randomUUID();
		ByteArrayOutputStream createQRgen = createQRgen(url);
		String fileName = sessionId + ".jpg";
		File dirFile = new File(ServiceEnums.PICTURE_QRCODE_LOCATION.getUrl());
		try
		{
			if (!dirFile.exists())
			{
				dirFile.mkdir();
			}
			File file = new File(dirFile.getAbsolutePath() + File.separator + fileName);
			if (!file.exists())
			{
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(new File(file.getAbsolutePath()));
			os.write(createQRgen.toByteArray());
			os.flush();
			os.close();
			return PictureEnums.PICTURE_SHOW_QRCODE.getMsg() + fileName;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将卖家信息写入redis和cookie 获取的话先通过cookie获取openid,再通过openid获取用户信息
	 * 
	 * @param name
	 *            sessionId会话id
	 * @param value
	 *            微信openid 同时也是redis的key
	 * @param redisValue
	 *            保存在redis中的信息,是用户信息
	 * @param response
	 */
	public static void writeSeller2CookieAndRedis(String name, String value, String redisValue,
			HttpServletResponse response)
	{
		Cookie cookie = new Cookie(CookieConstant.STORE_SELLER, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set(String.format(RedisConstant.STORE_SELLER_PREFIX, value), redisValue);
		jedis.expire(String.format(RedisConstant.STORE_SELLER_PREFIX, value), 60 * 60 * 24);
		jedis.close();
	}

	/**
	 * 写入cookie和redis
	 * 
	 * @param uuid
	 * @param redisValue
	 * @param response
	 */
	public static void write2CookieAndRedis(String uuid, String redisValue, HttpServletResponse response)
	{
		setCookie(response, String.format(CookieConstant.STORE_SELLER, uuid), uuid);
		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set(String.format(RedisConstant.SELLER_SESSIONID_PREFIX, uuid), redisValue);
		jedis.expire(String.format(RedisConstant.SELLER_SESSIONID_PREFIX, uuid), 60 * 60 * 24);
		jedis.close();
	}

	public static void setCookie(HttpServletResponse response, String name, String value)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
	}

	// public static SellerDTO findSellerFromCookieAndRedis(HttpServletRequest
	// request)
	// {
	// // 这里是可以跟js联合,当多个seller登陆过之后将这些seller的信息都取出让用户选择登哪个用户
	// List<String> uuidList = new ArrayList<String>();
	// Cookie[] cookies = request.getCookies();
	// if (cookies != null)
	// {
	// for (Cookie cookie : cookies)
	// {
	// if (cookie.getName().equals(CookieConstant.STORE_SELLER))
	// {
	// uuidList.add(cookie.getValue());
	// }
	// }
	// JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
	// Jedis jedis = jedisPool.getResource();
	// List<SellerDTO> sellerDTOList = new ArrayList<>();
	// for (String key : uuidList)
	// {
	// String json = jedis.get(String.format(RedisConstant.SELLER_INFO_PREFIX,
	// key));
	// if (!StringUtils.isEmpty(json))
	// {
	// Gson gson = new Gson();
	// SellerDTO seller = gson.fromJson(json, SellerDTO.class);
	// sellerDTOList.add(seller);
	// }
	// }
	// // 这里有大大滴问题
	// if (sellerDTOList.size() > 0 && sellerDTOList != null)
	// {
	// return sellerDTOList.get(0);
	// }
	// }
	// return null;
	// }
	public static SellerDTO findUserFromCookieAndRedis(String cookieKey, String redisKey, HttpServletRequest request)
			throws TmallThirdPartException
	{
		JedisPool jedisPool = new JedisPool("192.168.1.104", 6379);
		Jedis jedis = jedisPool.getResource();
		Cookie[] cookies = request.getCookies();
		try
		{
			if (cookies != null && cookies.length > 0)
			{
				SellerDTO user = new SellerDTO();
				for (Cookie cookie : cookies)
				{
					if (cookie.getName().equals(cookieKey))
					{
						String json = jedis.get(String.format(redisKey, cookie.getValue()));
						user = JsonUtils.jsonToPojo(json, SellerDTO.class);
						if (user == null)
						{
							throw new TmallThirdPartException(ResultEnums.USER_IS_NOT_LOGIN,
									CommonUtils.getRedirectUrlString(request));
						}
						return user;
					}
				}
			} else
			{
				throw new TmallThirdPartException(ResultEnums.USER_IS_NOT_LOGIN,
						CommonUtils.getRedirectUrlString(request));
			}
		} finally
		{
			jedis.close();
		}
		return null;
	}


}

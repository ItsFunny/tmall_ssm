/**
 * 
 */
package com.tmall.portal.utils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.joker.library.utils.CommonUtils;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.exception.TmallIllegalException;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.model.PortalUserConfig;
import com.tmall.model.User;
import com.tmall.vo.OrderDetailVO;
import com.tmall.vo.ProductInfoVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Administrator
 *
 */
public class PortalUtils
{

	private static final Logger logger = LoggerFactory.getLogger(PortalUtils.class);

	private static final JedisPool JEDIS_POOL = new JedisPool("192.168.0.115", 6379);

	public static JedisPool getJedisPool()
	{
		return JEDIS_POOL;
	}
	public static boolean limitWaitLoginUser(Integer limitUserCount) throws TmallPortalException
	{
		if(PortalUserConfig.WaitLoginUserQueue.size()<limitUserCount)
		{
			return true;
		}else {
			throw new TmallPortalException(ResultEnums.USER_WAIT_LOGIN_OUT_OF_RANGE);
		}
	}
//
//	public static boolean limitOnLineUser(Integer limitUserCount) throws TmallPortalException
//	{
//		Jedis jedis = PortalUtils.JEDIS_POOL.getResource();
//		try
//		{
//			String json = jedis.get(RedisConstant.PORTAL_ONLINE_USER);
//			if (!StringUtils.isEmpty(json))
//			{
//				// 确保当manager模块修改在线人员的时候,这里获得的都是最新的
//				PortalUserConfig.onLineCountMap = JsonUtils.jsonToPojo(json, Map.class);
//			}
//			// else
//			// {
//			// Map<String, UserDTO> map = new HashMap<>();
//			// String value = JsonUtils.objectToJson(map);
//			// jedis.set(RedisConstant.PORTAL_ONLINE_USER, value);
//			// }
//
//			Long onLineCount = (long) PortalUserConfig.onLineCountMap.size();
//			if (++onLineCount <= limitUserCount)
//			{
//				return true;
//			} else
//			{
//				logger.error("[校验用户上线]于{}在线用户过多,在线用户人数{}", ConverterUtils.date2SimpleDateString(new Date()),
//						jedis.get(RedisConstant.PORTAL_ONLINE_COUNT));
//				throw new TmallPortalException(ResultEnums.USER_ONLINE_OUT_OF_RANGE);
//			}
//		} finally
//		{
//			jedis.close();
//		}
//	}

	/**
	 * @param request
	 * @return
	 */
	public static ModelAndView isLogin(HttpServletRequest request)
	{
		ModelAndView modelAndView = null;
		UserDTO user = (UserDTO) request.getSession().getAttribute("user");
		if (user == null)
		{
			modelAndView = new ModelAndView("redirect:/login.html");
			modelAndView.addObject("error", "请先登陆");
			modelAndView.addObject("redirect", request.getParameter("redirect"));
			return modelAndView;
		}
		return modelAndView;
	}

	public static OrderDTO user2OrderDTO(User user)
	{
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerName(user.getUsername());
		orderDTO.setBuyerOpenid(user.getOpenid());
		orderDTO.setBuyerPhone(user.getPhoneNumber());
		return orderDTO;
	}

	public static OrderDetailVO productInfo2OrderDetail(ProductInfoVO productInfo)
	{
		OrderDetailVO orderDetail = new OrderDetailVO();
		BeanUtils.copyProperties(productInfo, orderDetail);
		if (StringUtils.isEmpty(productInfo.getProductPromotePrice()))
		{
			orderDetail.setProductPrice(new BigDecimal(productInfo.getProductPrice()));
		} else
		{
			orderDetail.setProductPrice(new BigDecimal(productInfo.getProductPromotePrice()));
		}
		orderDetail.setProductPitcureId(productInfo.getPictureId());
		return orderDetail;
	}

	public static LinkedList<String> string2LinkedList(String object)
	{

		LinkedList<String> linkedList = new LinkedList<>();
		String[] strings = object.split(",");
		for (String string : strings)
		{
			linkedList.add(string);
		}
		return linkedList;
	}

	// cookie操作

	/*
	 * 增
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static Cookie getCookie(HttpServletRequest request, String key)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(key))
				{
					return cookie;
				}
			}
		}
		return null;
	}

	/*
	 * 删 批量删除
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, List<String> deleteKey)
	{
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
		{
			// 这里可以用算法
			for (String key : deleteKey)
			{
				if (cookie.getName().equals(key))
				{
					cookie.setValue(null);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	/**
	 * 删除用户在redis和cookie中的信息
	 * 
	 * @param request
	 * @param response
	 * @author joker
	 * @date 创建时间：2018年1月15日 下午2:41:58
	 */
	public static void deleteUserInfo(HttpServletRequest request, HttpServletResponse response)
	{

		Jedis jedis = PortalUtils.JEDIS_POOL.getResource();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals(CookieConstant.TOKEN))
			{
				jedis.del(cookie.getValue());
				cookie.setValue(null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		jedis.close();
	}

	/*
	 * 改
	 */
	/*
	 * 查
	 */
	public static UserDTO findUserFromCookieAndRedis(String cookieKey,String redisKey,HttpServletRequest request) throws TmallThirdPartException
	{
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0)
		{
			UserDTO user = null;
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(cookieKey))
				{
					// JedisClient jedisClient = new JedisClient();

					Jedis jedis = PortalUtils.JEDIS_POOL.getResource();
					String json = jedis.get(String.format(redisKey, ":"+cookie.getValue()));
					jedis.close();
					// String json = jedisClient.get(String.format(RedisConstant.TOKEN_PREFIX,
					// cookie.getValue()));
					user = JsonUtils.jsonToPojo(json, UserDTO.class);
					if (user == null)
					{
						logger.error("[获取用户]用户未登陆");
						throw new TmallThirdPartException(ResultEnums.USER_IS_NOT_LOGIN, CommonUtils.getRedirectUrlString(request));
					}
					return user;
				}
			}
			logger.error("[获取用户]用户未登陆");
			throw new TmallThirdPartException(ResultEnums.USER_IS_NOT_LOGIN, CommonUtils.getRedirectUrlString(request));
		}else {
			logger.error("[获取用户]用户未登陆");
			throw new TmallThirdPartException(ResultEnums.USER_IS_NOT_LOGIN, CommonUtils.getRedirectUrlString(request));
		}
	}

	/**
	 * 获取用户信息,并且查询是否为本人操作,若涉及openid
	 * 
	 * @param request
	 * @param openid
	 * @throws TmallPortalException
	 */
//	public static void findLoginUseAndcheckrWithOpenid(HttpServletRequest request, String openid) throws Exception
//	{
//		UserDTO userDTO = findUserFromCookieAndRedis(request);
//		if (!userDTO.getOpenid().equals(openid))
//		{
//			logger.error("[匹配用户是否是本人]用户{}于{}试图处理用户openid为{}的相关信息", userDTO.getNickName(), openid, new Date());
//			throw new TmallIllegalException(ResultEnums.USER_ILLEGAL_OPERATION);
//		}
//	}

	public static String getIpAddr(HttpServletRequest request)
	{
		String ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("x-request-ip");
		}
		if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0.0.0.0.0.0.0.1"))
			{
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try
				{
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15)
		{
			if (ipAddress.indexOf(",") > 0)
			{
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

}

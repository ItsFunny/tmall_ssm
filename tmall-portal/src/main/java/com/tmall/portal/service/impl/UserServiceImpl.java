/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.utils.KeyUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.UserDao;
import com.tmall.dto.OrderDTO;
import com.tmall.dto.UserDTO;
import com.tmall.form.FormUser;
import com.tmall.model.User;
import com.tmall.portal.dao.impl.JedisClient;
import com.tmall.portal.service.UserService;
import com.tmall.portal.utils.PortalUtils;

/**
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService
{
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${THIRD_PART_URL}")
	private String THIRD_PART_URL;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public UserDTO findUserByUserId(String userId)
	{
		String key = RedisEnums.USER.getKey() + ":" + userId;
		String json = null;
		UserDTO user = new UserDTO();
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			user = JsonUtils.jsonToPojo(json, UserDTO.class);
			return user;
		}
		user = userDao.findUserByUserId(userId);
		try
		{
			String value = JsonUtils.objectToJson(user);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}

		return user;
	}

	@Override
	public List<UserDTO> findUsersByUserIds(List<String> userIds)
	{
		List<UserDTO> users = userDao.findUsersInUserIDs(userIds);
		return users;
	}

	@Override
	public List<UserDTO> findUsersByNicknames(List<String> nicknames)
	{
		List<UserDTO> users = userDao.findUsersInNicknames(nicknames);
		return users;
	}

	@Override
	public UserDTO findUserByUserNickname(String nickname)
	{
		UserDTO userDTO = userDao.findUserByNickname(nickname);
		if (userDTO == null)
		{
			throw new TmallException(ResultEnums.USER_NOT_EXIT);
		}
		return userDTO;
	}

	@Override
	public UserDTO findUser(String key)
	{
//		String url = REST_BASE_URL + "/user/check/" + key;
		String url=THIRD_PART_URL+"/login/"+key;
		UserDTO userDTO = null;
		try
		{
			userDTO = restTemplate.postForObject(url, null, UserDTO.class);
			return userDTO;
		} catch (Exception e)
		{
		}
		return userDTO;
	}

	@Override
	public UserDTO findUserByPhoneNum(String phoneNum)
	{
		UserDTO userDTO = userDao.findUserByPhone(phoneNum);
		if (userDTO == null)
		{
			throw new TmallException(ResultEnums.USER_NOT_EXIT);
		}
		return userDTO;
	}
	@Override
	public boolean checkLoginUser(UserDTO userDTO, String password, HttpServletRequest request, HttpServletResponse response)
	{
//		String url = REST_BASE_URL + "/user/check";
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		// Map<String, Object>params=new HashMap<>();
//		params.add("key", key);
//		params.add("password", password);
//		ResultVo<UserDTO> resultVo = new ResultVo<>();
//		try
//		{
//			resultVo = restTemplate.postForObject(url, params, ResultVo.class);
//		} catch (Exception e)
//		{
//			// 自己查
//		}
//		if (resultVo.getCode() == 200)
//		{
//			HttpSession session = request.getSession();
//			Gson gson = new Gson();
//			UserDTO userDTO = gson.fromJson(gson.toJson(resultVo.getData()), UserDTO.class);
//			session.setAttribute(ServletContextConstant.USER_IN_SESSION, userDTO);
//			String value = JsonUtils.objectToJson(userDTO);
//			// 写入cookie和redis,,这里感觉设置的有点问题
//			String token = UUID.randomUUID().toString();
//			Integer expire = RedisConstant.EXPIRE;
//			PortalUtils.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
//			jedisClient.set(String.format(RedisConstant.TOKEN_PREFIX, token), value);
//			jedisClient.expire(String.format(RedisConstant.TOKEN_PREFIX, token), expire);
//			return true;
//		} else
//		{
//			return false;
//		}
		
		if(userDTO!=null)
		{
			if(userDTO.getPassword().equals(password))
			{
//				HttpSession session = request.getSession();
//				session.setAttribute(ServletContextConstant.USER_IN_SESSION, userDTO);
//				// 写入cookie和redis,,这里感觉设置的有点问题
//				String value = JsonUtils.objectToJson(userDTO);
//				String token = UUID.randomUUID().toString();
//				Integer expire = RedisConstant.EXPIRE;
//				PortalUtils.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
//				jedisClient.set(String.format(RedisConstant.TOKEN_PREFIX, token), value);
//				jedisClient.expire(String.format(RedisConstant.TOKEN_PREFIX, token), expire);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

	}

	@Override
	public ResultVo<String> checkRegisterUser(String phoneNum, String nickname)
	{
		ResultVo<String> resultVo = new ResultVo<>(200, "此用户能够注册");
		try
		{
			findUserByPhoneNum(phoneNum);
			resultVo = new ResultVo<>(500, "该手机已被注册,请更换手机号");
			return resultVo;
		} catch (Exception e)
		{
			// 如果服务器没问题,抛出异常说明用户不存在,可以注册
		}
		try
		{
			findUserByUserNickname(nickname);
			resultVo = new ResultVo<>(500, "该用户名已经存在,请重新输入");
			return resultVo;
		} catch (Exception e)
		{
			// 如果服务器没问题,抛出异常说明用户不存在,可以注册
		}
		return resultVo;
	}

	@Override
	public ResultVo<String> addUser(FormUser formUser)
	{
		User user = new User();
		BeanUtils.copyProperties(formUser, user);
		user.setUserId(KeyUtils.generateUniqueKey());
		user.setOpenid(formUser.getOpenid());
		String url = REST_BASE_URL + "/user/add";
		ResultVo<String> resultVo = checkRegisterUser(user.getPhoneNumber(), user.getNickname());
		if (resultVo.getCode() != 200)
		{
			// 说明提交的信息不合格,直接返回注册信息
			return resultVo;
		}
		try
		{
			restTemplate.postForObject(url, user, ResultVo.class);
		} catch (Exception e)
		{
			logger.error("[用户注册]rest系统UserController扑街了");
		}
		return new ResultVo<>(200, "注册成功,请登陆");
	}

	/*
	 * 110:这里的查询服务最好是自己查询而不是去调用restapi接口
	 */
	@Override
	public List<OrderDTO> findUserOrders(String type, String openid)
	{
		String url = REST_BASE_URL + "/user/orders";
		ResultVo<List<OrderDTO>> resultVo = null;
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("type", type);
		params.add("openid", openid);
		List<OrderDTO> orderDTOs = null;
		try
		{
			resultVo = restTemplate.postForObject(url, params, ResultVo.class);
		} catch (Exception e)
		{
			logger.error("[获取用户订单信息]rest服务器挂了");
			e.printStackTrace();
		}
		orderDTOs = resultVo.getData();
		return orderDTOs;
	}

}

/**
 * 
 */
package com.tmall.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.ProjectURLConxtant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.constant.ServletContextConstant;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.TmallUserEnums;
import com.tmall.common.exception.TmallIllegalException;
import com.tmall.common.exception.TmallPortalException;
import com.tmall.common.exception.TmallThirdPartException;
import com.tmall.common.utils.CommonUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.StoreDTO;
import com.tmall.dto.UserDTO;
import com.tmall.form.FormUser;
import com.tmall.model.PortalUserConfig;
import com.tmall.model.TmallUser;
import com.tmall.portal.dao.impl.JedisClient;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.portal.service.StoreService;
import com.tmall.portal.service.UserService;
import com.tmall.portal.utils.PortalUtils;
import com.tmall.vo.ProductInfoAndCategoryVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 展示首页
 * 
 * @author Administrator
 */
@Controller
public class IndexController
{
	@Autowired
	private ProductInfosService productInfosService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JedisClient jedisClient;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
			throws IOException, TmallThirdPartException
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> params = new HashMap<String, Object>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(CookieConstant.PORTAL_USER))
				{
					String jsonString = jedisClient.get(String.format(RedisConstant.PORTAL_USER_PREFIX,cookie.getValue()));
					if(!StringUtils.isEmpty(jsonString))
					{
						UserDTO userDTO = JsonUtils.jsonToPojo(jsonString, UserDTO.class);
						if(userDTO!=null)
						{
							params.put("userDTO", userDTO);
						}
					}
				}
			}
		}
		String totalCount = jedisClient.get(RedisConstant.PORTAL_ONLINE_COUNT);
		String maxRecord = jedisClient.get(RedisConstant.PORTAL_MAX_ONLINE_RECORD);
		String dateOfMaxCount = null;
		String maxCount = null;
		Map<String, Object> map = new HashMap<>();

		if (!StringUtils.isEmpty(maxRecord))
		{
			map = JsonUtils.jsonToPojo(maxRecord, Map.class);
			dateOfMaxCount = (String) map.get(RedisConstant.PORTAL_DATE_OF_MAX_COUNT);
			maxCount = (String) map.get(RedisConstant.PORTAL_MAX_COUNT);
		}
		ResultVo<List<ProductInfoAndCategoryVO>> resultVO = new ResultVo<>();
		resultVO = productInfosService.showAllProducts();
		List<StoreDTO> storeDTOList = storeService.findAllStores();
		params.put("resultVO", resultVO);
		params.put("storeDTOList", storeDTOList);
		params.put("totalCount", totalCount);
		params.put("dateOfMaxCount", dateOfMaxCount);
		params.put("maxCount", maxCount);
		ModelAndView modelAndView = new ModelAndView("index", params);
		return modelAndView;
	}


	@RequestMapping("/temp")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException
	{
		String redircet = request.getParameter("redirect") == null ? "index.html" : request.getParameter("redirect");
		String error = new String(request.getParameter("error").getBytes("iso-8859-1"), "utf-8");
		ModelAndView modelAndView = new ModelAndView("temp");
		modelAndView.addObject("error", error);
		modelAndView.addObject("redirect", redircet);
		return modelAndView;
	}

}

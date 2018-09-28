/**
 * 
 */
package com.tmall.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.ProductInfoDao;
import com.tmall.search.service.ProductService;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

import net.sf.json.JSONArray;


/**
 * @author Administrator
 *
 */
@Service
public class ProductServiceImpl implements ProductService
{

	@Autowired
	private RestTemplate restTemplate;
	@Override
	public List<ProductInfoAndCategoryVO> findAll()
	{
		String url=ServiceEnums.REST_BASE_URL.getUrl()+"/product/show/all";
		List<ProductInfoAndCategoryVO>productInfoAndCategoryVOList=new ArrayList<ProductInfoAndCategoryVO>();
		try
		{
			@SuppressWarnings("unchecked")
			ResultVo<List<ProductInfoAndCategoryVO>> resultVo=restTemplate.getForObject(url, ResultVo.class);
			productInfoAndCategoryVOList=resultVo.getData();
//			Gson gson=new Gson();
//			String json = gson.toJson(resultVo.getData());
//			productInfoAndCategoryVOList=JsonUtils.jsonToList(json, ProductInfoAndCategoryVO.class);
//			productInfoVOList=resultVo.getData();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return productInfoAndCategoryVOList;
	}

}

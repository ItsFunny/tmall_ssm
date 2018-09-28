/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.vo.ResultVo;
import com.tmall.dao.PictureDao;
import com.tmall.model.Picture;
import com.tmall.portal.service.PictureService;

/**
 * @author Administrator
 *
 */
@Service
public class PictureServiceImpl implements PictureService
{
	private Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);
	@Autowired
	private PictureDao pictureDao;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResultVo<Picture> findProductDetailPicturesByPorductId(String productId)
	{
		String url = REST_BASE_URL + "/picture/product/detail/" + productId;
		ResultVo<Picture> resultVo = new ResultVo<>();
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return resultVo;
	}

	@Override
	public ResultVo<Picture> findProdutShowPicturesByProductId(String productId)
	{
		String url = REST_BASE_URL + "/picture/product/show/{productId}";
		ResultVo<Picture> resultVo = new ResultVo<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class, params);
		} catch (Exception e)
		{
		}
		return resultVo;
	}

	@Override
	public List<Picture> findPicturesByPictureIdList(List<String> orderDetailPictureIdList)
	{
		List<Picture> pictureIdList = pictureDao.findPicturesByPictureIdList(orderDetailPictureIdList);
		return pictureIdList;
	}

}

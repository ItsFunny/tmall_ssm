/**
 * 
 */
package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.PictureDao;
import com.tmall.model.Picture;
import com.tmall.service.PictureService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.PictureVO;

/**
 * @author Administrator
 *
 */
@Service
public class PictureServiceImpl implements PictureService
{
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PictureDao pictureDao;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public PictureVO findProductAllPictures(String productId)
	{
		String url = ServiceEnums.REST_BASE_URL.getUrl() + "/picture/product/all/pictures/{productId}";
		ResultVo<PictureVO> resultVo = null;
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class, productId);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		PictureVO pictureVO = gson.fromJson(gson.toJson(resultVo.getData()), PictureVO.class);
		return pictureVO;
	}

	@Override
	public void deleteProductOnePictureByPictureId(String pictureId)
	{
		pictureDao.deleteProductPictureByPictureId(pictureId);
	}

	@Override
	public void deletePictureAndUpdateRedis(String pictureId, String productId)
	{
		String key = RedisEnums.PRODUCT_ALLPICTURES.getKey() + ":" + productId;
		//先更新数据库,再重新设置缓存
		deleteProductOnePictureByPictureId(pictureId);
		
		try
		{
			jedisClient.expire(key, 0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		//先更新数据库,并且使得缓存失效,迫使他去数据库中查最新的,并且重新设置
		PictureVO pictureVO = findProductAllPictures(productId);
		try
		{
			String value = JsonUtils.objectToJson(pictureVO);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
	}

	@Override
	public void addProductPicture(String pictureId,String productId, String picturePath, String type)
	{
		Integer pictureType=null;
		if(type.equals(PictureEnums.DETAIL_PICTURE.getMsg()))
		{
			pictureType=PictureEnums.DETAIL_PICTURE.getCode();
		}
		else if(type.equals(PictureEnums.SHOW_PICTURE.getMsg()))
		{
			pictureType=PictureEnums.SHOW_PICTURE.getCode();
		}else if(type.equals(PictureEnums.INDEX_PICTURE.getMsg()))
		{
			pictureType=PictureEnums.INDEX_PICTURE.getCode();
		}
		Picture picture=new Picture();
		picture.setPictureId(pictureId);
		picture.setProductId(productId);
		picture.setPicturePath(picturePath);
		picture.setPictureType(pictureType);
		pictureDao.addOnePicture(picture);
		//redis更新
//		PictureVO pictureVO = findProductAllPictures(productId);
//		String key=RedisEnums.PRODUCT_ALLPICTURES.getKey()+":"+productId;
//		try
//		{
//			jedisClient.expire(key, 0);
//			//这里可能会有bug
//			String value=JsonUtils.objectToJson(pictureVO);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//		}
	}

	@Override
	public List<Picture> findOrderDetalPicture(List<String> orderDetailPictureIdList)
	{
		return pictureDao.findPicturesByPictureIdList(orderDetailPictureIdList);
	}

}

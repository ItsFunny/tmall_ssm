/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.PictureDao;
import com.tmall.model.Picture;
import com.tmall.rest.dao.impl.JedisClient;
import com.tmall.rest.service.PictureService;
import com.tmall.vo.PictureVO;

/**
 * @author Administrator
 *
 */
@Service
public class PictureServiceImpl implements PictureService
{
	private Logger logger=LoggerFactory.getLogger(PictureServiceImpl.class);
	@Autowired
	private PictureDao pictureDao;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<Picture> findProductPicturesInProductIds(List<Integer> productIds)
	{
		return null;
	}

	@Override
	public ResultVo<List<Picture>> findProductDetailPicturesByProductId(String productId)
	{
//		String key = RedisEnums.PRODUCT_DETAILPICTURES.getKey() + ":" + productId;
//		String json = null;
		List<Picture> pictures = new ArrayList<Picture>();
//		try
//		{
//			json = jedisClient.get(key);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			pictures = JsonUtils.jsonToList(json, Picture.class);
//			return new ResultVo<List<Picture>>(200, "sucess", pictures);
//		}
		pictures = pictureDao.findProductPictureByType(PictureEnums.DETAIL_PICTURE.getCode(), productId);
//		try
//		{
//			String value = JsonUtils.List2Json(pictures);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		return new ResultVo<List<Picture>>(200, "sucess", pictures);
	}

	@Override
	public List<Picture> findProductShowPicturesByProductId(String productId)
	{
//		String key = RedisEnums.PRODUCT_SHOWPICTURES.getKey() + ":" + productId;
		List<Picture> pictures = new ArrayList<>();
//		String json = null;
//		try
//		{
//			json = jedisClient.get(key);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		if (!StringUtils.isEmpty(json))
//		{
//			pictures = JsonUtils.jsonToList(json, Picture.class);
//			return pictures;
//		}
		pictures = pictureDao.findProductPictureByType(PictureEnums.SHOW_PICTURE.getCode(), productId);
//
//		try
//		{
//			String value = JsonUtils.List2Json(pictures);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		return pictures;
	}

	@Override
	public PictureVO findAllPictures(String productId)
	{
		/*
		 * 这里感觉其实并不需要将缓存进行再封装
		 */
//		String key=RedisEnums.PRODUCT_ALLPICTURES.getKey()+":"+productId;
		PictureVO pictureVO = new PictureVO();
//		String json=null;
//		try
//		{
//			json=jedisClient.get(key);
//		} catch (Exception e)
//		{
//		}
//		if(!StringUtils.isEmpty(json))
//		{
//			pictureVO=JsonUtils.jsonToPojo(json, pictureVO.getClass());
//			return pictureVO;
//		}
		ResultVo<List<Picture>> resultVODetailPictures = findProductDetailPicturesByProductId(productId);
		List<Picture> resultVOShowPictures = findProductShowPicturesByProductId(productId);
		List<Picture> topPictures = pictureDao.findProductPictureByType(PictureEnums.TOP_PICTURE.getCode(), productId);
		List<Picture> indexPictrure=pictureDao.findProductPictureByType(PictureEnums.INDEX_PICTURE.getCode(), productId);
		pictureVO.setProductId(productId);
		pictureVO.setDetailPictures(resultVODetailPictures.getData());
		pictureVO.setShowPictures(resultVOShowPictures);
		pictureVO.setTopPicture(topPictures);
		if (indexPictrure.size()!=0)
		{
			pictureVO.setIndexPicture(indexPictrure.get(0));
		}
		return pictureVO;
	}

	@Override
	public List<PictureVO> findProductsAllPictures(LinkedList<String> productIds)
	{
//		String key=RedisEnums.PRODUCT_ALLPICTURES.getKey();
//		String json=null;所有图片也不适合存放在redis中
		List<PictureVO> pictureVOList = new ArrayList<>();
//		try
//		{
//			json=jedisClient.get(key);
//			jedisClient.expire(key, 60*60*24*10);
//		} catch (Exception e)
//		{
//			logger.error("[查询类目下商品的所有图片]写入redis出错{}",e.getMessage());
//		}
//		if(!StringUtils.isEmpty(json))
//		{
//			JSONArray jsonArray=JSONArray.fromObject(json);
//			pictureVOList=JSONArray.toList(jsonArray,PictureVO.class);
////			pictureVOList=JsonUtils.jsonToList(json, PictureVO.class);
//			return pictureVOList;
//		}
//		if(!productIds.isEmpty())
//		{
			
			/*
			 * 这里好像能用上面的方法拼接,但是又感觉不好,因为如果采用上面的方法,就得是多个for循环查询redis
			 * 	for(String id:productIds)
				{
					PictureVO pictureVO = findAllPictures(id);
					pictureVOList.add(pictureVO);
				}
				虽然代码量能少很多,但是总感觉不行
			 */
			List<Picture> pictures = pictureDao.findProductsAllPictures(productIds);
			for (String productId : productIds)
			{
				PictureVO pictureVO = new PictureVO();
				pictureVO.setProductId(productId);
				if(pictures.size()!=0)
				{
					List<Picture> detailPictures = new ArrayList<>();
					List<Picture> showPictures = new ArrayList<>();
					List<Picture> topPictures = new ArrayList<>();
					for (Picture picture : pictures)
					{
						if (picture.getProductId().equals(productId))
						{
							if (picture.getPictureType().equals(PictureEnums.DETAIL_PICTURE.getCode()))
							{
								detailPictures.add(picture);
							} else if (picture.getPictureType().equals(PictureEnums.SHOW_PICTURE.getCode()))
							{
								showPictures.add(picture);
							} else if (picture.getPictureType().equals(PictureEnums.TOP_PICTURE.getCode()))
							{
								topPictures.add(picture);
							}
						}
					}
					pictureVO.setDetailPictures(detailPictures);
					pictureVO.setShowPictures(showPictures);
					pictureVO.setTopPicture(topPictures);
				}
				pictureVOList.add(pictureVO);
			}
//		}
//		try
//		{
//			String value=JsonUtils.List2Json(pictureVOList);
//			jedisClient.set(key, value);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		return pictureVOList;
	}
	@Override
	public List<Picture> findPicturesByPictureIdList(List<String> pictureIds)
	{
		List<Picture> pictureIdList = pictureDao.findPicturesByPictureIdList(pictureIds);
		return pictureIdList;
	}
}

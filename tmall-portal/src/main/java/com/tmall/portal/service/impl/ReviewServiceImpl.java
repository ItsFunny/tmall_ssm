/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.enums.RedisEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.ReviewDao;
import com.tmall.model.Review;
import com.tmall.portal.dao.impl.JedisClient;
import com.tmall.portal.service.ReviewService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service
public class ReviewServiceImpl implements ReviewService
{
	private Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	@Autowired
	private ReviewDao reviewDao;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<Review> findAllReviewsByProductId(String productId)
	{
		String key = RedisEnums.PRODUCTREVIEWS.getKey() + ":" + productId;
		String json = null;
		List<Review> reviews = new ArrayList<Review>();
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
			logger.error("[获取商品评论]redis挂了");
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(json))
		{
			JSONArray jsonArray=JSONArray.fromObject(json);
			reviews=JSONArray.toList(jsonArray,Review.class);
//			reviews = JsonUtils.jsonToList(json, Review.class);
			return reviews;
		}
		reviews = reviewDao.findAllReviewsByProductId(productId);
		try
		{
			String value = JsonUtils.List2Json(reviews);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return reviews;
	}

	@Override
	public Integer getTotalReviewCount(String productId)
	{
		return reviewDao.getTotalReviewCount(productId);
	}

	@Override
	public boolean addReview(String productId, String content, String userId, String nickname)
	{
		try
		{
			reviewDao.addReviewOfProduct(productId, content, userId, nickname);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

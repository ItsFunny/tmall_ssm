package com.tmall.portal.service;

import java.util.List;

import com.tmall.model.Review;

public interface ReviewService
{
	/*
	 * 获取某商品的所有评论
	 */
	List<Review> findAllReviewsByProductId(String productId);
	
	Integer getTotalReviewCount(String productId);
	
	boolean addReview(String productId,String content,String userId,String nickname);
	
	
}

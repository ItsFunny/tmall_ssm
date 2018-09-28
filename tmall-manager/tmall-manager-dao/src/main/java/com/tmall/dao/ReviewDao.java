/**
 * 
 */
package com.tmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tmall.model.Review;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ReviewDao
{
	
	/*
	 * 增
	 * 增加用户对于某商品的评论
	 */
	@Insert("insert into review  (content,user_id,nickname,product_id) values (#{content},#{userId},#{nickname},#{productId})")
	void addReviewOfProduct(@Param("productId")String productId,@Param("content")String content,@Param("userId")String userId,@Param("nickname")String nickname);
	/*
	 * 
	 * 用户id
	 * 查询用户的所有评论
	 */
	
	/*
	 * 查询
	 * 某产品的所有评论
	 * 某产品所有评论的数目 productId
	 */
	@Select("select * from review where product_id=#{productId}")
	List<Review> findAllReviewsByProductId(String productId);
	@Select("select count(review_id) from review where product_id=#{productId}")
	Integer getTotalReviewCount(String productId);
	
	
	
	

}

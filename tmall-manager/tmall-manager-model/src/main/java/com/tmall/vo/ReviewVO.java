/**
 * 
 */
package com.tmall.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class ReviewVO
{
	private Integer reviewId;

	/*
	 * 评论
	 */
	private String content;

	private String productId;
	private String createDate;
	private String updateDate;

	/*
	 * 用户的昵称
	 */
	private String nickname;

	/*
	 * 评论的数目
	 */

	public Integer getReviewId()
	{
		return reviewId;
	}

	public void setReviewId(Integer reviewId)
	{
		this.reviewId = reviewId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	public String getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(String updateDate)
	{
		this.updateDate = updateDate;
	}

}

package com.tmall.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Review
{
	private Integer reviewId;

	private String content;

	private String userId;
	private String nickname;

	private String productId;

	private Date createDate;
	private Date updateDate;

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
		this.content = content == null ? null : content.trim();
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId == null ? null : productId.trim();
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
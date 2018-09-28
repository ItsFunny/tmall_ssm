package com.tmall.model;

public class Picture
{
	private String pictureId;

	private String productId;

	private String picturePath;

	private Integer pictureType;

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId == null ? null : productId.trim();
	}

	public String getPicturePath()
	{
		return picturePath;
	}

	public void setPicturePath(String picturePath)
	{
		this.picturePath = picturePath == null ? null : picturePath.trim();
	}

	public Integer getPictureType()
	{
		return pictureType;
	}

	public void setPictureType(Integer pictureType)
	{
		this.pictureType = pictureType;
	}

	public String getPictureId()
	{
		return pictureId;
	}

	public void setPictureId(String pictureId)
	{
		this.pictureId = pictureId;
	}

}
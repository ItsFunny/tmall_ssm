/**
 * 
 */
package com.tmall.model;

/**
 * @author Administrator
 *
 */
public class CategoryPicture
{
	private Integer categoryPictureId;
	private String categoryPicturePath;
	private Integer categoryType;

	public Integer getCategoryPictureId()
	{
		return categoryPictureId;
	}

	public void setCategoryPictureId(Integer categoryPictureId)
	{
		this.categoryPictureId = categoryPictureId;
	}

	public String getCategoryPicturePath()
	{
		return categoryPicturePath;
	}

	public void setCategoryPicturePath(String categoryPicturePath)
	{
		this.categoryPicturePath = categoryPicturePath;
	}

	public Integer getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(Integer categoryType)
	{
		this.categoryType = categoryType;
	}

}

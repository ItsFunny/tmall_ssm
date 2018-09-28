/**
 * 
 */
package com.tmall.vo;

/**
 * @author Administrator
 *
 */
public class ProductCategoryVO
{
	private String categoryName;
	private Integer categoryType;
	private Integer categoryPictureId;
	private String categoryPicturePath;
	private Integer storeId;
	


	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public Integer getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(Integer categoryType)
	{
		this.categoryType = categoryType;
	}


	public String getCategoryPicturePath()
	{
		return categoryPicturePath;
	}

	public void setCategoryPicturePath(String categoryPicturePath)
	{
		this.categoryPicturePath = categoryPicturePath;
	}

	public Integer getCategoryPictureId()
	{
		return categoryPictureId;
	}

	public void setCategoryPictureId(Integer categoryPictureId)
	{
		this.categoryPictureId = categoryPictureId;
	}

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}
}

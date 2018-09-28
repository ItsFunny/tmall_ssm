/**
 * 
 */
package com.tmall.vo;

import java.io.Serializable;
import java.util.List;


/**
 * 前端商品分类展示
 * 
 * @author Administrator
 *
 */
public class ProductInfoAndCategoryVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3459599194136802735L;

	private String categoryName;

	private Integer categoryType;
	
	private Integer categoryPictureId;
	
	private String categoryPicturePath;
	
	private Integer storeId;

	private List<ProductInfoVO> productInfoList;

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

	public List<ProductInfoVO> getProductInfoList()
	{
		return productInfoList;
	}

	public void setProductInfoList(List<ProductInfoVO> productInfoList)
	{
		this.productInfoList = productInfoList;
	}

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

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}

}

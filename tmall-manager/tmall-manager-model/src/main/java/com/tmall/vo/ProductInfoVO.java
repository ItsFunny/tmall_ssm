/**
 * 
 */
package com.tmall.vo;

/**
 * @author Administrator
 *
 */
public class ProductInfoVO
{
	private String productId;

	private String productName;

	private String productSubTitle;

	private Double productPrice;

	private Double productPromotePrice;

	private String productDescription;

	private String pictureId;

	private String productPicturePath;

	private Integer productStatus;

	private Integer categoryType;

	private String categoryName;

	private Integer productTotalSelled;

	private Integer productStock;

	private Integer productReviewsCount;

	private Integer productIntegral;

	private Integer productQuantity;

	private PictureVO productPictures;
	
	private Integer storeId;
	
	private String storeName;
	

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProductSubTitle()
	{
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle)
	{
		this.productSubTitle = productSubTitle;
	}

	public Double getProductPrice()
	{
		return productPrice;
	}

	public void setProductPrice(Double productPrice)
	{
		this.productPrice = productPrice;
	}

	public Double getProductPromotePrice()
	{
		return productPromotePrice;
	}

	public void setProductPromotePrice(Double productPromotePrice)
	{
		this.productPromotePrice = productPromotePrice;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription;
	}

	public Integer getProductStatus()
	{
		return productStatus;
	}

	public void setProductStatus(Integer productStatus)
	{
		this.productStatus = productStatus;
	}

	public Integer getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(Integer categoryType)
	{
		this.categoryType = categoryType;
	}

	@Override
	public String toString()
	{
		return "ProductInfoVO [productId=" + productId + ", productName=" + productName + ", productSubTitle="
				+ productSubTitle + ", productPrice=" + productPrice + ", productPromotePrice=" + productPromotePrice
				+ ", productDescription=" + productDescription + ", pictureId=" + pictureId + ", productStatus="
				+ productStatus + ", categoryType=" + categoryType + "]";
	}

	public Integer getProductTotalSelled()
	{
		return productTotalSelled;
	}

	public void setProductTotalSelled(Integer productTotalSelled)
	{
		this.productTotalSelled = productTotalSelled;
	}

	public Integer getProductStock()
	{
		return productStock;
	}

	public void setProductStock(Integer productStock)
	{
		this.productStock = productStock;
	}

	public Integer getProductReviewsCount()
	{
		return productReviewsCount;
	}

	public void setProductReviewsCount(Integer productReviewsCount)
	{
		this.productReviewsCount = productReviewsCount;
	}

	public Integer getProductIntegral()
	{
		return productIntegral;
	}

	public void setProductIntegral(Integer productIntegral)
	{
		this.productIntegral = productIntegral;
	}

	public Integer getProductQuantity()
	{
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity)
	{
		this.productQuantity = productQuantity;
	}

	public PictureVO getProductPictures()
	{
		return productPictures;
	}

	public void setProductPictures(PictureVO productPictures)
	{
		this.productPictures = productPictures;
	}

	public String getProductPicturePath()
	{
		return productPicturePath;
	}

	public void setProductPicturePath(String productPicturePath)
	{
		this.productPicturePath = productPicturePath;
	}

	public String getPictureId()
	{
		return pictureId;
	}

	public void setPictureId(String pictureId)
	{
		this.pictureId = pictureId;
	}


	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public String getStoreName()
	{
		return storeName;
	}

	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}

}

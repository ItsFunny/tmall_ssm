package com.tmall.model;

public class ProductInfo
{
	private String productId;

	private String productName;

	private String productSubTitle;

	private Double productPrice;

	private Double productPromotePrice;

	private Integer productStock;

	private Integer productTotalSelled;

	private String productDescription;

	private String pictureId;

	private Integer productStatus;

	private Integer categoryType;
	
	private Integer productReviewsCount;
	
	private Integer productIntegral;
	
	private Integer storeId;
	
	

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductSubTitle()
	{
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle)
	{
		this.productSubTitle = productSubTitle == null ? null : productSubTitle.trim();
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

	public Integer getProductStock()
	{
		return productStock;
	}

	public void setProductStock(Integer productStock)
	{
		this.productStock = productStock;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription == null ? null : productDescription.trim();
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
		return "ProductInfo [productId=" + productId + ", productName=" + productName + ", productSubTitle="
				+ productSubTitle + ", productPrice=" + productPrice + ", productPromotePrice=" + productPromotePrice
				+ ", productStock=" + productStock + ", productDescription=" + productDescription + ", pictureId="
				+ pictureId + ", productStatus=" + productStatus + ", categoryType=" + categoryType + "]";
	}

	public Integer getProductTotalSelled()
	{
		return productTotalSelled;
	}

	public void setProductTotalSelled(Integer productTotalSelled)
	{
		this.productTotalSelled = productTotalSelled;
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


}
package com.tmall.model;

import java.math.BigDecimal;
import java.util.Date;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;

public class OrderDetail {
    private String orderDetailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private Integer productPitcureId;
    
    private String content;
    
    private Integer orderStatus=OrderStatusEnum.WAIT.getCode();
    
    private Integer payStatus=PayStatusEnum.WAIT_PAY_MONEY.getCode();
    
    private Date createDate;
    private Date updateDate;


    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId == null ? null : orderDetailId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
	public Integer getProductPitcureId()
	{
		return productPitcureId;
	}

	public void setProductPitcureId(Integer productPitcureId)
	{
		this.productPitcureId = productPitcureId;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Integer getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
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


}
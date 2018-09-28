package com.tmall.model;

import java.math.BigDecimal;
import java.util.Date;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;

public class OrderMaster
{
	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String buyerAddress;

	private String buyerPost;

	private String buyerOpenid;

	private BigDecimal orderAmount;

	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	private Integer payStatus=PayStatusEnum.WAIT_PAY_MONEY.getCode();
	
	private Integer storeId;
	
	private Date createDate;

	private Date updateDate;

	//后增
	private Date payDate;
	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getBuyerName()
	{
		return buyerName;
	}

	public void setBuyerName(String buyerName)
	{
		this.buyerName = buyerName == null ? null : buyerName.trim();
	}

	public String getBuyerPhone()
	{
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone)
	{
		this.buyerPhone = buyerPhone == null ? null : buyerPhone.trim();
	}

	public String getBuyerAddress()
	{
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress)
	{
		this.buyerAddress = buyerAddress == null ? null : buyerAddress.trim();
	}

	public String getBuyerPost()
	{
		return buyerPost;
	}

	public void setBuyerPost(String buyerPost)
	{
		this.buyerPost = buyerPost == null ? null : buyerPost.trim();
	}

	public String getBuyerOpenid()
	{
		return buyerOpenid;
	}

	public void setBuyerOpenid(String buyerOpenid)
	{
		this.buyerOpenid = buyerOpenid == null ? null : buyerOpenid.trim();
	}

	public BigDecimal getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount)
	{
		this.orderAmount = orderAmount;
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

	public Date getPayDate()
	{
		return payDate;
	}

	public void setPayDate(Date payDate)
	{
		this.payDate = payDate;
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
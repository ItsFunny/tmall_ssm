/**
 * 
 */
package com.tmall.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;
import com.tmall.common.utils.ConverterUtils;

/**
 * @author Administrator
 *
 */
public class OrderDetailVO
{
	private String orderDetailId;

	private String orderId;

	private String productId;

	private String productName;

	private BigDecimal productPrice;

	private Integer productQuantity;

	private String productPitcureId;

	private String picturePath;

	private String content;

	private Integer orderStatus = OrderStatusEnum.WAIT.getCode();

	private Integer payStatus = PayStatusEnum.WAIT_PAY_MONEY.getCode();

	
	
	//后增
	private String createDateStr;
	private String deliverDateStr;
	private String receiptDateStr;
	private String updateDateStr;
	
	
	private Date deliverDate;
	private Date receiptDate;
	private Date createDate;
	private Date updateDate;

	public String getOrderDetailId()
	{
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId)
	{
		this.orderDetailId = orderDetailId;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

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

	public BigDecimal getProductPrice()
	{
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice)
	{
		this.productPrice = productPrice;
	}

	public Integer getProductQuantity()
	{
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity)
	{
		this.productQuantity = productQuantity;
	}

	public String getPicturePath()
	{
		return picturePath;
	}

	public void setPicturePath(String picturePath)
	{
		this.picturePath = picturePath;
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
		this.createDateStr = ConverterUtils.date2SimpleDateString(createDate);
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
		this.updateDateStr = ConverterUtils.date2SimpleDateString(updateDate);
	}

	public Date getDeliverDate()
	{
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate)
	{
		this.deliverDate = deliverDate;
		this.deliverDateStr = ConverterUtils.date2SimpleDateString(deliverDate);
	}

	public Date getReceiptDate()
	{
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate)
	{
		this.receiptDate = receiptDate;
		this.receiptDateStr = ConverterUtils.date2SimpleDateString(receiptDate);
	}

	public String getCreateDateStr()
	{
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr)
	{
		this.createDateStr = createDateStr;
		this.createDateStr = ConverterUtils.date2SimpleDateString(createDate);
	}

	public String getDeliverDateStr()
	{
		return deliverDateStr;
	}

	public void setDeliverDateStr(String deliverDateStr)
	{
		this.deliverDateStr = deliverDateStr;
	}

	public String getReceiptDateStr()
	{
		return receiptDateStr;
	}

	public void setReceiptDateStr(String receiptDateStr)
	{
		this.receiptDateStr = receiptDateStr;
	}

	public String getUpdateDateStr()
	{
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr)
	{
		this.updateDateStr = updateDateStr;
	}

	public String getProductPitcureId()
	{
		return productPitcureId;
	}

	public void setProductPitcureId(String productPitcureId)
	{
		this.productPitcureId = productPitcureId;
	}
}

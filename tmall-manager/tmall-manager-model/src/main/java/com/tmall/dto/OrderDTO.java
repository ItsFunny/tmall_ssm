/**
 * 
 */
package com.tmall.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tmall.common.enums.OrderStatusEnum;
import com.tmall.common.enums.PayStatusEnum;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.model.OrderMaster;
import com.tmall.vo.OrderDetailVO;

/**
 * @author Administrator
 *
 */
public class OrderDTO
{

	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-hh HH:mm:ss");
	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String buyerAddress;

	private String buyerPost;

	private String buyerOpenid;

	private BigDecimal orderAmount;

	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	private Integer payStatus = PayStatusEnum.WAIT_PAY_MONEY.getCode();

	private String buyerMessage;

	private String content;
	
	private List<OrderDetailVO> orderDetailList;

	private Date createDate;
	private Date updateDate;
	// 后增
	private String createDateStr;
	private String updateDateStr;
	private Date payDate;
	private String payDateStr;
	private Integer storeId;
	
	private Integer totalCount;
	
	
	public OrderDTO()
	{
		super();
		//下面的都是错误的
//		System.out.println("初始化时间");
//		init();
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getBuyerName()
	{
		return buyerName;
	}

	public void setBuyerName(String buyerName)
	{
		this.buyerName = buyerName;
	}

	public String getBuyerPhone()
	{
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone)
	{
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerAddress()
	{
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress)
	{
		this.buyerAddress = buyerAddress;
	}

	public String getBuyerPost()
	{
		return buyerPost;
	}

	public void setBuyerPost(String buyerPost)
	{
		this.buyerPost = buyerPost;
	}

	public String getBuyerOpenid()
	{
		return buyerOpenid;
	}

	public void setBuyerOpenid(String buyerOpenid)
	{
		this.buyerOpenid = buyerOpenid;
	}

	public BigDecimal getOrderAmount()
	{
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount)
	{
		this.orderAmount = orderAmount;
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
		createDateStr=dateFormat.format(createDate);
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
		updateDateStr=dateFormat.format(updateDate);
	}

	public String getBuyerMessage()
	{
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage)
	{
		this.buyerMessage = buyerMessage;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public List<OrderDetailVO> getOrderDetailList()
	{
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetailVO> orderDetailList)
	{
		Integer totalCount=0;
		for (OrderDetailVO orderDetailVO : orderDetailList)
		{
			totalCount+=orderDetailVO.getProductQuantity();
		}
		this.orderDetailList = orderDetailList;
		this.totalCount=totalCount;
	}

	public String getCreateDateStr()
	{
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr)
	{
		this.createDateStr = createDateStr;
	}

	public String getUpdateDateStr()
	{
		return updateDateStr;
	}

	public void setUpdateDateStr(String updateDateStr)
	{
		this.updateDateStr = updateDateStr;
	}

	public Date getPayDate()
	{
		return payDate;
	}

	public void setPayDate(Date payDate)
	{
		this.payDate = payDate;
		payDateStr=dateFormat.format(payDate);
	}

	public String getPayDateStr()
	{
		return payDateStr;
	}

	public void setPayDateStr(String payDateStr)
	{
		this.payDateStr = payDateStr;
	}

	public Integer getStoreId()
	{
		return storeId;
	}

	public void setStoreId(Integer storeId)
	{
		this.storeId = storeId;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

}

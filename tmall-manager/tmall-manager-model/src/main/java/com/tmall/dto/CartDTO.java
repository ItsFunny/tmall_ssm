/**
 * 
 */
package com.tmall.dto;

import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
public class CartDTO
{
	//购物车里面,每个商品都有一个ooid,这是唯一的
	//这样当提交订单的时候,就提交ooid,然后从cookie中取他的value
	private String ooId;
	
	private String productId;
	private Integer productQuantity;

	private ProductInfoVO productInfoVO;

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public Integer getProductQuantity()
	{
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity)
	{
		this.productQuantity = productQuantity;
	}

	public ProductInfoVO getProductInfoVO()
	{
		return productInfoVO;
	}

	public void setProductInfoVO(ProductInfoVO productInfoVO)
	{
		this.productInfoVO = productInfoVO;
	}

	public String getOoId()
	{
		return ooId;
	}

	public void setOoId(String ooId)
	{
		this.ooId = ooId;
	}

}

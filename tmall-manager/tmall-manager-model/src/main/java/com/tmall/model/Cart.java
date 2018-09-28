/**
 * 
 */
package com.tmall.model;


/**
 * @author Administrator
 *
 */

public class Cart 
{
	private String cartId;

	private String userId;
	private String productId;

	public String getCartId()
	{
		return cartId;
	}

	public void setCartId(String cartId)
	{
		this.cartId = cartId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

}

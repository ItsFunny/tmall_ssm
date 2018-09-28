/**
 * 
 */
package com.tmall.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmall.model.TmallUser;

/**
 * @author Administrator
 *
 */
public class UserDTO extends TmallUser implements Serializable
{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	
	


	
	
	/*
	 * 用户的购物车列表
	 */
	private List<CartDTO> cartDTOList;
	
	private Date lastLoginDate=StringUtils.isEmpty(getUserId())?null:new Date();
	private String lastLoginDateStr;

	@Override
	public boolean equals(Object obj)
	{
		UserDTO userDTO=(UserDTO) obj;
		return this.getUserId().equals(userDTO.getUserId());
	}
	


	public List<CartDTO> getCartDTOList()
	{
		return cartDTOList;
	}

	public void setCartDTOList(List<CartDTO> cartDTOList)
	{
		this.cartDTOList = cartDTOList;
	}




	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}



	public void setLastLoginDate(Date lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}



	public String getLastLoginDateStr()
	{
		return lastLoginDateStr;
	}



	public void setLastLoginDateStr(String lastLoginDateStr)
	{
		this.lastLoginDateStr = lastLoginDateStr;
	}



	
}

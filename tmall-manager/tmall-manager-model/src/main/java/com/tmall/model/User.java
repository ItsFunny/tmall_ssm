package com.tmall.model;

import java.util.Date;

public class User 
{
	private String userId;

	private String username;

	private String idCard;

	private String nickname;

	private String phoneNumber;

	private String password;

	private String openid;

	private Date createDate;

	private Date updateDate;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username == null ? null : username.trim();
	}

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(String idCard)
	{
		this.idCard = idCard == null ? null : idCard.trim();
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password == null ? null : password.trim();
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid == null ? null : openid.trim();
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

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
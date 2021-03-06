/**
*
* @author joker 
* @date 创建时间：2018年1月28日 下午12:51:58
* 
*/
package com.tmall.model;

import java.util.Date;

/**
 * 
 * @author joker
 * @date 创建时间：2018年1月28日 下午12:51:58
 */
public class TmallUser
{
	private String userId;
	
	//在sql表中重新创建了一个表
	private String realName;
	private String nickName;
	private String mailBox;
	private String IDCard;
	private String phoneNumber;
	private Integer status;
	private String sessionId;
	
	
	



	public TmallUser()
	{
		super();
	}

	public TmallUser(String userId)
	{
		super();
		this.userId = userId;
	}

	private String password;

	private String openid;
	

	private Date createDate;

	private Date updateDate;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}



	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
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

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getMailBox()
	{
		return mailBox;
	}

	public void setMailBox(String mailBox)
	{
		this.mailBox = mailBox;
	}

	public String getIDCard()
	{
		return IDCard;
	}

	public void setIDCard(String iDCard)
	{
		IDCard = iDCard;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	@Override
	public boolean equals(Object obj)
	{
		TmallUser tmallUser=(TmallUser) obj;
		return this.userId.equals(tmallUser.getUserId());
	}

	@Override
	public String toString()
	{
		return "TmallUser [userId=" + userId + ", realName=" + realName + ", nickName=" + nickName + ", mailBox="
				+ mailBox + ", IDCard=" + IDCard + ", phoneNumber=" + phoneNumber + ", status=" + status
				+ ", sessionId=" + sessionId + ", password=" + password + ", openid=" + openid + ", createDate="
				+ createDate + ", updateDate=" + updateDate + "]";
	}
}

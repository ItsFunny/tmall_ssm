/**
 * 
 */
package com.tmall.common.exception;

import com.tmall.common.enums.ResultEnums;

/**
 * 后台管理的错误
 * 
 * @author Administrator
 *
 */
public class TmallSellerException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8185909940160571789L;
	/*
	 * 抛出异常的url
	 */
	private String url;
	/*
	 * 异常的描述
	 */
	private String error;
	/*
	 * 异常的状态码
	 */
	private Integer code;
	/*
	 * 抛出异常之后附带的参数
	 */
	private String param;
	
	
	
	
	
	public TmallSellerException(ResultEnums resultEnums,String message,String param)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
		this.error=message;
		this.param=param;
	}
	
	public TmallSellerException(ResultEnums resultEnums,String url)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
		this.url=url;
	}
	public TmallSellerException(ResultEnums resultEnums)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
	}
	
	public TmallSellerException(String url, String error, Integer code, String param)
	{
		super();
		this.url = url;
		this.error = error;
		this.code = code;
		this.param = param;
	}


	public TmallSellerException(String url, String error)
	{
		super(error);
		this.url = url;
		this.error = error;
	}

	public TmallSellerException(String url)
	{
		super(url);
		this.url = url;
	}

	public TmallSellerException()
	{
		super();
	}

	public String geturl()
	{
		return url;
	}

	public void seturl(String url)
	{
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getParam()
	{
		return param;
	}

	public void setParam(String param)
	{
		this.param = param;
	}


}

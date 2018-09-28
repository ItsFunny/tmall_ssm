/**
 * 
 */
package com.tmall.common.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 所有结果返回集
 * 
 * @author Administrator
 *
 */
public class ResultVo<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 错误码 */
	private Integer code;
	/* 错误或者成功信息 */
	private String msg;
	/* 传输的数据 */
	@JsonProperty
	private T data;
	

	public ResultVo()
	{
		super();
	}
	

	public ResultVo(Integer code, String msg)
	{
		super();
		this.code = code;
		this.msg = msg;
	}


	public ResultVo(Integer code, T data)
	{
		super();
		this.code = code;
		this.data = data;
	}

	public ResultVo(Integer code, String msg, T data)
	{
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}


	@Override
	public String toString()
	{
		return "ResultVo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}

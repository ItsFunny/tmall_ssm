/**
 * 
 */
package com.tmall.common.exception;

import com.tmall.common.enums.ResultEnums;

/**
 * @author Administrator
 *
 */
public class TmallException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	private Integer code;

	public TmallException(ResultEnums resultEnums)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
	}
	
	public TmallException(String message)
	{
		super(message);
	}

	public TmallException()
	{
		super();
	}
	public TmallException(Integer code,String msg)
	{
		super(msg);
		this.code = code;
	}

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

}

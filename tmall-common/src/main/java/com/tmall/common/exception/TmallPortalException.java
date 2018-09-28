/**
 * 
 */
package com.tmall.common.exception;

import com.tmall.common.enums.ResultEnums;

/**
 * @author Administrator
 *
 */
public class TmallPortalException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5133540169881966555L;
	public TmallPortalException(ResultEnums resultEnums)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
	}
	public TmallPortalException()
	{
		super();
	}
	public TmallPortalException(Integer code)
	{
		super();
		this.code = code;
	}
	private Integer code;
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
	
}

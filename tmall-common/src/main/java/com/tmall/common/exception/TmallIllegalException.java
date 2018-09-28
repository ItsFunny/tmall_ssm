/**
 * 
 */
package com.tmall.common.exception;

import com.tmall.common.enums.ResultEnums;

/**
 * @author Administrator
 *
 */
public class TmallIllegalException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950220372562520995L;
	public TmallIllegalException(ResultEnums resultEnums)
	{
		super(resultEnums.getMsg());
		this.code=resultEnums.getCode();
	}
	public TmallIllegalException()
	{
		super();
	}
	public TmallIllegalException(Integer code)
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

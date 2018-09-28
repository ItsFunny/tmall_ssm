package com.tmall.common.exception;

public class TmallAuthorizeException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	public TmallAuthorizeException(StringBuffer url)
	{
		super(new String(url));
		this.url = new String(url);
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

}

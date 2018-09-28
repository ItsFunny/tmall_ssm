/**
 * 
 */
package com.tmall.common.dto;

import java.util.List;

/**
 * @author Administrator
 * @param <T>
 *
 */
public class PageDTO<T>
{
	private Integer pageSize;
	private Integer pageNum;
	private Integer totalCount;
	private Integer maxPage;
	
	private List<T> responseList;
	


	public PageDTO(Object pageSize, Object pageNum, Integer totalCount, Integer maxPage, List<T> responseList)
	{
		if (pageSize instanceof String)
		{
			pageSize = Integer.parseInt((String) pageSize);
		} else if (pageNum instanceof String)
		{
			pageNum = Integer.parseInt((String) pageNum);
		}
		this.pageSize = (Integer) pageSize;
		this.pageNum = (Integer) pageNum;
		this.totalCount = totalCount;
		this.maxPage=maxPage;
		this.responseList = responseList;
	}
	
	
	public PageDTO()
	{
		super();
	}


	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	public Integer getMaxPage()
	{
		return maxPage;
	}

	public void setMaxPage(Integer maxPage)
	{
		this.maxPage = maxPage;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(Integer pageNum)
	{
		this.pageNum = pageNum;
	}

	public List<T> getResponseList()
	{
		return responseList;
	}

	public void setResponseList(List<T> responseList)
	{
		this.responseList = responseList;
	}

}

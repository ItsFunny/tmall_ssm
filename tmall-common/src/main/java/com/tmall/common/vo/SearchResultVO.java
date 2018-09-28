/**
 * 
 */
package com.tmall.common.vo;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class SearchResultVO
{
	private List<?> objectList;
	private Integer pageSize;
	private Integer pageNum;
	private Long totalCount;
	private Long maxPage;
	public List<?> getObjectList()
	{
		return objectList;
	}

	public void setObjectList(List<?> objectList)
	{
		this.objectList = objectList;
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

	public Long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Long l)
	{
		this.totalCount = l;
	}

	public Long getMaxPage()
	{
		return maxPage;
	}

	public void setMaxPage(Long maxPage)
	{
		this.maxPage = maxPage;
	}

}

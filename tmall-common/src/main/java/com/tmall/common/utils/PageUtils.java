/**
 * 
 */
package com.tmall.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.tmall.common.dto.PageDTO;

/**
 * @author Administrator
 *
 */
public class PageUtils
{
	/*
	 * 分页显示
	 */
	/**
	 * 
	 * @param pageSize	每页显示的数目
	 * @param pageNum	第几页
	 * @param objectList 对传入的进行拆分,因为在redis中不可能是每几页保存一点数据,不可能的,通常都是保存所有的数据
	 * @return 返回的是泛型list
	 */
	public static <T> PageDTO<T> pageHelper(Integer pageSize, Integer pageNum, List<T> objectList)
	{
		Integer totalCount = null;
		Integer maxPage = null;
		totalCount = objectList.size();
		maxPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
		List<T> resultList = new ArrayList<T>();
		for (int i = (pageNum - 1) * pageSize; i < pageSize * pageNum && (pageNum-1) < maxPage&&i<totalCount; ++i)
		{
			resultList.add(objectList.get(i));
		}
		PageDTO<T> pageDTO = new PageDTO<>(pageSize, pageNum, totalCount, maxPage, resultList);
		return pageDTO;
	}
}

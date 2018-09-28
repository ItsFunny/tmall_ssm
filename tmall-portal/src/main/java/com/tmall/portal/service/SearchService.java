/**
 * 
 */
package com.tmall.portal.service;

import com.tmall.common.vo.SearchResultVO;

/**
 * @author Administrator
 *
 */
public interface SearchService
{
	SearchResultVO search(String q,Integer pageSize,Integer pageNum);
}

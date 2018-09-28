/**
 * 
 */
package com.tmall.search.service;


import com.tmall.common.vo.SearchResultVO;

/**
 * @author Administrator
 *
 */
public interface SearchService
{
	SearchResultVO searchProduct(String q,Integer pageNum,Integer pageSize);
}

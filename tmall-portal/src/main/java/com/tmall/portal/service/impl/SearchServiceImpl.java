/**
 * 
 */
package com.tmall.portal.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.vo.SearchResultVO;
import com.tmall.portal.service.SearchService;

/**
 * @author Administrator
 *
 */
@Service
public class SearchServiceImpl implements SearchService
{
	
	@Autowired
	private RestTemplate restTemplate;
	
	

	@Override
	public SearchResultVO search(String q, Integer pageSize, Integer pageNum)
	{
		String url=ServiceEnums.SEARCH_BASE_URL.getUrl()+"/query?q="+q+"&pageSize="+pageSize+"&pageNum="+pageNum;
//		Map<String, Object>params=new HashMap<String, Object>();
//		params.put("q", q);
//		params.put("pageSize", pageSize);
//		params.put("pageNum", pageNum);
		SearchResultVO searchResultVO = restTemplate.getForObject(url, SearchResultVO.class);
		return searchResultVO;
	}
	

}

/**
 * 
 */
package com.tmall.search.service.impl;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.common.vo.SearchResultVO;
import com.tmall.search.dao.SearchDao;
import com.tmall.search.service.SearchService;

/**
 * @author Administrator
 *
 */
@Service
public class SearchServiceImpl implements SearchService
{
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResultVO searchProduct(String q, Integer pageNum, Integer pageSize)
	{
		//创建查询对象
		SolrQuery solrQuery=new SolrQuery();
		String query="*"+q+"*";
		//导入查询条件
		solrQuery.setQuery(query);
		//设置分页相关设置
		solrQuery.setStart((pageNum-1)*pageSize);
		//设置每页显示的数量
		solrQuery.setRows(pageSize);
		//设置默认搜索域
		solrQuery.set("df","product_keywords");
		//设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		//执行查询
		SearchResultVO searchResultVO=null;
		try
		{
			searchResultVO=searchDao.search(solrQuery);
		} catch (SolrServerException e)
		{
			e.printStackTrace();
		}
		//计算查询结果总页数
		Long totalCount = searchResultVO.getTotalCount();
		long maxPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
		searchResultVO.setPageNum(pageNum);
		searchResultVO.setMaxPage(maxPage);
		return searchResultVO;
	}
}

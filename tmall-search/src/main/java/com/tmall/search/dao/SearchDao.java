/**
 * 
 */
package com.tmall.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import com.tmall.common.vo.ResultVo;
import com.tmall.common.vo.SearchResultVO;

/**
 * @author Administrator
 *
 */
public interface SearchDao
{
	SearchResultVO search(SolrQuery query) throws SolrServerException;
}

package com.tmall.search.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tmall.common.vo.SearchResultVO;
import com.tmall.search.dao.SearchDao;
import com.tmall.vo.ProductInfoVO;

@Component
public class SearchDaoImpl implements SearchDao
{

	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResultVO search(SolrQuery query) throws SolrServerException
	{
		SearchResultVO searchResultVO=new SearchResultVO();
		//执行查询条件
		QueryResponse queryResponse = solrServer.query(query);
		//获得查询到的结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		
		//取得查询结果的总数量
		searchResultVO.setTotalCount(solrDocumentList.getNumFound());
		
		//商品列表
//		List<ProductInfoAndCategoryVO>productInfoAndCategoryVOList=new ArrayList<>();
		List<ProductInfoVO>productInfoVOList=new ArrayList<ProductInfoVO>();
		//拼接商品列表
		try
		{
			for (SolrDocument solrDocument : solrDocumentList)
			{
//				ProductInfoAndCategoryVO productInfoAndCategoryVO=new ProductInfoAndCategoryVO();
//				productInfoAndCategoryVO.setCategoryName(solrDocument.get("product_category_name").toString());
				ProductInfoVO productInfoVO=new ProductInfoVO();
				productInfoVO.setProductId(solrDocument.get("id").toString());
				productInfoVO.setProductName(solrDocument.get("product_name").toString());
				productInfoVO.setProductSubTitle(solrDocument.get("product_sub_title").toString());
				productInfoVO.setProductDescription(solrDocument.get("product_description").toString());
//				System.out.println(solrDocument.get("product_picture_path").toString());
				productInfoVO.setProductPicturePath(solrDocument.get("product_picture_path").toString());
				productInfoVO.setProductReviewsCount(Integer.parseInt(solrDocument.get("product_reviews_count").toString()));
				productInfoVO.setCategoryName(solrDocument.get("product_category_name").toString());
				productInfoVO.setProductPrice(Double.parseDouble(solrDocument.get("product_price").toString()));
				String totalSelled = solrDocument.get("product_total_selled").toString();
				if(StringUtils.isEmpty(totalSelled))
				{
					totalSelled="0";
				}
				productInfoVO.setProductTotalSelled(Integer.parseInt(solrDocument.get("product_total_selled").toString()));
				productInfoVOList.add(productInfoVO);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		searchResultVO.setObjectList(productInfoVOList);
		return searchResultVO;
	}

	
	
}

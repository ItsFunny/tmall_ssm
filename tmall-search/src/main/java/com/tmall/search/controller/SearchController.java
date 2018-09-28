package com.tmall.search.controller;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tmall.common.vo.SearchResultVO;
import com.tmall.search.service.ProductService;
import com.tmall.search.service.SearchService;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

@RestController
public class SearchController
{
	@Autowired
	private ProductService productService;
	@Autowired
	private SearchService searchServive;
	@Autowired
	private SolrServer solrServer;

	private Gson gson = new Gson();

	@RequestMapping("/import-all-products")
	public List<ProductInfoAndCategoryVO> importAll()
	{
		List<ProductInfoAndCategoryVO> productInfoAndCategoryVOList = productService.findAll();
		try
		{
			for (int i = 0; i < productInfoAndCategoryVOList.size(); ++i)
			{
				ProductInfoAndCategoryVO productInfoAndCategoryVO = gson
						.fromJson(gson.toJson(productInfoAndCategoryVOList.get(i)), ProductInfoAndCategoryVO.class);
				for (ProductInfoVO productInfoVO : productInfoAndCategoryVO.getProductInfoList())
				{
					SolrInputDocument document = new SolrInputDocument();
					document.addField("id", productInfoVO.getProductId());
					document.addField("product_category_name", productInfoAndCategoryVO.getCategoryName());
					document.addField("product_name", productInfoVO.getProductName());
					document.addField("product_sub_title", productInfoVO.getProductSubTitle());
					document.addField("product_description", productInfoVO.getProductDescription());
					document.addField("product_reviews_count", productInfoVO.getProductReviewsCount());
					document.addField("product_picture_path", productInfoVO.getProductPicturePath());
					document.addField("product_total_selled", productInfoVO.getProductTotalSelled());
					document.addField("product_price", productInfoVO.getProductPrice());
					// 写入索引库
					solrServer.add(document);
					solrServer.commit();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// for (ProductInfoAndCategoryVO productInfoAndCategoryVO : productInfoVOList)
		// {
		// SolrInputDocument document = new SolrInputDocument();
		// // 这里的productinfovo好像需要更改下,因为没有categoryname,,
		// //不需要了,我将rest的接口返回的对象搞错了,
		//// document.setField("product_id", productInfoVO.getProductId());
		//// document.setField("product_name", productInfoVO.getProductName());
		//// document.setField("product_sub_title", productInfoVO.getProductSubTitle());
		//// document.setField("product_description",
		// productInfoVO.getProductDescription());
		//// document.setField("product_reviews_count",
		// productInfoVO.getProductReviewsCount());
		//// document.setField("product_picture_path",
		// productInfoVO.getProductPicturePath());
		// document.setField("product_category_name",
		// productInfoAndCategoryVO.getCategoryName());
		// for(ProductInfoVO
		// productInfoVO:productInfoAndCategoryVO.getProductInfoList())
		// {
		// document.setField("product_id", productInfoVO.getProductId());
		// document.setField("product_name", productInfoVO.getProductName());
		// document.setField("product_sub_title", productInfoVO.getProductSubTitle());
		// document.setField("product_description",
		// productInfoVO.getProductDescription());
		// document.setField("product_reviews_count",
		// productInfoVO.getProductReviewsCount());
		// document.setField("product_picture_path",
		// productInfoVO.getProductPicturePath());
		// // 写入索引库
		// solrServer.add(document);
		// }
		// }
		// solrServer.commit();
		return productInfoAndCategoryVOList;
	}

	@RequestMapping("/query")
	public SearchResultVO queryForObject(@RequestParam(name = "pageSize", defaultValue = "50") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "q", required = true) String q)
	{
		SearchResultVO searchResultVO = null;
		try
		{
			q = new String(q.getBytes("iso-8859-1"), "utf-8");
			searchResultVO = searchServive.searchProduct(q, pageNum, pageSize);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return searchResultVO;
	}

	@RequestMapping("/delete")
	public void deleteDate() throws SolrServerException, IOException
	{
		solrServer.deleteByQuery("*:*");
		solrServer.commit();

	}
}

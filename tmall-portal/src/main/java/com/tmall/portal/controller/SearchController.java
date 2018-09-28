/**
 * 
 */
package com.tmall.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.vo.SearchResultVO;
import com.tmall.portal.service.SearchService;

/**
 * @author Administrator
 *
 */
@Controller
public class SearchController
{
	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public ModelAndView searchProduct(@RequestParam(name = "q") String q,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) throws UnsupportedEncodingException
	{
		String query = new String(q.getBytes("iso-8859-1"), "utf-8");
		SearchResultVO searchResultVO = searchService.search(query, pageSize, pageNum);
		ModelAndView modelAndView = new ModelAndView("search_detail");
		modelAndView.addObject("searchResultVO", searchResultVO);
		return modelAndView;
	}

}

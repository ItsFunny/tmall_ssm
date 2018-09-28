/**
*
* @author joker 
* @date 创建时间：2018年5月20日 上午11:30:47
* 
*/
package com.rebuildtmall.tmall_sso_test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 
* @author joker 
* @date 创建时间：2018年5月20日 上午11:30:47
*/
@RestController
public class TestController
{

	@RequestMapping("/test")
	public String test2()
	{
		return "2";
	}
}

package com.rebuildtmall.tmall_sso_test.config;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rebuildtmall.tmall_sso_test.filter.LoginFilter;

@Configuration
public class Config
{
	@Bean
	public FilterRegistrationBean<Filter>filterRegistrationBean()
	{
		FilterRegistrationBean<Filter>filterRegistrationBean=new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginFilter());
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/test"));
		return filterRegistrationBean;
	}


}

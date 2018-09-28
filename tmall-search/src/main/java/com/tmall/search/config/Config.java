/**
 * 
 */
package com.tmall.search.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author Administrator
 *
 */

@Configuration
@ComponentScan(basePackages="com.tmall.search")
public class Config
{
	@Value("${SOLR_SERVER_URL}")
	private String SOLR_SERVER_URL;
	
	@Bean(name = "dataSource")
	@Profile(value = "dev")
	public DruidDataSource dataSource()
	{
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("123");
		dataSource.setUrl("jdbc:mysql://localhost/rebuild_tmall?characterEncoding=utf-8");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(5);
		return dataSource;
	}

	@Bean(name = "dataSource")
	@Profile(value = "prod")
	public DruidDataSource dataSourceProd()
	{
		return null;
	}
	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate=new RestTemplate();
		return restTemplate;
	}
	@Bean
	public SolrServer solrServer()
	{
		SolrServer solrServer=new HttpSolrServer(SOLR_SERVER_URL);
		return solrServer;
	}
}

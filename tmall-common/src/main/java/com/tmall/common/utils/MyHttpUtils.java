/**
 * 
 */
package com.tmall.common.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Administrator
 *
 */
public class MyHttpUtils
{
	public static void get(String url)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		try
		{
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

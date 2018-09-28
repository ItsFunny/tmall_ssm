/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午2:23:33
* 
*/
package com.tmall.service;

/**
* 
* @author joker 
* @date 创建时间：2018年1月27日 下午2:23:33
*/
public interface IBaseService<T>
{
	T findOne(String sqlName,String key);
}

/**
*
* @author joker 
* @date 创建时间：2018年1月27日 下午5:30:31
* 
*/
package com.tmall.dao;

import org.apache.ibatis.annotations.Mapper;

/**
* 
* @author joker 
* @date 创建时间：2018年1月27日 下午5:30:31
*/
@Mapper
public interface IBaseDao<M,PK>
{
	M findOne(PK key);
	
	void save(M m);
	void update(M m);

}

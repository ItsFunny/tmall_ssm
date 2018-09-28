/**
 * 
 */
package com.tmall.service;




import com.tmall.common.dto.PageDTO;
import com.tmall.dto.StoreDTO;

/**
 * @author Administrator
 *
 */
public interface StoreService
{
	/**
	 * 查询所有的店铺信息
	 * @param start	用于分页,第几页
	 * @param number	用于分页,每页显示的记录数
	 * @return
	 */
	PageDTO<StoreDTO> findAllStoreInfos(Integer start,Integer number);
	
	/**
	 * 查询一个店铺
	 * @param storeId
	 * @return
	 */
	
	StoreDTO findStoreByStoreId(Integer storeId);
	
	/**
	 * 删除一个店铺
	 * @param storeId
	 */
	void deleteStoreByStoreId(Integer storeId);
}

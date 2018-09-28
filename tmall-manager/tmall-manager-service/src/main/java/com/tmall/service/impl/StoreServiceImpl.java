/**
 * 
 */
package com.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.dto.PageDTO;
import com.tmall.common.utils.ConverterUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.utils.PageUtils;
import com.tmall.dao.ProductInfoDao;
import com.tmall.dao.StoreDao;
import com.tmall.dto.StoreDTO;
import com.tmall.service.StoreService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class StoreServiceImpl implements StoreService
{
	@Autowired
	private StoreDao storeDao;

	@Autowired
	private JedisClient jedisClient;
	@Override
	public PageDTO<StoreDTO> findAllStoreInfos(Integer start,Integer number)
	{
		String json=null;
		List<StoreDTO>storeDTOList=null;
		PageDTO<StoreDTO> pageDTO =new PageDTO<>();
		Integer pageNum=(start/number)+1;
		try
		{
			json=jedisClient.get(RedisConstant.ALL_STORES);
		} catch (Exception e)
		{
		}
		if(!StringUtils.isEmpty(json))
		{
			//start=(pageNum-1)*number;
			storeDTOList=JsonUtils.jsonToList(json, StoreDTO.class);
			pageDTO= PageUtils.pageHelper(number, pageNum, storeDTOList);
			return pageDTO;
		}
		storeDTOList=storeDao.findStoresLimited(start,number);
		for (StoreDTO storeDTO : storeDTOList)
		{
			String date=ConverterUtils.date2SimpleDateString(storeDTO.getCreateDate());
			storeDTO.setCreateDateStr(date);
		}
		Integer totalCount = storeDao.getStoreCount();
		Integer maxPage=(totalCount%number)==0?totalCount/number:(totalCount/number)+1;
		pageDTO.setMaxPage(maxPage);
		pageDTO.setPageSize(number);
		pageDTO.setTotalCount(totalCount);
		pageDTO.setPageNum(pageNum);
		pageDTO.setResponseList(storeDTOList);
		try
		{
			List<StoreDTO> stores = storeDao.findStores();
			for (StoreDTO storeDTO : stores)
			{
				String date=ConverterUtils.date2SimpleDateString(storeDTO.getCreateDate());
				storeDTO.setCreateDateStr(date);
			}
			String value = JsonUtils.List2Json(stores);
			jedisClient.set(RedisConstant.ALL_STORES, value);
		} catch (Exception e)
		{
		}
		return pageDTO;
	}
	@Override
	public StoreDTO findStoreByStoreId(Integer storeId)
	{
		return storeDao.findOneByStoreId(storeId);
	}
	@Override
	public void deleteStoreByStoreId(Integer storeId)
	{
		storeDao.deleteStoreByStoreId(storeId);
	}

}

/**
 * 
 */
package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.SellerEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.SellerInfoDao;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.service.SellerInfoService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * @author Administrator
 *
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService
{
	@Autowired
	private SellerInfoDao sellerInfoDao;
	@Autowired
	private JedisClient jedisClient;

	// 106-20:30 这里需要做修改,因为这里肯定是要分页的
	@Override
	public List<SellerDTO> findStoreAllSellers(Integer storeId, Integer start, Integer end)
	{
		String json = null;
		String key = String.format(RedisConstant.STORE_ALL_SELLERS, ":" + storeId);
		List<SellerDTO> sellerDTOList = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			sellerDTOList = JsonUtils.jsonToList(json, SellerDTO.class);
			return sellerDTOList;
		}
		sellerDTOList = sellerInfoDao.findStoreSellers(storeId, start, end);
		try
		{
			String value = JsonUtils.List2Json(sellerDTOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
		return sellerDTOList;
	}

	@Override
	public List<SellerDTO> findAllSellersByStoreId(Integer storeId)
	{
		String json = null;
		String key = String.format(RedisConstant.STORE_ALL_SELLERS, ":" + storeId);
		List<SellerDTO> sellerDTOList = null;
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			sellerDTOList = JsonUtils.jsonToList(json, SellerDTO.class);
			return sellerDTOList;
		}
		sellerDTOList = sellerInfoDao.findStoreAllSellersByStoreId(storeId);
		try
		{
			if (sellerDTOList.size() > 0 && sellerDTOList != null)
			{
				String value = JsonUtils.List2Json(sellerDTOList);
				jedisClient.set(key, value);
			}
		} catch (Exception e)
		{
		}
		return sellerDTOList;
	}

	@Override
	public String updateSellerStatusByStoreId(Integer storeId, Integer status)
	{

		sellerInfoDao.updateSellerByStoreId(storeId, status);
		// finally
		// {
		// // 这里最好还是用finally,确保全删了
		// List<SellerDTO> sellerDTOList = findAllSellersByStoreId(storeId);
		// if(sellerDTOList!=null&&sellerDTOList.size()>0)
		// {
		// for (SellerDTO sellerDTO : sellerDTOList)
		// {
		// String key2 = String.format(RedisConstant.SELLER_INFO_PREFIX,
		// sellerDTO.getOpenId());
		// jedisClient.del(key2);
		// }
		// jedisClient.del(key);
		// }
		return null;
	}
}

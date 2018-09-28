/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.dao.StoreDao;
import com.tmall.dao.StorePictureDao;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.StorePicture;
import com.tmall.store.dao.impl.JedisClient;
import com.tmall.store.service.StoreService;

/**
 * @author Administrator
 *
 */
@Service
public class StoreServiceImpl implements StoreService
{
	@Autowired
	private StoreDao storeDao;
	
	//这里干脆一起写得了,其实最好是多写一个单独的接口好,毕竟面向接口编程,接口隔离原则
	@Autowired
	private StorePictureDao storePictureDao;
	@Autowired
	private JedisClient jedisClient;
	

	
	@Override
	public Integer addStoreAndUpdateRedis(SellerDTO sellerDTO,String picturePath)
	{
		if(!isTheStoreExist(sellerDTO))
		{
			String key = RedisConstant.ALL_STORES;
//			String path=PictureEnums.PICTURE_SHOW_STORE+picturePath;
			storeDao.addStore(sellerDTO);
			StorePicture storePicture=new StorePicture();
			storePicture.setStoreId(sellerDTO.getStoreId());
			storePicture.setStorePicturePath(picturePath);
			storePictureDao.addStorePicture(storePicture);
			jedisClient.del(key);
//			System.out.println(sellerDTO.getStoreId());
			return sellerDTO.getStoreId();
		}
		return null;
	}
	public boolean isTheStoreExist(SellerDTO sellerDTO)
	{
		SellerDTO store = findStoreByStoreName(sellerDTO.getStoreName());
		if(store ==null)
		{
			return false;
		}
		return true;
	}

	@Override
	public List<SellerDTO> findAllStores()
	{
		String key = RedisConstant.ALL_STORES;
		String json = null;
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
		sellerDTOList = storeDao.findAllStores();
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
	public SellerDTO findStoreByStoreName(String storeName)
	{
		return storeDao.findStoreByStoreName(storeName);
	}
	@Override
	public List<StoreDTO> findSellerStoreInfo(String userId)
	{
		return storeDao.findSellerStoreInfo(userId);
	}
}

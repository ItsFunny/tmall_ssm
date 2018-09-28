/**
 * 
 */
package com.tmall.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmall.common.constant.RedisConstant;
import com.tmall.dto.SellerDTO;
import com.tmall.dto.StoreDTO;
import com.tmall.model.StorePicture;
import com.tmall.wechat.dao.StoreDao;
import com.tmall.wechat.dao.StorePictureDao;
import com.tmall.wechat.dao.impl.JedisClient;
import com.tmall.wechat.service.StoreService;

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
	private StorePictureDao storePictureDao;
	@Autowired
	private JedisClient jedisClient;
	@Transactional
	@Override
	public void addStore(SellerDTO sellerDTO,String picturePath)
	{
		storeDao.addStore(sellerDTO);
		addStorePicture(sellerDTO.getStoreId(), picturePath);
	}
	@Override
	public void addStorePicture(int storeId, String path)
	{
		StorePicture storePicture=new StorePicture();
		storePicture.setStoreId(storeId);
		storePicture.setStorePicturePath(path);
		storeDao.addStorePicture(storePicture);
	}
	@Override
	public void addStoreAndUpdateRedis(SellerDTO sellerDTO, String picturePath)
	{
		addStore(sellerDTO, picturePath);
		String key=RedisConstant.ALL_STORES;
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
	}
	@Override
	public boolean checkIsTheStoreExit(String storeName)
	{
		StoreDTO storeDTO = storeDao.findStoreByName(storeName);
		if(storeDTO!=null)
		{
			return true;
		}
		return false;
	}
	

}

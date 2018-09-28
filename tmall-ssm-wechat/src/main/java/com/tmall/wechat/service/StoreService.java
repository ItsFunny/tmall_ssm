/**
 * 
 */
package com.tmall.wechat.service;

import com.tmall.dto.SellerDTO;

/**
 * @author Administrator
 *
 */

public interface StoreService
{
	void addStore(SellerDTO sellerDTO,String picturePath);
	void addStorePicture(int storeId,String path);
	void addStoreAndUpdateRedis(SellerDTO sellerDTO,String picturePath);
	boolean checkIsTheStoreExit(String storeName);
}

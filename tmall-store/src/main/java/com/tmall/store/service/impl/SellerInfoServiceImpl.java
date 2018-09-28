/**
 * 
 */
package com.tmall.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.SellerInfoDao;
import com.tmall.dto.SellerDTO;
import com.tmall.store.service.SellerInfoService;

/**
 * @author Administrator
 *
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService
{

	@Autowired
	private SellerInfoDao sellerInfoDao;
	@Override
	public SellerDTO findSellerByOpenId(String openid)
	{
		return sellerInfoDao.findOneSellerByOpenId(openid);
	}
	@Override
	public void addSeller(SellerDTO sellerDTO)
	{
		sellerInfoDao.addSeller(sellerDTO);
	}
}

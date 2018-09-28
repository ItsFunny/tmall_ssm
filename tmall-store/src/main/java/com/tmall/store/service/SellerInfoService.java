/**
 * 
 */
package com.tmall.store.service;

import com.tmall.dto.SellerDTO;

/**
 * @author Administrator
 *
 */
public interface SellerInfoService
{
	SellerDTO findSellerByOpenId(String openid);
	
	void addSeller(SellerDTO sellerDTO);
}

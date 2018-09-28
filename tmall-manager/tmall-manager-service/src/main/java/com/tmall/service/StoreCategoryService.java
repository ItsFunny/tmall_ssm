/**
 * 
 */
package com.tmall.service;

import java.util.List;


import com.tmall.model.SellerInfo;
import com.tmall.model.StoreCategory;

/**
 * @author Administrator
 *
 */
public interface StoreCategoryService
{
	List<StoreCategory>findSellerCategoryList(SellerInfo sellerInfo);
	

}

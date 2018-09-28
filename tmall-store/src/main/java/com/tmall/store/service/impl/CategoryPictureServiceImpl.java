/**
 * 
 */
package com.tmall.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.CategoryPictureDao;
import com.tmall.store.service.CategoryPictureService;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Service
public class CategoryPictureServiceImpl implements CategoryPictureService
{
	@Autowired
	private CategoryPictureDao categoryPictureDao;

	@Override
	public void updateCategoryPictureByCategoryType(String categoryPicturePath,Integer categoryType)
	{
		categoryPictureDao.updateCategoryPicture(categoryPicturePath, categoryType);
	}

	@Override
	public void addCategoryPicture(ProductCategoryVO productCategoryVO)
	{
		categoryPictureDao.addCategoryPicture(productCategoryVO);
	}
}

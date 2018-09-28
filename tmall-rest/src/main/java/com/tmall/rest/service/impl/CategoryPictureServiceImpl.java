/**
 * 
 */
package com.tmall.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmall.dao.CategoryPictureDao;
import com.tmall.model.CategoryPicture;
import com.tmall.rest.service.CategoryPictureService;

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
	public void updateCategoryPicture(String picturePath, Integer categoryType)
	{
		categoryPictureDao.updateCategoryPicture(picturePath, categoryType);
	}
	@Override
	public List<CategoryPicture> findPicturesByCategoryTypeList(List<Integer> categoryType)
	{
		/*
		 * 这里可以先查询所有的,再一一匹配,但是又感觉不对~~~
		 */
		List<CategoryPicture> categoryTypes= categoryPictureDao.findPicturesByCategoryTypeList(categoryType);
		return categoryTypes;
	}
}

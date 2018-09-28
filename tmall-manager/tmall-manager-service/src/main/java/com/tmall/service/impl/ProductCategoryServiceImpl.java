/**
 * 
 */
package com.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.RedisEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.CategoryPictureDao;
import com.tmall.dao.ProductCategoryDao;
import com.tmall.service.ProductCategoryService;
import com.tmall.service.dao.impl.JedisClient;
import com.tmall.vo.ProductCategoryVO;

/**
 * @author Administrator
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService
{
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JedisClient jedisClient;
	private final String REST_BASE_URL = ServiceEnums.REST_BASE_URL.getUrl();

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private CategoryPictureDao categoryPictureDao;

	@Override
	public List<ProductCategoryVO> findAllCategories()
	{
		String url = REST_BASE_URL + "/category/all";
		ResultVo<List<ProductCategoryVO>> resultVo = new ResultVo<>();
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		List<ProductCategoryVO> productCategoryList = resultVo.getData();
		return productCategoryList;
	}

	@Override
	public void deleteCategory(Integer categoryType)
	{
		productCategoryDao.deleteCategory(categoryType);
		// String key=RedisEnums.ALLUPCATEGORIES.getKey();
		String key2 = RedisEnums.ALLCATEGORIES.getKey();
		String key = RedisEnums.ALLCATEGORIES_WITH_PICTURES.getKey();
		String key3 = RedisEnums.ALLCATEGORIES_WITH_PRODUCTS.getKey();

		try
		{
			jedisClient.del(key2);
			jedisClient.del(key3);
			jedisClient.del(key);
			// jedisClient.del(key);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public void updateCategoryName(String categoryName, Integer categoryType)
	{
		productCategoryDao.updateProductCategoryName(categoryName, categoryType);
		String key = RedisEnums.ALLCATEGORIES.getKey();
		// String key2=RedisEnums.ALLUPCATEGORIES.getKey();
		try
		{
			// jedisClient.del(key2);
			jedisClient.del(key);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public void updateCategoryPicture(String picturePath, Integer categoryType)
	{
		categoryPictureDao.updateCategoryPicture(picturePath, categoryType);
	}

	@Transactional
	@Override
	public void updateCategory(String categoryName, String picturePath, Integer categoryType)
	{
		updateCategoryName(categoryName, categoryType);
		updateCategoryPicture(picturePath, categoryType);
	}

	@Override
	public void addCategory(ProductCategoryVO productCategory)
	{
		/*
		 * 这里的redis好像并不需要,所以先注释了
		 */
		// String key=RedisEnums.ALLCATEGORIES.getKey();
		// try
		// {
		// jedisClient.del(key);
		// } catch (Exception e)
		// {
		// }
		productCategoryDao.addCategory(productCategory);
		// List<ProductCategory> productCategories =
		// productCategoryDao.findAllProductCategories();
		//
		// try
		// {
		// String value=JsonUtils.List2Json(productCategories);
		// jedisClient.set(key, value);
		// } catch (Exception e)
		// {
		// }
	}

	@Override
	public void addCategoryPicture(ProductCategoryVO productCategory)
	{
		try
		{
			categoryPictureDao.addCategoryPicture(productCategory);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public void addCategoryAndPicture(ProductCategoryVO productCategory, String path)
	{
		addCategory(productCategory);
		addCategoryPicture(productCategory);
	}

	@Override
	public void addAndUpdateCategoryAndPicture(ProductCategoryVO productCategory, String path)
	{
		addCategoryAndPicture(productCategory, path);
		String key = RedisEnums.ALLCATEGORIES_WITH_PICTURES.getKey();
		try
		{
			jedisClient.del(key);
		} catch (Exception e)
		{
		}
		List<ProductCategoryVO> categories = findAllCategories();
		categories.add(productCategory);
		try
		{
			String value = JsonUtils.List2Json(categories);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
		}
	}

	@Override
	public List<ProductCategoryVO> findSellerCategoryList(List<Integer> categoryTypeList, Integer storeId)
	{
		String key = String.format(RedisConstant.STORE_ALL_CATEGORIES_WITH_PICTURES, ":" + storeId.toString());
		String json = null;
		List<ProductCategoryVO> productCategoryVOList = new ArrayList<ProductCategoryVO>();
		try
		{
			json = jedisClient.get(key);
		} catch (Exception e)
		{
		}
		if (!StringUtils.isEmpty(json))
		{
			productCategoryVOList = JsonUtils.jsonToList(json, ProductCategoryVO.class);
			return productCategoryVOList;
		}
		productCategoryVOList = productCategoryDao.findAllCategoriesAndPictures(categoryTypeList);
		try
		{
			String value = JsonUtils.List2Json(productCategoryVOList);
			jedisClient.set(key, value);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return productCategoryVOList;
	}

	@Override
	public String deleteStoreCategory(Integer storeId, List<Integer> categoryTypeList)
	{
		productCategoryDao.deleteCategoryList(categoryTypeList);
		return null;
	}
}

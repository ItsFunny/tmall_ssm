///**
// * 
// */
//package com.tmall.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mysql.fabric.xmlrpc.base.Array;
//import com.tmall.model.ProductCategory;
//import com.tmall.model.ProductInfo;
//
///**
// * @author Administrator
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class ProductCategoryDaoTest
//{
//	@Autowired
//	private ProductCategoryDao productCategoryDao;
//	
//	@Test
//	public void inTest()
//	{
//		List<Integer> typeList=new ArrayList<>();
//		typeList.add(1);
//		typeList.add(2);
//		typeList.add(3);
//		List<ProductCategory> list = productCategoryDao.findAllProductCategoriesInCategoryType(typeList);
//		for (ProductCategory productCategory : list)
//		{
//			System.out.println(productCategory.toString());
//		}
//		
//		
//	}
//
//}

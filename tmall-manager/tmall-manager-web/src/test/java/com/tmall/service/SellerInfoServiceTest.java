///**
// * 
// */
//package com.tmall.service;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tmall.model.SellerInfo;
//import com.tmall.model.StoreCategory;
//
///**
// * @author Administrator
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class SellerInfoServiceTest
//{
//	@Autowired
//	private StoreCategoryService storeCategoryService;
//	@Test
//	public void findStoreCategoryListTest()
//	{
//		SellerInfo sellerInfo=new SellerInfo();
//		sellerInfo.setOpenId("qwe");
//		sellerInfo.setStoreId(1);
//		List<StoreCategory> findSellerCategoryList = storeCategoryService.findSellerCategoryList(sellerInfo);
//		for (StoreCategory storeCategory : findSellerCategoryList)
//		{
//			System.out.println(storeCategory.toString());
//		}
//	}
//	
//}

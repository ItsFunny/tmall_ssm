//package com.tmall.dao;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tmall.model.ProductInfo;
//
///*
// * 
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//// @ContextConfiguration(classes=Config.class)
//public class ProductInfoDaoTest
//{
//	@Autowired
//	private ProductInfoDao productInfoDao;
//	private Logger logger = LoggerFactory.getLogger(ProductInfoDaoTest.class);
//
//	@Test
//	public void addProductTest()
//	{
//
//		Random random = new Random();
//		for (Integer i = 500; i < 652; ++i)
//		{
//			logger.info("[productinfodao测试]");
//
//			ProductInfo productInfo = new ProductInfo();
//			productInfo.setCategoryType(random.nextInt(7) + 1);
//			productInfo.setPictureId(random.nextInt(5));
//			productInfo.setProductDescription("description" + "----" + i + "------");
//			Integer id = i + 1;
//			productInfo.setProductId(id.toString());
//			productInfo.setProductName("productName:" + "----" + i + "~~~~");
//			productInfo.setProductPrice(new BigDecimal((i + 1) * 31.8));
//			productInfo.setProductPromotePrice(new BigDecimal((i + 1) * 15.99));
//			productInfo.setProductStatus(0);
//			productInfo.setProductStock((i + 1) * 70);
//			productInfo.setProductSubTitle("subtitle:" + "----" + i + "----");
//			productInfoDao.addProductInfo(productInfo);
//		}
//	}
//
//}

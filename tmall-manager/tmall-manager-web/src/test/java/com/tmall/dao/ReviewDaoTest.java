///**
// * 
// */
//package com.tmall.dao;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tmall.model.Review;
//
///**
// * @author Administrator
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class ReviewDaoTest
//{
//	@Autowired
//	private ReviewDao  reviewDao;
//	@Test
//	public void dateTest()
//	{
//		List<Review> reviews = reviewDao.findAllReviewsByProductId("100");
//		for (Review review : reviews)
//		{
//			System.out.println(review.getCreateDate());
//		}
//	}
//}

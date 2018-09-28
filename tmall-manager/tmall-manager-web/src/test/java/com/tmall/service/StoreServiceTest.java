//package com.tmall.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tmall.common.dto.PageDTO;
//import com.tmall.dto.StoreDTO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class StoreServiceTest
//{
//	@Autowired
//	private StoreService storeService;
//
//	@Test
//	public void testFindAll()
//	{
//		PageDTO<StoreDTO> pageDTO = storeService.findAllStoreInfos(1, 2);
//		System.out.println(pageDTO.toString());
//	}
//}

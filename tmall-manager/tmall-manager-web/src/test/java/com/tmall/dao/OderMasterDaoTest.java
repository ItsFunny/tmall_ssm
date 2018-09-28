//package com.tmall.dao;
//
//import java.math.BigDecimal;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.tmall.model.OrderDetail;
//import com.tmall.model.OrderMaster;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class OderMasterDaoTest
//{
//
//	@Autowired
//	private OrderMasterDao orderMasterDao;
//	@Autowired
//	private OrderDetailDao orderDetailDao;
//	@Test
//	public void addTest()
//	{
//		OrderMaster orderMaster=new OrderMaster();
//		orderMaster.setBuyerAddress("add");
//		orderMaster.setBuyerName("joker");
//		orderMaster.setBuyerOpenid("sss");
//		orderMaster.setBuyerPhone("12873123");
//		orderMaster.setBuyerPost("123333");
//		orderMaster.setOrderAmount(new BigDecimal(12345));
//		orderMaster.setOrderId("124444444444");
//		orderMasterDao.addOrderMaster(orderMaster);
//		OrderDetail orderDetail=new OrderDetail();
//		orderDetail.setOrderDetailId("1231424123");
//		orderDetail.setOrderId(orderMaster.getOrderId());
//		orderDetail.setProductId("100");
//		orderDetail.setProductName("sad");
//		orderDetail.setProductPitcureId(1);
//		orderDetail.setProductPrice(new BigDecimal(100));
//		orderDetail.setProductQuantity(100);
//		orderDetailDao.addOrderDetail(orderDetail);
//		
//	}
//}

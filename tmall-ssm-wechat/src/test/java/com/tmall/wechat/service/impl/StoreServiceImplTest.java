package com.tmall.wechat.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmall.dto.SellerDTO;
import com.tmall.wechat.service.StoreService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring/*.xml")
public class StoreServiceImplTest
{

	@Autowired
	private StoreService storeService;
	@Test
	public void addStorePictureTest()
	{
		storeService.addStorePicture(9, "aaaaaa");
	}

}

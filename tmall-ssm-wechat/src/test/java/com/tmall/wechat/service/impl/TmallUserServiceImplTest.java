package com.tmall.wechat.service.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmall.wechat.service.ITmallUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:spring/*.xml")
public class TmallUserServiceImplTest
{
	@Autowired
	private ITmallUserService service;

//	@Test
//	public void testAddNormalUser()
//	{
//		TmallUserTest tmallUser=new TmallUserTest();
//		tmallUser.setNickName("asdasd");
//		tmallUser.setOpenid("zz");
//		tmallUser.setPassword("123");
//		tmallUser.setPhoneNumber("qweqzxc");
//		try
//		{
//			service.addNormalUser(tmallUser);
//		} catch (Exception e)
//		{
//			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
//		}
//		System.out.println("-----------------");
//		System.out.println(tmallUser.getUserId());
//		
//	}

}

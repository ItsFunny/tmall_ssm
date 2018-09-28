//package com.tmall.dao;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.tmall.model.Picture;
//import com.tmall.vo.ProductInfoVO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-*.xml")
//public class PictureDaoTest
//{
//	@Autowired
//	private PictureDao pictureDao;
//	@Autowired
//	private ProductInfoDao productInfoDao;
//	@Test
//	public void inTest()
//	{
//		List<Integer> ids=new ArrayList<Integer>();
//		ids.add(0);
//		ids.add(1);
//		List<Picture> pictures = pictureDao.findProductSinglePicturesByProductIds(ids);
//		for (Picture picture : pictures)
//		{
//			System.out.println(picture.toString());
//		}
//	}
//	@Test
//	public void addTest()
//	{
//		String[] paths= {"https://img.alicdn.com/tfs/TB14EbqeILJ8KJjy0FnXXcFDpXa-468-1236.jpg",
//				"https://gw.alicdn.com/bao/uploaded/i3/725677994/TB1SKqGXxTxLuJjy1XcXXb.gXXa_!!0-item_pic.jpg",
//				"https://gw.alicdn.com/bao/uploaded/i4/725677994/TB1INLbXsrHK1Jjy1zkXXX.QpXa_!!0-item_pic.jpg",
//				"https://gw.alicdn.com/bao/uploaded/i3/2255459295/TB1Uonfb6JTMKJjSZFPXXbHUFXa_!!0-item_pic.jpg",
//				"https://gw.alicdn.com/bao/uploaded/i1/2823354534/TB1K6SYfv6H8KJjy0FjXXaXepXa_!!0-item_pic.jpg",
//				"https://gw.alicdn.com/bao/uploaded/i4/TB1XmUbRFXXXXbTXpXXXXXXXXXX_!!0-item_pic.jpg"};
//		List<ProductInfoVO> productInfoVOs = productInfoDao.findUpAll();
//		List<String> ids=new ArrayList<>();
//		for (ProductInfoVO productInfoVO : productInfoVOs)
//		{
//			ids.add(productInfoVO.getProductId());
//		}
//		Random random=new Random();
//		for(int i=0;i<150;++i)
//		{
//			int index=random.nextInt(ids.size());
//			String productId=ids.get(index);
//			ids.remove(index);
//			String path=paths[random.nextInt(6)];
//			pictureDao.addSinglePictureByProductId(productId, path,0);
//		}
//		
//	}
//}

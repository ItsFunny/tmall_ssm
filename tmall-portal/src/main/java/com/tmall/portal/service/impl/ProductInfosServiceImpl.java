/**
 * 
 */
package com.tmall.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tmall.common.enums.ResultEnums;
import com.tmall.common.exception.TmallException;
import com.tmall.common.vo.ResultVo;
import com.tmall.dao.ProductInfoDao;
import com.tmall.dto.CartDTO;
import com.tmall.model.ProductInfo;
import com.tmall.portal.service.ProductCategoryService;
import com.tmall.portal.service.ProductInfosService;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoAndCategoryVO;
import com.tmall.vo.ProductInfoVO;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class ProductInfosServiceImpl implements ProductInfosService
{
	private Logger logger = LoggerFactory.getLogger(ProductInfosServiceImpl.class);
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Autowired
	private ProductInfoDao productInfoDao;
	// 这里其实有点违背单一原则:因为这个是ProductInfo的模块
	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public ResultVo<List<ProductInfoAndCategoryVO>> showAllProducts()
	{
		String url = REST_BASE_URL + "/product/show/all";
		ResultVo<List<ProductInfoAndCategoryVO>> resultVO = new ResultVo<>();

		// 这里还可以再写点,万一rest服务器挂了咋办
		resultVO = restTemplate.getForObject(url, ResultVo.class);
		return resultVO;
	}

	@Override
	public ResultVo<ProductInfoAndCategoryVO> showAllProductsByCategoryTypeAndStoreId(Integer categoryType,Integer storeId)
	{
//		List<ProductInfoVO> productInfos = new ArrayList<>();
		// List<ProductInfoVO> productInfoVOList = new ArrayList<>();
		String url = REST_BASE_URL + "/category/detail/{storeId}/{categoryType}";
		ResultVo<ProductInfoAndCategoryVO> resultVo = new ResultVo<>();
		Map<String, Object>params=new HashMap<>();
		params.put("storeId", storeId);
		params.put("categoryType", categoryType);
		try
		{
			resultVo = restTemplate.getForObject(url, resultVo.getClass(), params);
		} catch (Exception e)
		{
//			logger.error("[查询单一类目下的商品]远程rest服务器挂了");
//			productInfos = productInfoDao.findAllProductsByCategoryType(categoryType);
//			ResultVo<ProductCategoryVO> category = productCategoryService.findOne(categoryType);
//			ProductInfoAndCategoryVO productInfoAndCategoryVO = new ProductInfoAndCategoryVO();
//			BeanUtils.copyProperties(category, productInfoAndCategoryVO);
			// for (ProductInfoVO productInfo : productInfos)
			// {
			// ProductInfoVO productInfoVO = new ProductInfoVO();
			// BeanUtils.copyProperties(productInfo, productInfoVO);
			// productInfoVOList.add(productInfoVO);
			// }
//			productInfoAndCategoryVO.setProductInfoList(productInfos);
//			return new ResultVo<ProductInfoAndCategoryVO>(200, "sucess", productInfoAndCategoryVO);
		}
		return resultVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultVo<ProductInfoVO> findOneProduct(String productId)
	{
		String url = REST_BASE_URL + "/product/detail/{productId}";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		ResultVo<ProductInfoVO> resultVo = new ResultVo<>();
		try
		{
			resultVo = restTemplate.getForObject(url, ResultVo.class, params);
		} catch (Exception e)
		{
			ProductInfoVO productInfoVO = productInfoDao.findOne(productId);
			resultVo.setCode(200);
			resultVo.setData(productInfoVO);
			resultVo.setMsg("sucess");
		}
		return resultVo;
	}
	@Override
	public ProductInfoVO findOneProductFromDB(String productId)
	{
		return productInfoDao.findOne(productId);
	}

	@Override
	public void update(ProductInfo productInfo)
	{
		productInfoDao.update(productInfo);
	}

	@Override
	public void decreateStock(List<CartDTO> cartDTOList)
	{
		for (CartDTO cartDTO : cartDTOList)
		{
			ProductInfoVO productInfoVO = productInfoDao.findOne(cartDTO.getProductId());
			if (productInfoVO == null)
			{
				throw new TmallException(ResultEnums.PRODUCT_NOTE_EXIT);
			}
			ProductInfo productInfo = new ProductInfo();
			BeanUtils.copyProperties(productInfoVO, productInfo);
			synchronized (this)
			{
				Integer result = productInfoVO.getProductStock() - cartDTO.getProductQuantity();
				productInfo.setProductStock(result);
				productInfoDao.update(productInfo);
			}
		}
	}

	@Override
	public List<ProductInfoVO> findProductsInIds(List<String> productIds)
	{
		// String url=REST_BASE_URL+"/product/cart/products?{json}";
		// JSONArray jsonArray=JSONArray.fromObject(productIds);
		// String json=jsonArray.toString();
		// Map<String, String>params=new HashMap<>();
		// params.put("json", json);
		// ResultVo<List<ProductInfoVO>> resultVo=null;
		// try
		// {
		// resultVo= restTemplate.getForObject(url, ResultVo.class,params);
		// } catch (Exception e)
		// {
		// // TODO: handle exception
		// }
		// return resultVo;
		List<ProductInfoVO> productInfoVOs = productInfoDao.findSomeProductsInIds(productIds);
		return productInfoVOs;
	}
	@Override
	public void increateStock(List<CartDTO> cartDTO)
	{
		for (CartDTO cartDTO2 : cartDTO)
		{
			String productId = cartDTO2.getProductId();
			ProductInfoVO productInfoVO = productInfoDao.findOne(productId);
			if (productInfoVO == null)
			{
				throw new TmallException(ResultEnums.PRODUCT_NOTE_EXIT);
			}
			Integer stock = productInfoVO.getProductStock() + cartDTO2.getProductQuantity();
			ProductInfo productInfo = new ProductInfo();
			BeanUtils.copyProperties(productInfoVO, productInfo);
			productInfo.setProductStock(stock);
			productInfoDao.update(productInfo);
		}
	}

	@Override
	public void updateProductSelledNumber(ProductInfoVO productInfoVO,Integer buyNum)
	{
		ProductInfo productInfo=new ProductInfo();
		BeanUtils.copyProperties(productInfoVO, productInfo);
		Integer totalSelled = productInfo.getProductTotalSelled();
		totalSelled+=buyNum;
		productInfo.setProductTotalSelled(totalSelled);
		update(productInfo);
	}

	@Override
	public Integer findProductStoreIdByProductId(String productId)
	{
		return productInfoDao.findProductStoreIdByProductId(productId);
	}

	@Override
	public ResultVo<List<ProductInfoVO>> findAllProductsByCategoryType(Integer categoryType)
	{
		String url = REST_BASE_URL + "/category/detail/{categoryType}";
		 ResultVo<List<ProductInfoVO>> resultVo = restTemplate.getForObject(url, ResultVo.class,categoryType);
		return resultVo;
	}
	
}

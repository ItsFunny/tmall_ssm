/**
 * 
 */
package com.tmall.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tmall.common.constant.SellerConstant;
import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ProductStatusEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.SellerEnums;
import com.tmall.common.utils.FileUtils;
import com.tmall.common.utils.JsonUtils;
import com.tmall.common.utils.KeyUtils;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;
import com.tmall.dto.SellerDTO;
import com.tmall.model.Picture;
import com.tmall.model.ProductCategory;
import com.tmall.model.ProductInfo;
import com.tmall.model.Property;
import com.tmall.model.SellerInfo;
import com.tmall.model.StoreCategory;
import com.tmall.store.service.PictureService;
import com.tmall.store.service.ProductCategoryService;
import com.tmall.store.service.ProductInfoService;
import com.tmall.store.service.PropertyService;
import com.tmall.store.service.SellerInfoService;
import com.tmall.store.service.SellerService;
import com.tmall.store.service.StoreCategoryService;
import com.tmall.store.service.StoreService;
import com.tmall.store.utils.StoreUtils;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoVO;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService
{
	@Autowired
	private SellerInfoService sellerInfoService;
	@Autowired
	private StoreCategoryService storeCategoryService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired

	private ProductInfoService productInfoService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private StoreService storeService;

	/*
	 * 获取卖家的商品类目
	 */
	public List<ProductCategoryVO> findSellerCategoryList(String openid)
	{

		SellerDTO sellerInfo = sellerInfoService.findSellerByOpenId(openid);
		List<StoreCategory> categoryList = storeCategoryService.findSellerCategoryList(sellerInfo);
		if (categoryList != null && categoryList.size() > 0)
		{
			// 拼接categoryType
			List<Integer> categoryTypeList = new ArrayList<Integer>();
			for (StoreCategory storeCategory : categoryList)
			{
				if (!categoryTypeList.contains(storeCategory.getProductCategoryType()))
				{
					categoryTypeList.add(storeCategory.getProductCategoryType());
				}
			}
			List<ProductCategoryVO> sellerCategoryList = productCategoryService.findSellerCategoryList(categoryTypeList,
					sellerInfo.getStoreId());
			return sellerCategoryList;
		}
		return null;
	}

	@Override
	public List<ProductInfoVO> findSellerProductsByCategoryType(Integer categoryType, Integer storeId)
	{
		return productInfoService.findAllProductByCategoryType(categoryType, storeId);
	}

	@Override
	public PictureVO findProductAllPictures(String productId)
	{
		return pictureService.findProductAllPictures(productId);
	}

	@Override
	public List<PropertyDTO> findProductAllPropertyValues(String productId)
	{
		return propertyService.findProductAllPropertyValues(productId);
	}

	@Override
	public void addProductAndPicture(ProductInfo productInfo, Integer storeId, MultipartFile file)
	{
		PictureDTO pictureDTO = FileUtils.generateFile(file, PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
		productInfo.setPictureId(pictureDTO.getPictureId());
		productInfoService.addOneProduct(productInfo, storeId, pictureDTO);
	}

	@Override
	public void addProductTypePicture(String productId, String type, MultipartFile file)
	{
		PictureDTO pictureDTO = FileUtils.generateFile(file, PictureEnums.PICTURE_TYPE_PRODUCT.getMsg());
		pictureService.addProductPicture(pictureDTO.getPictureId(), productId, pictureDTO.getPicturePath(), type);
	}

	@Transactional
	@Override
	public void set2indexPicture(String pictureId, String productId)
	{
		ProductInfoVO productInfoVO = productInfoService.findOneProduct(productId);

		productInfoVO.setStoreId(1);
		String oldPictureId = productInfoVO.getPictureId();

		Picture picture = new Picture();
		picture.setPictureId(pictureId);
		picture.setPictureType(PictureEnums.INDEX_PICTURE.getCode());
		pictureService.updatePictureByPictureId(picture);

		pictureService.deleteProductOnePictureByPictureId(oldPictureId);
		productInfoVO.setPictureId(pictureId);
		productInfoService.updateProductInfo(productInfoVO);

	}

	@Override
	public List<Property> findPropertyListByCategoryType(Integer categoryType, Integer storeId)
	{
		return propertyService.findAllPropertiesByCategoryType(categoryType, storeId);
	}

	@Override
	public void updateCategory(ProductCategory productCategory, Integer storeId, MultipartFile file, String oldPath)
	{
		PictureDTO pictureDTO = FileUtils.generateFile(file, PictureEnums.PICTURE_TYPE_CATEGORY.getMsg());
		FileUtils.deleteFile(oldPath, PictureEnums.PICTURE_TYPE_CATEGORY.getMsg());
		productCategoryService.updateSellerCategoryAndUpdateRedis(productCategory, storeId,
				pictureDTO.getPicturePath());
	}

	@Override
	public void addCategoryAndPicture(ProductCategoryVO productCategoryVO, Integer storeId)
	{
//		PictureDTO pictureDTO = FileUtils.generateFile(file, PictureEnums.PICTURE_TYPE_CATEGORY.getMsg());
		Random random = new Random();
		Integer categoryType = random.nextInt(10000) + 10000;
		productCategoryVO.setCategoryType(categoryType);
//		productCategoryVO.setCategoryPicturePath(pictureDTO.getPicturePath());
		productCategoryService.addCategoryAndUpdateRedis(productCategoryVO, storeId);

	}

	@Override
	public void deleteCategoryByCategoryType(Integer categoryType, Integer storeId, boolean isDelete)
	{
		Integer status = null;
		// 如果确认删除所有商品
		if (isDelete)
		{
			status = ProductStatusEnums.DELETE.getStatus();
		} else
		{
			status = ProductStatusEnums.DOWN.getStatus();
		}
		productCategoryService.deleteCategoryAndUpdateRedis(categoryType, status, storeId);

	}

	@Override
	public ResultVo<String> addStoreAndSeller(SellerDTO sellerDTO, WxMpUser wxMpUser,MultipartFile picture, HttpServletResponse response)
	{
		PictureDTO pictureDTO = FileUtils.generateFile(picture, PictureEnums.PICTURE_TYPE_STORE.getMsg());
		Integer storeId = storeService.addStoreAndUpdateRedis(sellerDTO,pictureDTO.getPicturePath());
		//店家店铺的图片
		
		ResultVo<String> resultVo = new ResultVo<>();
		if (storeId != null)
		{
			sellerDTO.setStoreId(storeId);
			sellerDTO.setLevel(SellerEnums.FOUNDER.getCode());
			sellerDTO.setRealName(String.format(SellerConstant.SELLER_USERNAME, wxMpUser.getNickname()));
			String sellerId = KeyUtils.generateUniqueKey();
			sellerDTO.setUserId(sellerId);
			sellerInfoService.addSeller(sellerDTO);
			resultVo.setCode(200);
			resultVo.setMsg("店铺注册成功");
			UUID uuid = UUID.randomUUID();
			String redisValue = JsonUtils.objectToJson(sellerDTO);
			StoreUtils.writeSeller2CookieAndRedis(uuid.toString(), wxMpUser.getOpenId(),redisValue, response);
		} else
		{
			// 说明名字重复了
			resultVo.setCode(ResultEnums.SELLER_STORE_NAME_REPEAT.getCode());
			resultVo.setMsg(ResultEnums.SELLER_STORE_NAME_REPEAT.getMsg());
		}
		return resultVo;
	}
/*-
 * -------------------------------------------------------------------分隔线-------------------------------------------
 */

	@Override
	public List<ProductCategoryVO> findStoreCategoryList(Integer storeId)
	{
		return storeCategoryService.findStoreCategoryList(storeId);
	}
}

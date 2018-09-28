/**
 * 
 */
package com.tmall.store.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tmall.common.dto.PictureDTO;
import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;
import com.tmall.dto.SellerDTO;
import com.tmall.model.ProductCategory;
import com.tmall.model.ProductInfo;
import com.tmall.model.Property;
import com.tmall.model.SellerInfo;
import com.tmall.vo.PictureVO;
import com.tmall.vo.ProductCategoryVO;
import com.tmall.vo.ProductInfoVO;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Administrator
 *
 */
public interface SellerService
{
	/*
	 * 查 获取店铺的商品类目
	 */
	//del
	List<ProductCategoryVO> findSellerCategoryList(String openid);
	
	List<ProductCategoryVO>findStoreCategoryList(Integer storeId);

	/**
	 * @param categoryType
	 *            类目的编号
	 * @param storeId
	 *            店铺的编号
	 * @return 返回这个店铺下的某个类目的所有属性,不包含属性值
	 */
	List<Property> findPropertyListByCategoryType(Integer categoryType, Integer storeId);

	/**
	 * 返回这个店铺下的这个类目下的所有商品
	 * 
	 * @param categoryType
	 *            类目的标识
	 * @param storeId
	 *            店铺的id
	 * @return
	 */
	List<ProductInfoVO> findSellerProductsByCategoryType(Integer categoryType, Integer storeId);

	/**
	 * 返回这个商品的所有图片,包括什么详情图片,显示图片等等
	 * 
	 * @param productId
	 *            商品的id
	 * @return
	 */
	PictureVO findProductAllPictures(String productId);

	/*
	 * 增
	 */
	/**
	 * 增加某一类目下的一个商品和图片
	 * 
	 * @param productInfo
	 * @param storeId
	 * @param file
	 */
	void addProductAndPicture(ProductInfo productInfo, Integer storeId, MultipartFile file);

	/**
	 * 在某个店铺下添加一个类目
	 * 
	 * @param productCategoryVO
	 * @param storeId
	 * @param file
	 */
	void addCategoryAndPicture(ProductCategoryVO productCategoryVO, Integer storeId);

	/**
	 * 商品图片管理页面,添加某一类型的图片
	 * 
	 * @param type
	 *            用于判断啥类型图片
	 * @param file
	 *            图片对象
	 */
	void addProductTypePicture(String productId, String type, MultipartFile file);

	/**
	 * 查询某个产品下的所有属性
	 * 
	 * @param productId
	 * @return
	 */
	List<PropertyDTO> findProductAllPropertyValues(String productId);

	/**
	 * 增加一个店铺和一个卖家信息
	 * 
	 * @param sellerDTO
	 * @param picture
	 *            店铺对应的图片
	 * @return
	 */
	ResultVo<String> addStoreAndSeller(SellerDTO sellerDTO, WxMpUser wxMpUser, MultipartFile picture,
			HttpServletResponse response);

	/*
	 * 改
	 */
	/**
	 * 将某张图片设置为首页显示的图片,同时删除原先的首页图片
	 * 
	 * @param pictureId
	 *            要设置的图片id
	 * @param productId
	 *            要设置的产品id
	 */
	void set2indexPicture(String pictureId, String productId);

	/**
	 * 更新类目的名称和图片
	 * 
	 * @param productCategory
	 *            更新的主体
	 * @param storeId
	 *            属于哪家店铺
	 * @param file
	 *            上传的图片
	 * @param oldPath
	 *            之前的图片,添加新的图片的时候,需要将旧的图片删除
	 */
	void updateCategory(ProductCategory productCategory, Integer storeId, MultipartFile file, String oldPath);

	/*
	 * 删
	 */
	void deleteCategoryByCategoryType(Integer categoryType, Integer storeId, boolean isDelete);
}

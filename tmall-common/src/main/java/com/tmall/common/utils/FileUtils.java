/**
 * 
 */
package com.tmall.common.utils;

import java.io.File;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import com.tmall.common.dto.PictureDTO;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ResultEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.common.exception.TmallException;

/**
 * @author Administrator
 *
 */
public class FileUtils
{
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/*
	 * 
	 */
	/**
	 * @param filepath
	 *            上传的图片
	 * @param objectName
	 *            上传之后要更改的命名
	 * @param type
	 *            上传的类型:类目图还是商品图还是店铺图片
	 */
	public static PictureDTO generateFile(MultipartFile filepath, String type)
	{
		String path = null;
		String suffix = filepath.getOriginalFilename().substring(filepath.getOriginalFilename().lastIndexOf('.'));
		String objectName = KeyUtils.generateRandomSequence();
		if (!filepath.isEmpty())
		{
			try
			{
				if (type.equals(PictureEnums.PICTURE_TYPE_CATEGORY.getMsg()))
				{
					File dir = new File(ServiceEnums.PICTURE_CATEGORY_LOCATION.getUrl());
					if (!dir.exists())
					{
						dir.mkdir();
					}
					// 写文件到服务器上

					File saveFile = new File(dir.getAbsoluteFile() + File.separator + objectName + suffix);
					filepath.transferTo(saveFile);
					path = PictureEnums.PICTURE_SHOW_CATEGORY.getMsg() + objectName + suffix;
				} else if (type.equals(PictureEnums.PICTURE_TYPE_PRODUCT.getMsg()))
				{
					File dir_product = new File(ServiceEnums.PICTURE_PRODUCT_LOCATION.getUrl());
					if (!dir_product.exists())
					{
						dir_product.mkdir();
					}
					File productSaveFile = new File(
							dir_product.getAbsoluteFile() + File.separator + objectName + suffix);
					filepath.transferTo(productSaveFile);
					path = PictureEnums.PICTURE_SHOW_PRODUCT.getMsg() + objectName + suffix;
				} else if (type.equals(PictureEnums.PICTURE_TYPE_STORE.getMsg()))
				{
					File dir_product = new File(ServiceEnums.PICTURE_STORE_LOCATION.getUrl());
					if (!dir_product.exists())
					{
						dir_product.mkdir();
					}
					File storeFile = new File(dir_product.getAbsolutePath() + File.separator + objectName + suffix);
					filepath.transferTo(storeFile);
					path = PictureEnums.PICTURE_SHOW_STORE.getMsg() + objectName + suffix;
				}

			} catch (Exception e)
			{
				throw new TmallException(ResultEnums.PICTURE_SERVER_IS_DOWN);
			}
		}
		PictureDTO pictureDTO = new PictureDTO();
		pictureDTO.setPictureId(objectName);
		pictureDTO.setPicturePath(path);
		return pictureDTO;
	}

	public static boolean deleteFile(String path, String type)
	{
		String objectName = path.substring(path.lastIndexOf('/'));
		File file = null;
		if (type.equals(PictureEnums.PICTURE_TYPE_CATEGORY.getMsg()))
		{
			file = new File(ServiceEnums.PICTURE_CATEGORY_LOCATION.getUrl());
		} else if (type.equals(PictureEnums.PICTURE_TYPE_PRODUCT.getMsg()))
		{
			file = new File(ServiceEnums.PICTURE_PRODUCT_LOCATION.getUrl());
		} else if (type.equals(PictureEnums.PICTURE_TYPE_STORE.getMsg()))
		{
			file = new File(ServiceEnums.PICTURE_STORE_LOCATION.getUrl());
		} else if (type.equals(PictureEnums.PICTURE_TYPE_QRCODE.getMsg()))
		{
			file=new File(ServiceEnums.PICTURE_QRCODE_LOCATION.getUrl());
		}
		File deleteFile = new File(file.getAbsoluteFile() + File.separator + objectName);
		if (deleteFile.exists())
		{
			if (deleteFile.delete())
			{
				return true;
			}
		} else
		{
			logger.error("[删除文件]文件已经不存在");
			return true;
		}
		return false;
	}
}

/**
 * 
 */
package com.tmall.wechat.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import com.google.gson.Gson;
import com.tmall.common.constant.CookieConstant;
import com.tmall.common.constant.RedisConstant;
import com.tmall.common.enums.PictureEnums;
import com.tmall.common.enums.ServiceEnums;
import com.tmall.dto.SellerDTO;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * @author Administrator
 *
 */
public class StoreUtils
{
	public static ByteArrayOutputStream createQRgen(String url)
	{
		// 如果有中文，可使用withCharset("UTF-8")方法

		// 设置二维码url链接，图片宽度500*500，JPG类型
		return QRCode.from(url).withSize(500, 500).to(ImageType.JPG).stream();
	}

	public static String getQRPath(String url, UUID sessionId)
	{
		// UUID randoUUID = UUID.randomUUID();
		ByteArrayOutputStream createQRgen = createQRgen(url);
		String fileName = sessionId + ".jpg";
		File dirFile = new File(ServiceEnums.PICTURE_QRCODE_LOCATION.getUrl());
		try
		{
			if (!dirFile.exists())
			{
				dirFile.mkdir();
			}
			File file = new File(dirFile.getAbsolutePath() + File.separator + fileName);
			if (!file.exists())
			{
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(new File(file.getAbsolutePath()));
			os.write(createQRgen.toByteArray());
			os.flush();
			os.close();
			return PictureEnums.PICTURE_SHOW_QRCODE.getMsg() + fileName;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static void setCookie(HttpServletResponse response, String name, String value)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
	}


	// 获得客户端真实IP地址的方法一：
	public static String getRemortIP(HttpServletRequest request)
	{
		if (request.getHeader("x-forwarded-for") == null)
		{
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	// 获得客户端真实IP地址的方法二：
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}

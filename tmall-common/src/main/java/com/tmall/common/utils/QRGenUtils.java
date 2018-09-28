/**
 * 
 */
package com.tmall.common.utils;

import java.io.ByteArrayOutputStream;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * 二维码工具包
 * 
 * @author Administrator
 *
 */
public class QRGenUtils
{
	public static ByteArrayOutputStream createQRgen(String url)
	{
		// 如果有中文，可使用withCharset("UTF-8")方法

		// 设置二维码url链接，图片宽度250*250，JPG类型
		return QRCode.from(url).withSize(250, 250).to(ImageType.JPG).stream();
	}
}

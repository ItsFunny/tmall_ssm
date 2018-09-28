/**
 * 
 */
package com.tmall.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Administrator
 *
 */
public class ConverterUtils
{
	private static  DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/*
	 * string转为char,将中间的以*代替 
	 * 返回string
	 */
	public static String hideKeyString(String object)
	{
		char[] array = object.toCharArray();
		for (int i = 1; i < array.length - 1; ++i)
		{
			array[i] = '*';
		}
		String newString = new String(array);
		return newString;
	}
	public static Date date2SimpleDate(Date object) throws ParseException
	{
		
		String resource = date2SimpleDateString(object);
		Date date = dateFormat.parse(resource);
		return date;
	}
	public static String date2SimpleDateString(Date object)
	{
		
		String format = dateFormat.format(object);
		return format;
	}
	
}

/**
 * 
 */
package com.tmall.common.utils;

import java.util.Random;

/**
 * 生成key
 * 
 * @author Administrator
 *
 */
public class KeyUtils
{
	private static Random random = new Random();

	public static String generateUniqueKey()
	{
		Integer a = random.nextInt(9000000) + 1000000;
		return System.currentTimeMillis() + String.valueOf(a);
	}

	public static String generateProductId()
	{
		Integer a = random.nextInt(10000);
		return System.currentTimeMillis() / 1000000 + String.valueOf(a);
	}

	public static String generateRandomSequence()
	{
		char[] arr =
		{ 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'H', 'i', 'j', 'k', 'n', 'm', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'h', 'I', 'J', 'K', 'N', 'M', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '-', '_' };
		
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < 8; ++i)
		{
			int index = random.nextInt(arr.length);
			char c = arr[index];
			str.append(c);
		}
		int number = random.nextInt(10000);
		str.append(number);
		return new String(str);
	}
}

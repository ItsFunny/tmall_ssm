/**
*
* @author joker 
* @date 创建时间：2018年1月24日 下午1:09:39
* 
*/
package com.tmall.common.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joker
 * @date 创建时间：2018年1月24日 下午1:09:39 常见集合类的工具类
 */
public class CommonUtils
{
	/**
	 * 获取map中value对应的key
	 * 
	 * @param value
	 *            value是独一无二的
	 * @return
	 * @author joker
	 * @date 创建时间：2018年1月24日 下午1:10:47
	 */
	public static <K, V> K getKeyByValue(Map<K, V> map, V value)
	{
		for (K key : map.keySet())
		{
			if (map.get(key).equals(value))
			{
				return key;
			}
		}
		return null;
	}

	/**
	 * 将collection转换为list
	 * 
	 * @param collection
	 * @param type
	 * @return
	 * @author joker
	 * @date 创建时间：2018年1月25日 下午9:09:05
	 */
	public static <T> List<T> collection2List(Collection<T> collection, Class<T> type)
	{
		@SuppressWarnings("unchecked")
		T[] arrs = (T[]) Array.newInstance(type, collection.size());
		T[] array = collection.toArray(arrs);
		List<T> list = Arrays.asList(array);
		return list;
	}

	/**
	 * 利用反射将java的model对象转为sql中的表名字 如:model:CategoryPicture 则sql中的为category_picture
	 * 
	 * @param javaModelName
	 * @return
	 * @author joker
	 * @date 创建时间：2018年1月27日 下午5:09:29
	 */
	public static String getSqlModel(String javaModelName)
	{
		char[] arr = javaModelName.toCharArray();
		int more = 0;
		String result = null;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] > 65 && arr[i] < 97)
			{
				more++;
			}
		}
		if (more != 0)
		{
			char[] newArr = new char[arr.length + more];
			for (int i = 0, j = 0; i < arr.length; i++, j++)
			{
				char temp = ' ';
				if (arr[i] > 65 && arr[i] < 97 && i > 0)
				{
					temp = arr[i];
				}
				if (temp != ' ')
				{
					newArr[j] = '_';
					newArr[j + 1] = temp;
					j++;
				} else
				{
					newArr[j] = arr[i];
				}
			}
			result = new String(newArr).toLowerCase();
		} else
		{
			result = javaModelName.toLowerCase();
		}
		return result;
	}
}

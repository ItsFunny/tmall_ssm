package com.tmall.common.utils;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import net.sf.json.JSONArray;

/**
 * 淘淘商城自定义响应结构
 */
public class JsonUtils
{

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final Gson GSON = new Gson();

	/**
	 * 将对象转换成json字符串。
	 * <p>
	 * Title: pojoToJson
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data)
	{
		// String string = MAPPER.writeValueAsString(data);
		try
		{
			String string = GSON.toJson(data);
			return string;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json结果集转化为对象
	 * 
	 * @param jsonData
	 *            json数据
	 * @param clazz
	 *            对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType)
	{
		try
		{
//			T t = MAPPER.readValue(jsonData, beanType);
			T t = GSON.fromJson(jsonData, beanType);
			return t;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转换成pojo对象list
	 * <p>
	 * Title: jsonToList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType)
	{

		try
		{
			JSONArray jsonArray = JSONArray.fromObject(jsonData);
			List<T> list = JSONArray.toList(jsonArray, beanType);
			return list;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * list对象转为json
	 */
	public static String List2Json(Collection<?> requestList)
	{
		JSONArray jsonArray = JSONArray.fromObject(requestList);

		String json = jsonArray.toString();
		return json;
	}

}

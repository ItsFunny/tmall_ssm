/**
 * 
 */
package com.tmall.common.utils.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Administrator
 *
 */
public class Date2StringSerializer extends JsonSerializer<Date>
{

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException
	{
		// TODO Auto-generated method stub
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-hh HH:mm:ss");
//		Date date2=new Date(date.getTime());
//		jsonGenerator.
		jsonGenerator.writeNumber(date.getTime()/10);
	}
}

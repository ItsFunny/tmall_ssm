/**
 * 
 */
package com.tmall.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;
import com.tmall.model.Property;
import com.tmall.model.PropertyValue;
import com.tmall.rest.service.PropertyService;
import com.tmall.rest.service.PropertyValueService;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/property")
public class PropertyController
{
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private PropertyValueService propertyValueService;

	//采用下面的简化版
	@RequestMapping("/product/values/{productId}")
	public ResultVo<List<PropertyDTO>> findAllProductPropertyValues(@PathVariable("productId") String productId)
	{
		List<PropertyValue> propertyValueList = propertyValueService.findAllPropertyValues(productId);
		List<Integer> propertyValueIds = new ArrayList<>();

		for (PropertyValue propertyValue : propertyValueList)
		{
			if (!propertyValueIds.contains(propertyValue.getPropertyId()))
			{
				propertyValueIds.add(propertyValue.getPropertyId());
			}
		}
		List<Property> propertyList = propertyService.findAllProductPropertyByPropertyIds(propertyValueIds,productId);
		List<PropertyDTO> propertyDTOList = new ArrayList<>();
		for (Property property : propertyList)
		{
			PropertyDTO propertyDTO = new PropertyDTO();
			BeanUtils.copyProperties(property, propertyDTO);
			String propertyValueString=null;
			List<String> values = null;
			for (PropertyValue propertyValue : propertyValueList)
			{
				if (propertyValue.getPropertyId().equals(property.getPropertyId()))
				{
//					values.add(propertyValue);
					propertyValueString=propertyValue.getPropertyValue();
					if(propertyValueString.contains(","))
					{
						String[] strings = propertyValueString.split(",");
						values=Arrays.asList(strings);
					}
				}
			}
			propertyDTO.setPropertyValues(values);
			propertyDTO.setPropertyValue(propertyValueString);
			propertyDTOList.add(propertyDTO);
		}
		return new ResultVo<List<PropertyDTO>>(200, "sucess", propertyDTOList);
	}

	@RequestMapping("/product/{productId}")
	public ResultVo<List<PropertyDTO>>findProductProperties(@PathVariable("productId")String productId,HttpServletRequest request,HttpServletResponse response)
	{
		List<PropertyDTO> propertyDTOList = propertyService.findProductPropertiesByProductId(productId);
		ResultVo<List<PropertyDTO>>resultVo=new ResultVo<>();
		resultVo.setData(propertyDTOList);
		resultVo.setCode(200);
		resultVo.setMsg("sucess");
		return resultVo;
	}
}

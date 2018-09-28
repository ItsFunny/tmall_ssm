/**
 * 
 */
package com.tmall.portal.service;

import com.tmall.common.vo.ResultVo;
import com.tmall.dto.PropertyDTO;

/**
 * @author Administrator
 *
 */
public interface PropertyService
{
	ResultVo<PropertyDTO> findAllPropertyValusByProductId(String productId);
}

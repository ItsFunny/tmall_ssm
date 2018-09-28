/**
 * 
 */
package com.tmall.portal.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public interface CartService
{
	void write2Cookie(String productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;
}

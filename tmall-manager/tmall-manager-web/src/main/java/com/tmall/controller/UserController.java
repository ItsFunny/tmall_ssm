/**
 * 
 */
package com.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tmall.common.dto.PageDTO;
import com.tmall.common.utils.PageUtils;
import com.tmall.dto.UserDTO;
import com.tmall.service.UserService;

/**
 * @author Administrator
 *
 */
@Controller
public class UserController
{
	@Autowired
	private UserService userService;

	@RequestMapping("/all-users")
	public ModelAndView showAllUsers(@RequestParam(name="pageSize",defaultValue="5",required=false)Integer pageSize
			,@RequestParam(name="pageNum",defaultValue="1",required=false)Integer pageNum,HttpServletRequest request,HttpServletResponse response)
	{
		List<UserDTO> userList = userService.findAllUsers();
		PageDTO<UserDTO> pageDTO = PageUtils.pageHelper(pageSize, pageNum, userList);
		ModelAndView modelAndView=new ModelAndView("admin_user_view");
		modelAndView.addObject("pageDTO",pageDTO);
		return modelAndView;
	}

}

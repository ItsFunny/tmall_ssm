package com.tmall.service;

import java.util.List;

import com.tmall.dto.UserDTO;

public interface UserService
{

	List<UserDTO> findAllUsers();
}

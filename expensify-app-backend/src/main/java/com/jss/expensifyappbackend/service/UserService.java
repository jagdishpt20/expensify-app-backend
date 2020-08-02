package com.jss.expensifyappbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jss.expensifyappbackend.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	
	void deleteUser(String userId);
}

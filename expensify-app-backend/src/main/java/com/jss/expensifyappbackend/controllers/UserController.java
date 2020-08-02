package com.jss.expensifyappbackend.controllers;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.expensifyappbackend.service.UserService;
import com.jss.expensifyappbackend.shared.dto.UserDto;
import com.jss.expensifyappbackend.ui.model.request.UserDetailsRequestModel;
import com.jss.expensifyappbackend.ui.model.response.UserRest;
import com.jss.expensifyappbackend.utility.Roles;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
	@GetMapping(path = "/getUserByUserId/{userId}",  consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserRest getUser(@PathVariable String userId) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(userId);

		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@PostMapping(value = "create")
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));

		UserDto createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.userId")
	//	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteByUserId/{userId}",  consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void removeUser(@PathVariable String userId) {
		userService.deleteUser(userId);		
	}
}

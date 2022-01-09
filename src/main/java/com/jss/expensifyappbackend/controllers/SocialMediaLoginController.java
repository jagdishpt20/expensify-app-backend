package com.jss.expensifyappbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.expensifyappbackend.entity.UserDetails;
import com.jss.expensifyappbackend.service.UserDetailsService;

@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
public class SocialMediaLoginController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping(value="/login/social-media-data")
	public UserDetails saveUserDetails(UserDetails userDetails) {
		return userDetailsService.saveUserDetails(userDetails); 
	}
}

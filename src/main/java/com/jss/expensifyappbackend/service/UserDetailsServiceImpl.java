package com.jss.expensifyappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.expensifyappbackend.entity.UserDetails;
import com.jss.expensifyappbackend.repository.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailsRepository userdetailsRepo;
	
	@Transactional
	@Override
	public UserDetails saveUserDetails(UserDetails userDetails) {
		return userdetailsRepo.save(userDetails);
	}
}

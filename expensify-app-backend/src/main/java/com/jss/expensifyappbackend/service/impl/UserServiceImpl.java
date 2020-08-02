package com.jss.expensifyappbackend.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.expensifyappbackend.io.entity.Role;
import com.jss.expensifyappbackend.io.entity.User;
import com.jss.expensifyappbackend.repository.RoleRepository;
import com.jss.expensifyappbackend.repository.UserRepository;
import com.jss.expensifyappbackend.service.UserService;
import com.jss.expensifyappbackend.service.security.UserPrincipal;
import com.jss.expensifyappbackend.shared.dto.UserDto;
import com.jss.expensifyappbackend.utility.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userRepository.findByEmail(userDto.getEmail()) != null) {
			throw new RuntimeException("User already exists");
		}

		User user = new User();

		BeanUtils.copyProperties(userDto, user);

		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		String userId = utils.generateUserId(30);

		user.setUserId(userId);
		
		Collection<Role> roles = new HashSet<>();
		
		for(String role: userDto.getRoles()) {
			Role roleEntity = roleRepository.findByName(role);
			
			if(roleEntity != null) {
				roles.add(roleEntity);
			}			
		}
		
		user.setRoles(roles);

		User storedUser = userRepository.save(user);

		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(storedUser, returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserPrincipal(user);
		
		/*return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(),
				new ArrayList<>());*/
	}

	@Override
	public UserDto getUser(String email) {
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(user, userDto);
		
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto userDto = new UserDto();
		
		User user = userRepository.findByUserId(userId);
		
		if(user == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		BeanUtils.copyProperties(user, userDto);
		
		return userDto;
	}

	@Transactional
	@Override
	public void deleteUser(String userId) {
		userRepository.deleteByUserId(userId);
	}
}

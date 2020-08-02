package com.jss.expensifyappbackend;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jss.expensifyappbackend.io.entity.Authority;
import com.jss.expensifyappbackend.io.entity.Role;
import com.jss.expensifyappbackend.io.entity.User;
import com.jss.expensifyappbackend.repository.AuthorityRepository;
import com.jss.expensifyappbackend.repository.RoleRepository;
import com.jss.expensifyappbackend.repository.UserRepository;
import com.jss.expensifyappbackend.utility.Roles;
import com.jss.expensifyappbackend.utility.Utils;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils utils;
	
	@Autowired
	UserRepository userRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent readyEvent) {
		Authority readAuthority = createAuthority("READ_AUTHORITY");

		Authority writeAuthority = createAuthority("WRITE_AUTHORITY");

		Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

		createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));

		Role roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		
		if(roleAdmin == null) return;
		
		User user = new User();
		
		user.setFirstName("Rambabu");
		user.setLastName("Verma");
		user.setEmail("ramu@gmail.com");
		user.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
		user.setEmailVerificationStatus(true);
		user.setUserId(utils.generateUserId(30));
		user.setRoles(Arrays.asList(roleAdmin));
		
		if(userRepository.findByEmail(user.getEmail()) == null) {
			userRepository.save(user);
		}		
	}

	@Transactional
	private Authority createAuthority(String name) {
		Authority authority = authorityRepository.findByName(name);

		if (authority == null) {
			authority = new Authority(name);
			authority = authorityRepository.save(authority);
		}

		return authority;
	}

	@Transactional
	private Role createRole(String name, Collection<Authority> authorities) {
		Role role = roleRepository.findByName(name);

		if (role == null) {
			role = new Role(name, authorities);
			role = roleRepository.save(role);
		}

		return role;
	}
}

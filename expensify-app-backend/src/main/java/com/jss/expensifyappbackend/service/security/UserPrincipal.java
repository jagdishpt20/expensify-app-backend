package com.jss.expensifyappbackend.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jss.expensifyappbackend.io.entity.Authority;
import com.jss.expensifyappbackend.io.entity.Role;
import com.jss.expensifyappbackend.io.entity.User;

public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1500828966997390989L;
	
	private User user;
	
	private String userId;

	public UserPrincipal(User user) {
		this.user = user;
		this.userId = user.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<>();
		
		Collection<Authority> authorityEntities = new HashSet<>();
		
		Collection<Role> roles = user.getRoles();
		
		if(roles == null) {
			return authorities;
		}
		
		roles.forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			authorityEntities.addAll(role.getAuthorities());
		});
		
		authorityEntities.forEach((authorityEntity) -> {
			authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
		});
		
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}

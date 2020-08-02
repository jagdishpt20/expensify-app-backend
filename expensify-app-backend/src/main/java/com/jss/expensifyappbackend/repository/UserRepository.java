package com.jss.expensifyappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jss.expensifyappbackend.io.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
	User findByUserId(String userId);
	void deleteByUserId(String userId);
}

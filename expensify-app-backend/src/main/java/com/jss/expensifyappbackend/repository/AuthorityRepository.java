package com.jss.expensifyappbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jss.expensifyappbackend.io.entity.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	Authority findByName(String name);
}

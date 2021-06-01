package com.elderpereira.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elderpereira.rest.data.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
}

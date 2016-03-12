package com.semicolon.centaurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semicolon.centaurs.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
}

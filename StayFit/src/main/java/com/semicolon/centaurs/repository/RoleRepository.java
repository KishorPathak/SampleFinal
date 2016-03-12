package com.semicolon.centaurs.repository;

import com.semicolon.centaurs.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

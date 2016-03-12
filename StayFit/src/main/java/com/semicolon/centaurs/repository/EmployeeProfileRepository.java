package com.semicolon.centaurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semicolon.centaurs.model.EmployeeProfile;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long>  {

}

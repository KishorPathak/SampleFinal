package com.semicolon.centaurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semicolon.centaurs.model.EmployeeMaster;


public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, Long>  {
	EmployeeMaster findByEmployeeEmail(String empEmail);
}

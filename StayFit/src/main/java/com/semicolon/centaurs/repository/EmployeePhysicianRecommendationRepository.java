package com.semicolon.centaurs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.semicolon.centaurs.model.EmployeePhysicianRecommendation;

public interface EmployeePhysicianRecommendationRepository extends JpaRepository<EmployeePhysicianRecommendation, Long>  {
	List<EmployeePhysicianRecommendation> findByEmpIdOrderByEmpPhyRecommendIdDesc(Long empId);
}

package com.semicolon.centaurs.service;

import com.semicolon.centaurs.valueobjects.EmployeeReccomendationResponseVO;

public interface EmployeeReccomendationService {
	EmployeeReccomendationResponseVO getLatestRecommendationForEmployee(String empEmail);
}

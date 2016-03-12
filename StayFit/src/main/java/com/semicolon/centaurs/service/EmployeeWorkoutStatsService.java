package com.semicolon.centaurs.service;

import java.util.List;

import com.semicolon.centaurs.valueobjects.FitnessDataRequestVO;

public interface EmployeeWorkoutStatsService {
	void saveEmployeeWorkoutStats(FitnessDataRequestVO fitnessDataRequestVO);
	List<Object[]> getZones(String from,String to);
	List<Object[]> getDailyZones(String from,String to);
	List<Object[]> getDailyCalories(String from,String to,int empId);
	List<Object[]> getEmpData(String from,String to,String zone);
}

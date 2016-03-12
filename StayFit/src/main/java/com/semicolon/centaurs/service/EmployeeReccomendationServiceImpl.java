package com.semicolon.centaurs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semicolon.centaurs.model.EmployeeMaster;
import com.semicolon.centaurs.model.EmployeePhysicianRecommendation;
import com.semicolon.centaurs.repository.EmployeeMasterRepository;
import com.semicolon.centaurs.repository.EmployeePhysicianRecommendationRepository;
import com.semicolon.centaurs.valueobjects.EmployeeReccomendationResponseVO;

@Service
public class EmployeeReccomendationServiceImpl implements EmployeeReccomendationService {

	@Autowired
	private EmployeePhysicianRecommendationRepository employeePhysicianRecommendationRepository;

	@Autowired
	private EmployeeMasterRepository employeeMasterRepository;

	public EmployeeReccomendationResponseVO getLatestRecommendationForEmployee(String empEmail) {
		EmployeeReccomendationResponseVO employeeReccomendationResponseVO = new EmployeeReccomendationResponseVO();
		EmployeeMaster employeeMaster = employeeMasterRepository.findByEmployeeEmail(empEmail);
		if (null != employeeMaster) {
			List<EmployeePhysicianRecommendation> recommendedList = employeePhysicianRecommendationRepository.findByEmpIdOrderByEmpPhyRecommendIdDesc(employeeMaster.getEmpId());
			if (null != recommendedList && !recommendedList.isEmpty()) {
				EmployeePhysicianRecommendation physicianRecommnededData = recommendedList.get(0);
				employeeReccomendationResponseVO.setEmpEmail(empEmail);
				employeeReccomendationResponseVO.setEmpId(physicianRecommnededData.getEmpId());
				employeeReccomendationResponseVO.setCalories(physicianRecommnededData.getCalories());
				employeeReccomendationResponseVO.setCyclingInMin(physicianRecommnededData.getCyclingInMin());
				employeeReccomendationResponseVO.setDistanceInMtr(physicianRecommnededData.getDistanceInMtr());
				employeeReccomendationResponseVO.setStepsCount(physicianRecommnededData.getStepsCount());
				employeeReccomendationResponseVO.setRunningInMin(physicianRecommnededData.getRunningInMin());
			}
			return employeeReccomendationResponseVO;
		}
		return null;
	}
}

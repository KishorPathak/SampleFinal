package com.semicolon.centaurs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.semicolon.centaurs.model.EmployeeMaster;
import com.semicolon.centaurs.model.EmployeePhysicianRecommendation;
import com.semicolon.centaurs.model.EmployeeWorkoutStats;
import com.semicolon.centaurs.model.ZoneMaster;
import com.semicolon.centaurs.repository.EmployeeMasterRepository;
import com.semicolon.centaurs.repository.EmployeePhysicianRecommendationRepository;
import com.semicolon.centaurs.repository.EmployeeWorkoutsStatsRepository;
import com.semicolon.centaurs.repository.ZoneMasterRepository;
import com.semicolon.centaurs.util.DateUtil;
import com.semicolon.centaurs.util.MessageConstants;
import com.semicolon.centaurs.valueobjects.FitAppData;
import com.semicolon.centaurs.valueobjects.FitnessDataRequestVO;

@Service
public class EmployeeWorkoutStatsServiceImpl implements EmployeeWorkoutStatsService {

	@Autowired
	private EmployeeWorkoutsStatsRepository employeeWorkoutsStatsRepository;

	@Autowired
	private EmployeeMasterRepository employeeMasterRepository;

	@Autowired
	private EmployeePhysicianRecommendationRepository employeePhysicianRecommendationRepository;

	@Autowired
	private ZoneMasterRepository zoneMasterRepository;

	public List<Object[]> getZones(String from,String to) {
		
		 List<Object[]> list= employeeWorkoutsStatsRepository.getZones(from,to); 
		if(list.size()==0)
			 System.out.println(list);
		 return list;
	}
	
	public List<Object[]> getDailyZones(String from,String to) {
		
		 List<Object[]> list=employeeWorkoutsStatsRepository.getDailyZones(from,to); 
		if(list.size()==0)
			 System.out.println(list);
		 return list;
	}
	
	public List<Object[]> getDailyCalories(String from,String to,int empId) {
		
		List<Object[]> list= employeeWorkoutsStatsRepository.getDailyCalories(from,to,empId); 
		if(list.size()==0)
			 System.out.println(list);
		 return list;
	}
	
	public List<Object[]> getEmpData(String from,String to,String zone)
	{
		List<Object[]> list= employeeWorkoutsStatsRepository.getEmpData(from,to,zone); 
		if(list.size()==0)
			 System.out.println(list);
		 return list;
	}
	
	
	@Override
	public void saveEmployeeWorkoutStats(FitnessDataRequestVO fitnessDataRequestVO) {
		List<EmployeeWorkoutStats> employeeWorkoutStatsList = new ArrayList<EmployeeWorkoutStats>();
		EmployeeWorkoutStats employeeWorkoutStats = new EmployeeWorkoutStats();
		List<EmployeePhysicianRecommendation> employeePhysicianRecommendationList = new ArrayList<EmployeePhysicianRecommendation>();

		if (null != fitnessDataRequestVO) {
			EmployeeMaster employeeMaster = employeeMasterRepository.findByEmployeeEmail(fitnessDataRequestVO.getEmployeeEmail());
			if (null != employeeMaster) {
				Long employeeId = employeeMaster.getEmpId();
				employeeWorkoutStats.setEmpId(employeeId);
				List<FitAppData> fitAppDataList = fitnessDataRequestVO.getFitAppData();
				for (FitAppData fitAppData : fitAppDataList) {
					employeeWorkoutStats.setCreatedDate(Calendar.getInstance().getTime());
					employeeWorkoutStats.setStartDate(DateUtil.formStringToDate(fitAppData.getStartDate()));
					employeeWorkoutStats.setEndDate(DateUtil.formStringToDate(fitAppData.getEndDate()));
					if (null != fitAppData.getDataType() && !fitAppData.getDataType().isEmpty() && fitAppData.getDataType().equalsIgnoreCase(MessageConstants.TOTAL_CALORIES)) {
						employeeWorkoutStats.setCalories(Float.valueOf(fitAppData.getFieldValues().getCalories()));
					} else if (null != fitAppData.getDataType() && !fitAppData.getDataType().isEmpty() && fitAppData.getDataType().equalsIgnoreCase(MessageConstants.STEP_COUNT)) {
						employeeWorkoutStats.setStepsCount(Long.valueOf(fitAppData.getFieldValues().getSteps()));
					} else if (null != fitAppData.getDataType() && !fitAppData.getDataType().isEmpty() && fitAppData.getDataType().equalsIgnoreCase(MessageConstants.DISNTANCE_IN_METERS)) {
						employeeWorkoutStats.setDistanceInMtr(Float.valueOf(fitAppData.getFieldValues().getDistance()));
					} else if (null != fitAppData.getDataType() && !fitAppData.getDataType().isEmpty() && fitAppData.getDataType().equalsIgnoreCase(MessageConstants.CYCLING_SPEED)) {
						employeeWorkoutStats.setCyclingInMin((Float.valueOf(fitAppData.getFieldValues().getAverage())));
					}

					employeeWorkoutStatsList.add(employeeWorkoutStats);
				}
				
				employeePhysicianRecommendationList = employeePhysicianRecommendationRepository.findByEmpIdOrderByEmpPhyRecommendIdDesc(employeeId);
				if (null != employeePhysicianRecommendationList && !employeePhysicianRecommendationList.isEmpty()) {
					Long totalRecommendeddistanceInMtr = employeePhysicianRecommendationList.get(0).getDistanceInMtr();
					Long totalRecommendedStepsCount = employeePhysicianRecommendationList.get(0).getStepsCount();
					Float totalRecommendedCalories = employeePhysicianRecommendationList.get(0).getCalories();
					Long totalRecommendedcyclingInMin = employeePhysicianRecommendationList.get(0).getCyclingInMin();

					Long totalStepCountFromDevice = employeeWorkoutStats.getStepsCount();
					if (null != totalStepCountFromDevice) {
						Float stepsAdherence = ((totalStepCountFromDevice * 100f) / totalRecommendedStepsCount);
						employeeWorkoutStats.setStepsAdherence(stepsAdherence);
					}

					Float totalDistanceFromDevice = employeeWorkoutStats.getDistanceInMtr();
					if (null != totalDistanceFromDevice) {
						Float distanceAdherence = ((totalDistanceFromDevice * 100f) / totalRecommendeddistanceInMtr);
						employeeWorkoutStats.setDistanceAdherence(distanceAdherence);
					}

					Float totalCyclingFromDevice = employeeWorkoutStats.getCyclingInMin();
					if (null != totalCyclingFromDevice) {
						Float cyclingAdherence = ((totalCyclingFromDevice * 100f) / totalRecommendedcyclingInMin);
						employeeWorkoutStats.setCyclingAdherence(cyclingAdherence);
					}

					Float totalCaloriesFromDevice = employeeWorkoutStats.getCalories();
					if (null != totalCyclingFromDevice) {
						Float overAllAdherence = ((totalCaloriesFromDevice * 100f) / totalRecommendedCalories);
						employeeWorkoutStats.setClaoriesAdherence(overAllAdherence);
						employeeWorkoutStats.setOverallAdherence(overAllAdherence);

						List<ZoneMaster> zoneMasterList = zoneMasterRepository.findAll();

						if (null != zoneMasterList && !zoneMasterList.isEmpty()) {
							for (ZoneMaster zoneMaster : zoneMasterList) {
								Float zoneStartValue = zoneMaster.getZoneStart();
								Float zoneEndValue = zoneMaster.getZoneEnd();
								String zoneDescValue = zoneMaster.getZoneDesc();
								if (overAllAdherence >= zoneStartValue && overAllAdherence <= zoneEndValue) {
									employeeWorkoutStats.setZoneDescription(zoneDescValue);
								}
							}
						}
					}
					
				}

				employeeWorkoutsStatsRepository.save(employeeWorkoutStats);

			}
		}
	}

}

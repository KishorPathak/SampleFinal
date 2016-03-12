package com.semicolon.centaurs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semicolon.centaurs.model.DailyCalories;
import com.semicolon.centaurs.model.DailyWorkOutZones;
import com.semicolon.centaurs.model.EmployeeZoneData;
import com.semicolon.centaurs.model.WorkoutZones;
import com.semicolon.centaurs.service.EmployeeWorkoutStatsService;
import com.semicolon.centaurs.valueobjects.FitnessDataRequestVO;

@CrossOrigin
@Controller
public class EmployeeWorkoutStatsController {
	
	private static final String APPLICATION_JSON = "application/json";
	
	@Autowired
    private EmployeeWorkoutStatsService employeeWorkoutStatsService;

    @ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/employeeWorkoutStats", method = RequestMethod.POST, consumes = APPLICATION_JSON)
    public void saveEmployeeWorkoutStats(@RequestBody final FitnessDataRequestVO fitnessDataRequestVO) {
    	employeeWorkoutStatsService.saveEmployeeWorkoutStats(fitnessDataRequestVO);
    }
    
    @RequestMapping(value = {"/dailyWorkoutZones"},  method = RequestMethod.GET, produces=APPLICATION_JSON)   
    public @ResponseBody List<DailyWorkOutZones> getDailyZone(@RequestParam("from") String from,@RequestParam("to") String to) {
    	
    	List<Object[]> list=employeeWorkoutStatsService.getDailyZones(from, to);
    	System.out.println(list);
    	List<DailyWorkOutZones> list2 = new ArrayList<DailyWorkOutZones>();
    	
    	list.forEach(object -> {
    		DailyWorkOutZones workoutZone = new DailyWorkOutZones();
    		workoutZone.setDate(object[0].toString());
			workoutZone.setName(object[1].toString());
			workoutZone.setCount(object[2].toString());
			list2.add(workoutZone);
    	});
    	return list2;
   }
   	   	    
    @RequestMapping(value = {"/dailyCalories"},  method = RequestMethod.GET, produces = APPLICATION_JSON)
    public @ResponseBody List<DailyCalories> getDailyCalories(@RequestParam("from") String from,@RequestParam("to") String to, @RequestParam("empId") int empId) {
    	System.out.println(from+" "+to+" "+empId);
    	List<Object[]> list=employeeWorkoutStatsService.getDailyCalories(from, to, empId);
    	System.out.println(list);
    	List<DailyCalories> list2 = new ArrayList<DailyCalories>();
    	
    	list.forEach(object -> {
    		DailyCalories calories = new DailyCalories();
    		calories.setDate(object[0].toString());    		
    		calories.setCalories(Double.parseDouble(object[1].toString()));
    		calories.setZone(object[2].toString());
			list2.add(calories);
    	});
    	return list2;
	}
    
   	   	
    @RequestMapping(value = {"/workoutZones"},  method = RequestMethod.GET)
    public @ResponseBody List<WorkoutZones> getZones(@RequestParam("from") String from,@RequestParam("to") String to) {
    	
    	List<Object[]> list=employeeWorkoutStatsService.getZones(from,to);
    	System.out.println(list);
    	List<WorkoutZones> list2 = new ArrayList<WorkoutZones>();
    	    	
    	list.forEach(object -> {
    		WorkoutZones workoutZone = new WorkoutZones();
			workoutZone.setName(object[0].toString());
			workoutZone.setY(object[1].toString());
			list2.add(workoutZone);
    	});
    	return list2;
	}
    
    @RequestMapping(value = {"/zonedata"},  method = RequestMethod.GET)
    public @ResponseBody List<EmployeeZoneData> getEmpData(@RequestParam("from") String from,@RequestParam("to") String to,@RequestParam("zone") String zone) {
    	
    	List<Object[]> list=employeeWorkoutStatsService.getEmpData(from,to,zone);
    	System.out.println(list);
    	List<EmployeeZoneData> list2 = new ArrayList<EmployeeZoneData>();
    	    	
    	list.forEach(object -> {
    		EmployeeZoneData empZoneData = new EmployeeZoneData(Long.valueOf(object[0].toString()),object[1].toString(),Float.valueOf(object[2].toString()));
			list2.add(empZoneData);
    	});
    	return list2;
	}
	
}

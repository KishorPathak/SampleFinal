package com.semicolon.centaurs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semicolon.centaurs.service.EmployeeReccomendationService;
import com.semicolon.centaurs.valueobjects.EmployeeReccomendationResponseVO;

@Controller
public class EmployeeRecommendationController {
    @Autowired
    private EmployeeReccomendationService employeeReccomendationService;

    @ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/recommendedData/{empEmail}/", method = RequestMethod.GET)
    public EmployeeReccomendationResponseVO getLatestRecommendationForEmployee(@PathVariable("empEmail") final String empEmail) {
    	EmployeeReccomendationResponseVO employeeReccomendationResponseVO = employeeReccomendationService.getLatestRecommendationForEmployee(empEmail);
		return employeeReccomendationResponseVO;
    }
    
}
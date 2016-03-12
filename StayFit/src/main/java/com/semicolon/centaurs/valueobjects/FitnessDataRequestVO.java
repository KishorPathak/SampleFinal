package com.semicolon.centaurs.valueobjects;

import java.util.List;

public class FitnessDataRequestVO {
	private List<FitAppData> fitAppData;
	private String employeeEmail;

	public List<FitAppData> getFitAppData() {
		return fitAppData;
	}

	public void setFitAppData(List<FitAppData> fitAppData) {
		this.fitAppData = fitAppData;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

}

package com.semicolon.centaurs.model;

public class EmployeeZoneData {
	
	private Long empId;
	private String employeeName;
	private Float calories;
	public EmployeeZoneData(Long empId, String employeeName, Float calories) {
		super();
		this.empId = empId;
		this.employeeName = employeeName;
		this.calories = calories;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Float getCalories() {
		return calories;
	}
	public void setCalories(Float calories) {
		this.calories = calories;
	}

}

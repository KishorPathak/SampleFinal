package com.semicolon.centaurs.valueobjects;

/**
 * @author kishor_pathak
 *
 */
public class EmployeeReccomendationResponseVO {
	private String empEmail;
	private Long empId;
	private Long runningInMin;
	private Long cyclingInMin;
	private Long stepsCount;
	private Long distanceInMtr;
	private Float calories;
	
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	public Long getRunningInMin() {
		return runningInMin;
	}
	public void setRunningInMin(Long runningInMin) {
		this.runningInMin = runningInMin;
	}
	public Long getCyclingInMin() {
		return cyclingInMin;
	}
	public void setCyclingInMin(Long cyclingInMin) {
		this.cyclingInMin = cyclingInMin;
	}
	public Long getStepsCount() {
		return stepsCount;
	}
	public void setStepsCount(Long stepsCount) {
		this.stepsCount = stepsCount;
	}
	public Long getDistanceInMtr() {
		return distanceInMtr;
	}
	public void setDistanceInMtr(Long distanceInMtr) {
		this.distanceInMtr = distanceInMtr;
	}
	public Float getCalories() {
		return calories;
	}
	public void setCalories(Float calories) {
		this.calories = calories;
	}

	
}

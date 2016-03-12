package com.semicolon.centaurs.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kishor_pathak
 *
 */
@Entity
@Table(name = "emp_workout_stats")
public class EmployeeWorkoutStats {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_workout_stats_id")
	private Long employeeWorkoutsStatsId;
	
	@Column(name="emp_id")
    private Long empId;
    
	@Column(name="running_min")
    private Float runningInMin;
	
	@Column(name="cycling_min")
	private Float cyclingInMin;
	
	@Column(name="steps_cnt")
	private Long stepsCount;
	
	@Column(name="distinct_mtr")
	private Float distanceInMtr;
	
	@Column(name="calories")
	private Float calories;
	
	@Column(name="running_adh")
	private Float runningAdherence;
	
	@Column(name="cycling_adh")
	private Float cyclingAdherence;
	
	@Column(name="steps_adh")
	private Float stepsAdherence;
	
	@Column(name="distance_adh")
	private Float distanceAdherence;
	
	@Column(name="calories_adh")
	private Float claoriesAdherence;
	
	@Column(name="overall_adh")
	private Float overallAdherence;
	
	@Column(name="date_created", insertable=false, updatable=false)
	private Date createdDate;
	
	@Column(name="from_period")
	private Date startDate;
	
	@Column(name="to_period")
	private Date endDate;
	
	@Column(name="zone_desc")
	private String zoneDescription;
    
	public Long getEmployeeWorkoutsStatsId() {
		return employeeWorkoutsStatsId;
	}
	public void setEmployeeWorkoutsStatsId(Long employeeWorkoutsStatsId) {
		this.employeeWorkoutsStatsId = employeeWorkoutsStatsId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Float getRunningInMin() {
		return runningInMin;
	}
	public void setRunningInMin(Float runningInMin) {
		this.runningInMin = runningInMin;
	}
	public Float getCyclingInMin() {
		return cyclingInMin;
	}
	public void setCyclingInMin(Float cyclingInMin) {
		this.cyclingInMin = cyclingInMin;
	}
	public Long getStepsCount() {
		return stepsCount;
	}
	public void setStepsCount(Long stepsCount) {
		this.stepsCount = stepsCount;
	}
	
	public Float getCalories() {
		return calories;
	}
	public void setCalories(Float calories) {
		this.calories = calories;
	}
	public Float getRunningAdherence() {
		return runningAdherence;
	}
	public void setRunningAdherence(Float runningAdherence) {
		this.runningAdherence = runningAdherence;
	}
	public Float getCyclingAdherence() {
		return cyclingAdherence;
	}
	public void setCyclingAdherence(Float cyclingAdherence) {
		this.cyclingAdherence = cyclingAdherence;
	}
	public Float getStepsAdherence() {
		return stepsAdherence;
	}
	public void setStepsAdherence(Float stepsAdherence) {
		this.stepsAdherence = stepsAdherence;
	}
	public Float getDistanceAdherence() {
		return distanceAdherence;
	}
	public void setDistanceAdherence(Float distanceAdherence) {
		this.distanceAdherence = distanceAdherence;
	}
	public Float getClaoriesAdherence() {
		return claoriesAdherence;
	}
	public void setClaoriesAdherence(Float claoriesAdherence) {
		this.claoriesAdherence = claoriesAdherence;
	}
	public Float getDistanceInMtr() {
		return distanceInMtr;
	}
	public void setDistanceInMtr(Float distanceInMtr) {
		this.distanceInMtr = distanceInMtr;
	}
	public Float getOverallAdherence() {
		return overallAdherence;
	}
	public void setOverallAdherence(Float overallAdherence) {
		this.overallAdherence = overallAdherence;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getZoneDescription() {
		return zoneDescription;
	}
	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}
}

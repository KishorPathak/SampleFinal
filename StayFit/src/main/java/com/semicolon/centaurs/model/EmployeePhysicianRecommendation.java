package com.semicolon.centaurs.model;


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
@Table(name = "emp_physician_recommendation")
public class EmployeePhysicianRecommendation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_phy_recommend_id")
    private Long empPhyRecommendId;
    
	@Column(name="emp_id")
	private Long empId;
	 
	@Column(name="emp_profile_id")
	private Long empProfileId;
	
	@Column(name="running_min")
	private Long runningInMin;
	
	@Column(name="cycling_min")
	private Long cyclingInMin;
	
	@Column(name="steps_cnt")
	private Long stepsCount;
	
	@Column(name="distinct_mtr")
	private Long distanceInMtr;
	
	@Column(name="calories")
	private Float calories;

	public Long getEmpPhyRecommendId() {
		return empPhyRecommendId;
	}

	public void setEmpPhyRecommendId(Long empPhyRecommendId) {
		this.empPhyRecommendId = empPhyRecommendId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getEmpProfileId() {
		return empProfileId;
	}

	public void setEmpProfileId(Long empProfileId) {
		this.empProfileId = empProfileId;
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

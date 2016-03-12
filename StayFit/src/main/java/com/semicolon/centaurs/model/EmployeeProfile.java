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
@Table(name = "emp_profile")
public class EmployeeProfile {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_profile_id")
	private Long employeeProfileId;
	
	@Column(name="emp_id")
    private Long empId;
    
	@Column(name="emp_profile_date")
	private Date empProfileDate;
	
	@Column(name="weight")
	private Float weight;
	
	@Column(name="height")
	private Float height;
	
	@Column(name="bp")
	private Long bp;
	
	@Column(name="sugar")
	private Long sugar;
	
	@Column(name="heart_rate")
	private Long heartRate;

	public Long getEmployeeProfileId() {
		return employeeProfileId;
	}

	public void setEmployeeProfileId(Long employeeProfileId) {
		this.employeeProfileId = employeeProfileId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getEmpProfileDate() {
		return empProfileDate;
	}

	public void setEmpProfileDate(Date empProfileDate) {
		this.empProfileDate = empProfileDate;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Long getBp() {
		return bp;
	}

	public void setBp(Long bp) {
		this.bp = bp;
	}

	public Long getSugar() {
		return sugar;
	}

	public void setSugar(Long sugar) {
		this.sugar = sugar;
	}

	public Long getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Long heartRate) {
		this.heartRate = heartRate;
	}
	
}

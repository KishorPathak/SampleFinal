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
@Table(name = "emp_mst")
public class EmployeeMaster {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="emp_id")
    private Long empId;
    
	@Column(name="emp_name")
	private String employeeName;
	
	@Column(name="emp_bu")
	private String employeeBu;
	
	@Column(name="emp_dob")
	private Date employeeDateOfBirth;
	
	@Column(name="email_id")
	private String employeeEmail;

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

	public String getEmployeeBu() {
		return employeeBu;
	}

	public void setEmployeeBu(String employeeBu) {
		this.employeeBu = employeeBu;
	}

	public Date getEmployeeDateOfBirth() {
		return employeeDateOfBirth;
	}

	public void setEmployeeDateOfBirth(Date employeeDateOfBirth) {
		this.employeeDateOfBirth = employeeDateOfBirth;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	
	
}

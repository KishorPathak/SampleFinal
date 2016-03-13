package com.semicolon.centaurs.service;


import com.semicolon.centaurs.valueobjects.AppUserRegisterRequestVO;

public interface EmployeeMasterService {
	void saveEmployeeMaster(AppUserRegisterRequestVO appUserRegisterRequestVO);
	Long getEmployeeIdByEmail(String userName);
}

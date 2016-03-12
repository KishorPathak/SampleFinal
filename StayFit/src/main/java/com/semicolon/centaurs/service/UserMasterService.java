package com.semicolon.centaurs.service;


import org.springframework.stereotype.Service;

import com.semicolon.centaurs.valueobjects.AppUserRegisterRequestVO;


public interface UserMasterService {
	void saveEmployeeMaster(AppUserRegisterRequestVO appUserRegisterRequestVO);
}

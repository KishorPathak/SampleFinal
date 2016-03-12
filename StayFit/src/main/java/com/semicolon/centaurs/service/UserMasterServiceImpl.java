package com.semicolon.centaurs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semicolon.centaurs.model.EmployeeMaster;
import com.semicolon.centaurs.repository.EmployeeMasterRepository;
import com.semicolon.centaurs.valueobjects.AppUserRegisterRequestVO;

@Service
public class UserMasterServiceImpl implements UserMasterService {
	
	@Autowired
	private EmployeeMasterRepository EmployeeMasterRepository;

	@Override
	public void saveEmployeeMaster(AppUserRegisterRequestVO appUserRegisterRequestVO) {
		// TODO Auto-generated method stub
		EmployeeMaster employeeMaster = new EmployeeMaster();
		employeeMaster.setEmployeeBu(appUserRegisterRequestVO.getBu());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date  = null;
		try {
			date = simpleDateFormat.parse(appUserRegisterRequestVO.getDob());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employeeMaster.setEmployeeDateOfBirth(date);
		employeeMaster.setEmployeeEmail(appUserRegisterRequestVO.getEmail());
		
		employeeMaster.setEmployeeName(appUserRegisterRequestVO.getFull_name());
		EmployeeMasterRepository.save(employeeMaster);
	}

}

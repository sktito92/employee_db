package com.employee.codetest.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.employee.codetest.config.ApplicationConfig;
import com.employee.codetest.model.DBFileData;
import com.employee.codetest.model.EmployeeDto;
import com.employee.codetest.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public EmployeeDto insertEmployee(EmployeeDto newEmpRequest) {
		EmployeeDto response = new EmployeeDto();
		try {
			File f = new File(ApplicationConfig.DB_FILEPATH);
			DBFileData dbData = new DBFileData();
			Long newEmployeeId = 1L;
			if(f.exists() && !f.isDirectory()) {
				dbData = getExistingEmployeesFromDb(dbData);
				if(dbData != null) {
					List<EmployeeDto> empList = dbData.getEmployees();
					newEmployeeId += empList.get(empList.size() -1).getId();
					newEmpRequest.setId(newEmployeeId);
					empList.add(newEmpRequest);
				}
			} else {
				newEmpRequest.setId(newEmployeeId);
				dbData.setEmployees(Arrays.asList(newEmpRequest));						
			}
			writeEmployeeToDb(dbData);
			response.setId(newEmployeeId);
		} catch (Exception ex) {
			LOGGER.error("exception occured insert employee = {}", ex);
		}
		return response;
	}	

	@Override
	public EmployeeDto getEmployeeById(Long id) {
		DBFileData dbData = new DBFileData();
		dbData = getExistingEmployeesFromDb(dbData);
		if(dbData != null) {
			return dbData.getEmployees().stream()
					.filter(e -> e.getId().equals(id))
					.collect(Collectors.toList())
					.get(0);
		} else {
			return new EmployeeDto();
		}
	}
	
	@Override
	public List<EmployeeDto> getEmployees(String name, String fromSalary, String toSalary) {
		DBFileData dbData = new DBFileData();
		dbData = getExistingEmployeesFromDb(dbData);
		if(dbData != null) {
			if(StringUtils.isNotBlank(name)) {
				return dbData.getEmployees().stream()
						.filter(e -> e.getFirstName().toLowerCase().contains(name.toLowerCase()) 
								|| e.getLastName().toLowerCase().contains(name.toLowerCase()))
						.collect(Collectors.toList());
			} else if(StringUtils.isNotBlank(fromSalary) && StringUtils.isBlank(toSalary)){
				return dbData.getEmployees().stream()
						.filter(e -> StringUtils.isNotBlank(e.getSalary()) && Double.valueOf(e.getSalary()) >= Double.valueOf(fromSalary))
						.collect(Collectors.toList());
			} else if(StringUtils.isBlank(fromSalary) && StringUtils.isNotBlank(toSalary)){
				return dbData.getEmployees().stream()
						.filter(e -> StringUtils.isNotBlank(e.getSalary()) && Double.valueOf(e.getSalary()) <= Double.valueOf(toSalary))
						.collect(Collectors.toList());
			} else if(StringUtils.isNotBlank(fromSalary) && StringUtils.isNotBlank(toSalary)){
				return dbData.getEmployees().stream()
						.filter(e -> StringUtils.isNotBlank(e.getSalary()) && Double.valueOf(e.getSalary()) >= Double.valueOf(fromSalary) && Double.valueOf(e.getSalary()) <= Double.valueOf(toSalary))
						.collect(Collectors.toList());
			}
		}
		return null;		
	}


	private DBFileData getExistingEmployeesFromDb(DBFileData dbData) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			dbData = mapper.readValue(new File(ApplicationConfig.DB_FILEPATH), DBFileData.class);
		} catch (IOException e) {
			LOGGER.error("exception occured getExistingEmployeesFromDb = {}", e);
		}
		return dbData;
	}

	public void writeEmployeeToDb(DBFileData dbData) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(ApplicationConfig.DB_FILEPATH), dbData);
		} catch (Exception e) {
			LOGGER.error("exception occured writeEmployeeToDb = {}", e);
		}		
	}



}

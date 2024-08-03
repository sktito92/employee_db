package com.employee.codetest.service;

import java.util.List;

import com.employee.codetest.model.EmployeeDto;

public interface EmployeeService {

	EmployeeDto insertEmployee(EmployeeDto request);

	EmployeeDto getEmployeeById(Long id);

	List<EmployeeDto> getEmployees(String name, String fromSalary, String toSalary);
}

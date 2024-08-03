package com.employee.codetest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.codetest.model.EmployeeDto;
import com.employee.codetest.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping(value = "/employees")
	public EmployeeDto createEmployee(HttpServletRequest httpServletRequest,
			@RequestBody EmployeeDto request) {		
		EmployeeDto response = employeeService.insertEmployee(request);
		return response;		
	}
		
	@GetMapping(value = "/employees/{id}")
	public EmployeeDto getEmployeeById(HttpServletRequest httpServletRequest,
			@PathVariable Long id) {		
		EmployeeDto response = employeeService.getEmployeeById(id);
		return response;		
	}
	
	@GetMapping(value = "/employees")
	public List<EmployeeDto> getEmployees(HttpServletRequest httpServletRequest,
			@RequestParam(name="name", required=false) String name,
			@RequestParam(name="fromSalary", required=false) String fromSalary,
			@RequestParam(name="toSalary", required=false) String toSalary) {		
		List<EmployeeDto> response = employeeService.getEmployees(name, fromSalary, toSalary);
		return response;		
	}
}

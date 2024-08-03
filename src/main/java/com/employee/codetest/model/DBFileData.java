package com.employee.codetest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DBFileData {
	
	private List<EmployeeDto> employees;

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "DBFileData [employees=" + employees + "]";
	}
	
}

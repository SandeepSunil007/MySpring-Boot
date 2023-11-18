package com.crudcode.service;

import java.util.List;
import java.util.Map;

import com.crudcode.entity.Employee;
import com.crudcode.request.EmployeeRequest;
import com.crudcode.response.EmployeeResponse;

public interface EmployeeService {

	// Add
	Employee addEmployee(EmployeeRequest employeeRequest);

	// Delete
	Employee deleteEmployee(Integer id);

	// Get Data By ID
	Employee getDataById(Integer empId);

	// Get All Data
	List<EmployeeResponse> getAllDataFromDB();

	// update
	Employee updateEmployeeDetails(EmployeeRequest employeeRequest);

	// patch -- way 1
	Employee patchEmployee(int id, EmployeeRequest employeeRequest);

	// patch -- way 2
	public Employee updatingFieldUsingPatch(int id, Map<String, Object> fields);
}

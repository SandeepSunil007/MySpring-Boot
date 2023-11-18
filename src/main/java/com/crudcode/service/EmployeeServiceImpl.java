package com.crudcode.service;

import static com.crudcode.constants.EmployeeConstants.ID_NOT_PRESENT_IN_DATABASE;
import static com.crudcode.constants.EmployeeConstants.NOT_FOUND_IN_DATABASE;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.crudcode.entity.Employee;
import com.crudcode.exception.EmployeeNotFoundException;
import com.crudcode.exception.IdNotFoundException;
import com.crudcode.repository.EmployeeRepository;
import com.crudcode.request.EmployeeRequest;
import com.crudcode.response.EmployeeResponse;

@Service
@Scope("singleton")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee addEmployee(EmployeeRequest employeeRequest) {
		Employee employee = Employee.builder().firstName(employeeRequest.getFirstName())
				.lastName(employeeRequest.getLastName()).empAge(employeeRequest.getEmpAge())
				.empSalary(employeeRequest.getEmpSalary()).dateOfJoining(employeeRequest.getDateOfJoining()).build();
		return employeeRepository.save(employee);
	}

	@Override
	public Employee deleteEmployee(Integer id) {
		Employee deleteEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException(id + NOT_FOUND_IN_DATABASE));
		employeeRepository.delete(deleteEmployee);
		return deleteEmployee;
	}

	@Override
	public Employee getDataById(Integer empId) {
		return employeeRepository.findById(empId)
				.orElseThrow(() -> new IdNotFoundException(empId + ID_NOT_PRESENT_IN_DATABASE));
	}

//	@Override
//	public List<Employee> getAllDataFromDB() {
//		return employeeRepository.findAll();
//	}

	@Override
	public List<EmployeeResponse> getAllDataFromDB() {
		return employeeRepository.findAll().stream().map(emp -> {
			EmployeeResponse response = EmployeeResponse.builder().build();
			BeanUtils.copyProperties(emp, response);
			return response;
		}).collect(Collectors.toList());
	}

	@Override
	public Employee updateEmployeeDetails(EmployeeRequest employeeRequest) {
		int empId = employeeRequest.getEmpId();
		Employee employeeResponse = employeeRepository.findById(empId)
				.orElseThrow(() -> new EmployeeNotFoundException(empId + " Id not available in DB"));

		BeanUtils.copyProperties(employeeRequest, employeeResponse);

		return employeeRepository.save(employeeResponse);

	}

	// Limited fields we can approach like this update particular records
	@Override
	public Employee patchEmployee(int id, EmployeeRequest employeeRequest) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id + " Id not available in DB"));

		if (employeeRequest.getFirstName() != null) {
			employee.setFirstName(employeeRequest.getFirstName());
		}

		if (employeeRequest.getLastName() != null) {
			employee.setLastName(employeeRequest.getLastName());
		}

		if (employeeRequest.getEmpAge() != null) {
			employee.setEmpAge(employeeRequest.getEmpAge());
		}

		if (employeeRequest.getEmpSalary() != null) {
			employee.setEmpSalary(employeeRequest.getEmpSalary());
		}

		if (employeeRequest.getDateOfJoining() != null) {
			employee.setDateOfJoining(employeeRequest.getDateOfJoining());
		}
		return employeeRepository.save(employee);
	}

	// without data field updation. if follow the structure
	// {

	// "lastName" : "pqr",
	// "empSalary" : 4000,
	// }

	@Override
	public Employee updatingFieldUsingPatch(int id, Map<String, Object> fields) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			fields.forEach((key, value) -> {
				Field findField = ReflectionUtils.findField(Employee.class, key);
				findField.setAccessible(true);
				ReflectionUtils.setField(findField, employee.get(), value);

			});
			return employeeRepository.save(employee.get());
		}
		return null;

	}

//	java.lang.IllegalArgumentException: Can not set java.sql.Date field com.crudcode.entity.Employee.dateOfJoining to java.lang.String
// to avoid the above problem we need to use below code

	// if we follow the structure
	// {

	// "lastName" : "pqr",
	// "empSalary" : 4000,
	// "dateOfJoining" : "1023-12-03"
	// }

//	@Override
//	public Employee updatingFieldUsingPatch(int id, Map<String, Object> fields) {
//		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//
//		if (optionalEmployee.isPresent()) {
//			Employee employee = optionalEmployee.get();
//
//			fields.forEach((key, value) -> {
//				Field findField = ReflectionUtils.findField(Employee.class, key);
//
//				if (findField != null) {
//					findField.setAccessible(true);
//
//					// Check if the field type is java.sql.Date and the value is a String
//					if (findField.getType().equals(java.sql.Date.class) && value instanceof String) {
//						String dateString = (String) value;
//						java.sql.Date sqlDate = java.sql.Date.valueOf(dateString);
//						ReflectionUtils.setField(findField, employee, sqlDate);
//					} else {
//						ReflectionUtils.setField(findField, employee, value);
//					}
//				}
//			});
//
//			return employeeRepository.save(employee);
//		}
//
//		return null;
//	}

}

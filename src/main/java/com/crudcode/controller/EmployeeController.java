package com.crudcode.controller;

import static com.crudcode.constants.EmployeeConstants.DELETED_SUCCESSFULLY;
import static com.crudcode.constants.EmployeeConstants.EMPLOYEE_DETAILS_ADDED_INTO_DATABASE_SUCCESSFULLY;
import static com.crudcode.constants.EmployeeConstants.FETCHED_THE_DATA_FROM_DB_SUCCESSFULLY;
import static com.crudcode.constants.EmployeeConstants.UPDATED_DATA_SUCCESFULLY_WITH_PATCH_METHOD;
import static com.crudcode.constants.EmployeeConstants.WE_GOT_THE_DATA_FROM_DB_SUCCESSFULLY;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudcode.request.EmployeeRequest;
import com.crudcode.response.EmployeeBodyResponse;
import com.crudcode.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("emp")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/addEmployee")
	public ResponseEntity<EmployeeBodyResponse> saveEmployeeData(
			@RequestBody @Validated EmployeeRequest employeeRequest) {
		return ResponseEntity.ok(
				EmployeeBodyResponse.builder().isError(false).message(EMPLOYEE_DETAILS_ADDED_INTO_DATABASE_SUCCESSFULLY)
						.data(employeeService.addEmployee(employeeRequest)).build());
	}

	@DeleteMapping("/deleteEmployee/{deleteId}")
	ResponseEntity<EmployeeBodyResponse> deleteEmployeeData(@PathVariable Integer deleteId) {
		return ResponseEntity.ok(EmployeeBodyResponse.builder().isError(false).message(deleteId + DELETED_SUCCESSFULLY)
				.data(employeeService.deleteEmployee(deleteId)).build());
	}

	@GetMapping("/getData/{empId}")
	ResponseEntity<EmployeeBodyResponse> getEmployeeDataById(@PathVariable Integer empId) {
		return ResponseEntity.ok(EmployeeBodyResponse.builder().isError(false)
				.message(FETCHED_THE_DATA_FROM_DB_SUCCESSFULLY).data(employeeService.getDataById(empId)).build());
	}

	@GetMapping("/getAllData")
	ResponseEntity<EmployeeBodyResponse> getAllRecords() {
		return ResponseEntity.ok(EmployeeBodyResponse.builder().isError(false)
				.message(WE_GOT_THE_DATA_FROM_DB_SUCCESSFULLY).data(employeeService.getAllDataFromDB()).build());
	}

	@PutMapping("/update")
	ResponseEntity<EmployeeBodyResponse> updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
		return ResponseEntity
				.ok(EmployeeBodyResponse.builder().isError(false).message("Data Updated in Database Successfully....")
						.data(employeeService.updateEmployeeDetails(employeeRequest)).build());
	}

	@PatchMapping("/patchData/{id}")
	ResponseEntity<EmployeeBodyResponse> patchEmployeeData(@PathVariable int id,
			@RequestBody EmployeeRequest employeeRequest) {
		return ResponseEntity
				.ok(EmployeeBodyResponse.builder().isError(false).message(UPDATED_DATA_SUCCESFULLY_WITH_PATCH_METHOD)
						.data(employeeService.patchEmployee(id, employeeRequest)).build());
	}

	@PatchMapping("/patchWay2/{id}")
	ResponseEntity<EmployeeBodyResponse> patchWay2Approach(@PathVariable int id,
			@RequestBody Map<String, Object> fields) {
		return ResponseEntity
				.ok(EmployeeBodyResponse.builder().isError(false).message(UPDATED_DATA_SUCCESFULLY_WITH_PATCH_METHOD)
						.data(employeeService.updatingFieldUsingPatch(id, fields)).build());
	}
}

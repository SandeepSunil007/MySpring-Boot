package com.crudcode.request;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRequest {
	private int empId;

	private String firstName;

	private String lastName;

	private Integer empAge;

	private Double empSalary;

	private Date dateOfJoining;
}

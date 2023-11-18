package com.crudcode.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeeResponse {
	private int empId;
	private String firstName;
	private String lastName;
	private int empAge;
	private double empSalary;
	private Date dateOfJoining;

}

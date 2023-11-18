package com.crudcode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
//@JsonInclude(value = Include.NON_DEFAULT)
public class EmployeeBodyResponse {

	private boolean isError;
	private String message;
	private Object data;

}

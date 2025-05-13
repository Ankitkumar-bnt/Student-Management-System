package com.manageStudent.restapi.successResponce;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponce<T> {

	private Integer statusCode;
	private String message;
	private HttpStatusCode status;
	private T data;
	
	public SuccessResponce(Integer statusCode, String message, HttpStatusCode status) {
		this.statusCode = statusCode;
		this.message = message;
		this.status = status;
	}
	
	public SuccessResponce(String message) {
		this.message = message;
	}
	
}

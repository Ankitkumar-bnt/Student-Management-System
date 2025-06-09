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
public class ResponseMessage<T>{

	private Integer statusCode;
	private String message;
	private HttpStatusCode status;
	private T data;
	
	public ResponseMessage(HttpStatusCode status, String message, T data) {
		this.message = message;
		this.status = status;
		this.statusCode = status.value();
		this.data = data;
	}
	
	public ResponseMessage(String message) {
		this.message = message;
	}
}

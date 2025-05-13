package com.manageStudent.restapi.exception;

public class StudentNotSavedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StudentNotSavedException(String message)
	{
		super(message);
	}

}

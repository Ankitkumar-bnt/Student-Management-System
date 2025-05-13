package com.manageStudent.restapi.exception;

public class StudentNotUpdatedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public StudentNotUpdatedException(String message)
	{
		super(message);
	}
}

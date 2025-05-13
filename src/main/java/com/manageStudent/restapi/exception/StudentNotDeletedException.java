package com.manageStudent.restapi.exception;

public class StudentNotDeletedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public StudentNotDeletedException(String message)
	{
		super(message);
	}
}

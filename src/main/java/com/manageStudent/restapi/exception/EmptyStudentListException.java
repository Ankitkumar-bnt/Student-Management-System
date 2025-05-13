package com.manageStudent.restapi.exception;

public class EmptyStudentListException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public EmptyStudentListException(String message)
	{
		super(message);
	}
}

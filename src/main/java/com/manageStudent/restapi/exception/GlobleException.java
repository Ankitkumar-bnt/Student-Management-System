package com.manageStudent.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleException {

	@ExceptionHandler(StudentNotSavedException.class)
	public ResponseEntity<String> handleStudentNotSavedException(StudentNotSavedException ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(EmptyStudentListException.class)
	public ResponseEntity<String> handleEmptyStudentListException(EmptyStudentListException ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StudentNotDeletedException.class)
	public ResponseEntity<String> handleStudentNotDeletedException(StudentNotDeletedException ex)
	{
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StudentNotUpdatedException.class)
	public ResponseEntity<String> handleStudentNotUpdatedException(StudentNotUpdatedException ex)
	{
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}

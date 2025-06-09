package com.manageStudent.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDTO {

	private int studentId;
	private String studentName;
	private String studentEmail;
	private String studentContact;
	private double studentMarks;
}

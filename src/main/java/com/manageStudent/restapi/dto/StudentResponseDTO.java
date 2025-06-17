package com.manageStudent.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {

	private int studentId;
	private String studentName;
	private String studentEmail;
	private String studentContact;
	private double studentMarks;
}

package com.manageStudent.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	@Column(nullable = false)
	private String studentName;
	@Column(nullable = false, unique = true)
	private String studentEmail;
	@Column(nullable = false, unique = true, length = 10)
	private String studentContact;
	@Column(nullable = false)
	private double studentMarks;
}

package com.manageStudent.restapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.entity.Student;

@Service
public interface StudentService {

	ResponseEntity<?> isAddStudent(StudentRequestDTO studentData);
	
	ResponseEntity<?> findAllStudent();
	
	ResponseEntity<?> isFoundStudentById(Integer studentId);

	ResponseEntity<?> isStudentDeleted(Integer id);

	Student isStudentUpdated(Integer id, Student studentData);


}

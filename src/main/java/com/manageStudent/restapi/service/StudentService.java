package com.manageStudent.restapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.successResponce.ResponseMessage;

@Service
public interface StudentService {

	ResponseEntity<ResponseMessage<?>> isAddStudent(StudentRequestDTO studentData);
	
	ResponseEntity<ResponseMessage<?>> findAllStudent();
	
	ResponseEntity<ResponseMessage<?>> isFoundStudentById(Integer studentId);

	ResponseEntity<ResponseMessage<?>> isStudentDeleted(Integer id);

	ResponseEntity<ResponseMessage<?>> isStudentUpdated(Integer id, StudentRequestDTO studentData);


}

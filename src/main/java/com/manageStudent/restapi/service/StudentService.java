package com.manageStudent.restapi.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.manageStudent.restapi.entity.Student;

@Service
public interface StudentService {

	Student isAddStudent(Student studentData);
	
	List<Student> findAllStudent();
	
	Student isFoundStudentById(Integer studentId);

	Student isStudentDeleted(Integer id);

	Student isStudentUpdated(Integer id, Student studentData);


}

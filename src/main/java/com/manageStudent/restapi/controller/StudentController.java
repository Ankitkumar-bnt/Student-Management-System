package com.manageStudent.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manageStudent.restapi.constants.Messages;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.service.StudentService;
import com.manageStudent.restapi.successResponce.SuccessResponce;

@CrossOrigin(origins ="http://localhost:5173/")

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@RequestBody Student studentData)
	{
		Student addStudent = studentService.isAddStudent(studentData);
		
		SuccessResponce<Student> responce = new SuccessResponce<>
		(HttpStatus.OK.value(),Messages.STUDENT_ADDED_SUCCESSFULLY,HttpStatus.OK,addStudent);
		
		return ResponseEntity.ok(responce);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> showAllStudent()
	{
		logger.info("============ in showAllStudent() ============");
		List<Student> studentList = studentService.findAllStudent();
		
		logger.info("============ Studnet data fount is "+studentList+" ============");
		SuccessResponce<?> responce = new SuccessResponce<>
		(HttpStatus.OK.value(),Messages.ALL_STUDENT_FOUND,HttpStatus.OK,studentList);
		
		return ResponseEntity.ok(responce);
	}
	
	@PostMapping("/findById")
	public ResponseEntity<?> findStudentById(@RequestParam Integer id)
	{
		Student studentFoundById = studentService.isFoundStudentById(id);
	
		SuccessResponce<?> responce = new SuccessResponce<>
		(HttpStatus.OK.value(),Messages.STUDENT_FOUND_BY_ID+id,HttpStatus.OK,studentFoundById);
		
		return ResponseEntity.ok(responce);
	}
	
	@DeleteMapping("/deleteById")
	public ResponseEntity<?> deleteStudent(@RequestParam Integer id)
	{
		Student studentDeleted = studentService.isStudentDeleted(id);
	
		SuccessResponce<?> responce = new SuccessResponce<>
		(HttpStatus.OK.value(),Messages.STUDENT_DELETED_SUCCESSFULLY,HttpStatus.OK,studentDeleted);
		
		return ResponseEntity.ok(responce);
	}
	
	@PatchMapping("/updateById/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody Student studentData)
	{
		Student studentUpdated = studentService.isStudentUpdated(id, studentData);
		
		SuccessResponce<?> responce = new SuccessResponce<>
		(HttpStatus.OK.value(),Messages.STUDENT_UPDATED_SUCCESSFULLY,HttpStatus.OK,studentUpdated);
		
		return ResponseEntity.ok(responce);
	}

}

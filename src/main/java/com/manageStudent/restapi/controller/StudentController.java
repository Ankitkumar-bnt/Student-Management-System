package com.manageStudent.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.service.StudentService;
import com.manageStudent.restapi.successResponce.ResponseMessage;
import com.manageStudent.restapi.successResponce.SuccessResponce;

import jakarta.validation.Valid;

@CrossOrigin(origins ="http://localhost:5173/")

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@Valid @RequestBody StudentRequestDTO studentData, BindingResult bindingResult)
	{
		logger.info("=========== In addStudent API ============");
		 if (bindingResult.hasErrors()) {
	        Map<String, String> errors = new HashMap<>();

	        bindingResult.getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	        );

	        ResponseMessage<Map<String, String>> response = new ResponseMessage<>(
	            HttpStatus.BAD_REQUEST,
	            "Validation failed",
	            errors
	        );
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 }
		return studentService.isAddStudent(studentData);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> showAllStudent()
	{
		logger.info("============ in showAllStudent() ============");
		ResponseEntity<?> studentList = studentService.findAllStudent();
		
		logger.info("============ Studnet data fount is "+studentList+" ============");
		
		return studentList;
	}
	
	@PostMapping("/findById")
	public ResponseEntity<?> findStudentById(@RequestParam Integer id)
	{
		ResponseEntity<?> studentFoundById = studentService.isFoundStudentById(id);
		
		return studentFoundById;
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


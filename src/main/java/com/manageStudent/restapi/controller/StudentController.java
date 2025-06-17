package com.manageStudent.restapi.controller;

import java.util.HashMap;
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

import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.service.StudentService;
import com.manageStudent.restapi.successResponce.ResponseMessage;

import jakarta.validation.Valid;

@CrossOrigin(origins ="http://localhost:5173/")

@RestController
@RequestMapping("/student")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<ResponseMessage<?>> addStudent(@Valid @RequestBody StudentRequestDTO studentData, BindingResult bindingResult)
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
	public ResponseEntity<ResponseMessage<?>> showAllStudent()
	{
		logger.info("============ in showAllStudent() ============");
		ResponseEntity<ResponseMessage<?>> studentList = studentService.findAllStudent();
		
		logger.info("============ Studnet data fount is "+studentList+" ============");
		
		return studentList;
	}
	
	@PostMapping("/findById")
	public ResponseEntity<ResponseMessage<?>> findStudentById(@RequestParam Integer id)
	{
		ResponseEntity<ResponseMessage<?>> studentFoundById = studentService.isFoundStudentById(id);
		
		return studentFoundById;
	}
	
	@DeleteMapping("/deleteById")
	public ResponseEntity<ResponseMessage<?>> deleteStudent(@RequestParam Integer id)
	{
		ResponseEntity<ResponseMessage<?>> studentDeleted = studentService.isStudentDeleted(id);
		
		return studentDeleted;
	}
	
	@PatchMapping("/updateById/{id}")
	public ResponseEntity<ResponseMessage<?>> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentRequestDTO studentData, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			Map<String, String> errors = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error->
			errors.put(error.getField(), error.getDefaultMessage())
			);
			
			ResponseMessage<Map<String, String>> response = new ResponseMessage<>(HttpStatus.BAD_REQUEST,"",null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		ResponseEntity<ResponseMessage<?>> response = studentService.isStudentUpdated(id, studentData);
		
		return response;
	}

}


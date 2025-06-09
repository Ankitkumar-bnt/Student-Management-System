package com.manageStudent.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageStudent.restapi.constants.Messages;
import com.manageStudent.restapi.controller.StudentController;
import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.dto.StudentResponseDTO;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.exception.EmptyStudentListException;
import com.manageStudent.restapi.exception.StudentNotDeletedException;
import com.manageStudent.restapi.exception.StudentNotFoundException;
import com.manageStudent.restapi.mapper.StudentMapper;
import com.manageStudent.restapi.repository.StudentRepository;
import com.manageStudent.restapi.successResponce.ResponseMessage;

@Service
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	private StudentMapper mapper;
	
	@Override
	public ResponseEntity<?> isAddStudent(StudentRequestDTO studentData) {
		
		logger.info("======= Service: In isAddStudent() ========");
		
		Student savedStudent = null;
		ResponseMessage<StudentResponseDTO> response = null;
		
		try {
			if(studentRepository.existsByStudentEmail(studentData.getStudentEmail())){
				ResponseMessage<StudentResponseDTO> stdRes = new ResponseMessage<>(HttpStatus.BAD_REQUEST, 
						Messages.EMAIL_ALREADY_EXISTS,null); 
				
				return ResponseEntity.status(stdRes.getStatus()).body(stdRes);
			}
			
			if(studentRepository.existsByStudentContact(studentData.getStudentContact())){
				ResponseMessage<StudentResponseDTO> stdRes = new ResponseMessage<>(HttpStatus.BAD_REQUEST, 
						Messages.CONTACT_ALREADY_EXISTS,null); 
				
				return ResponseEntity.status(stdRes.getStatus()).body(stdRes);
			}
			
			Student student = mapper.toEntity(studentData);
		
			savedStudent = studentRepository.save(student);
			mapper.toResponseDTO(savedStudent);
			response = new ResponseMessage<>(HttpStatus.OK, Messages.STUDENT_ADDED_SUCCESSFULLY, mapper.toResponseDTO(savedStudent));
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		catch (Exception e) {
			response = new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, Messages.STUDENT_NOT_ADDED, null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
	}

//=========================================== Add Student ====================================================================
	
	@Override
	public ResponseEntity<?> findAllStudent() {
		
		List<Student> all = null;
		
		try {
			all = studentRepository.findAll();
		}catch(Exception e)
		{
			ResponseMessage<StudentResponseDTO> emptyList = new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, 
					Messages.DATABASE_ERROR,null); 
			
			return ResponseEntity.status(emptyList.getStatus()).body(emptyList);
		}
		
		if(all.isEmpty())
		{
			ResponseMessage<StudentResponseDTO> emptyList = new ResponseMessage<>(HttpStatus.NO_CONTENT, 
					Messages.STUDENT_NOT_FOUND,null); 
			
			return ResponseEntity.status(emptyList.getStatus()).body(emptyList);
			
		}
		
		List<StudentResponseDTO> allStudent = new ArrayList<>();
		for(Student s: all)
		{
			allStudent.add(mapper.toResponseDTO(s));
		}
		
		ResponseMessage<List<StudentResponseDTO>> response = new ResponseMessage<>(HttpStatus.OK, 
				Messages.ALL_STUDENT_FOUND, allStudent);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

//====================================== Find All Student =====================================================================
	
	@Override
	public ResponseEntity<?> isFoundStudentById(Integer studentId) {

		Optional<Student> studentData = null;
		try {
			studentData = studentRepository.findById(studentId);
		}catch(Exception e)
		{
			ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, Messages.DATABASE_ERROR, null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		
		if(studentData.isEmpty()) {
			ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.NOT_FOUND, Messages.STUDENT_NOT_FOUND_BY_ID+studentId, null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		
		StudentResponseDTO studentFound = mapper.toResponseDTO(studentData.get());
		
		ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.OK, Messages.STUDENT_FOUND_BY_ID, studentFound);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

//======================================= Find By Id ==========================================================================
	
	@Override
	public ResponseEntity<?> isStudentDeleted(Integer id) {
		
		Optional<Student> studentData = studentRepository.findById(id);
		if(studentData.isEmpty())
		{
			ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.NOT_FOUND, Messages.STUDENT_NOT_FOUND_BY_ID+id, null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		
		try {
			studentRepository.deleteById(id);
		}catch(Exception e)
		{
			ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, Messages.DATABASE_ERROR, null);
			return ResponseEntity.status(response.getStatus()).body(response);
		}
		
		StudentResponseDTO studentFound = mapper.toResponseDTO(studentData.get());
		
		ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.OK, Messages.STUDENT_DELETED_SUCCESSFULLY, studentFound);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

//======================================== Delete By Id ======================================================================	
	
	@Override
	public Student isStudentUpdated(Integer id, Student studentData) {
		Student foundStudentById = studentRepository.findById(id).orElseThrow(()->
		new StudentNotDeletedException(Messages.UPDATION_FAILED));
		
		if(studentData.getStudentName()!=null)
			foundStudentById.setStudentName(studentData.getStudentName());
		
		if(studentData.getStudentEmail()!=null)
			foundStudentById.setStudentEmail(studentData.getStudentEmail());
		
		if(studentData.getStudentContact()!=null)
			foundStudentById.setStudentContact(studentData.getStudentContact());
		
		if(studentData.getStudentMarks() >= 0)
			foundStudentById.setStudentMarks(studentData.getStudentMarks());
		
		return studentRepository.save(foundStudentById);
	}

//======================================== Update By Id =======================================================================
}

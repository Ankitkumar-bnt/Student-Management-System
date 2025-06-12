package com.manageStudent.restapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.manageStudent.restapi.constants.Messages;
import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.dto.StudentResponseDTO;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.mapper.StudentMapperImpl;
import com.manageStudent.restapi.repository.StudentRepository;
import com.manageStudent.restapi.successResponce.ResponseMessage;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentRepository repo;
	
	private final StudentMapperImpl mapper = new StudentMapperImpl();
	
	@InjectMocks
	private StudentServiceImpl service;
	
	@BeforeEach
	void setup() throws Exception{
		service.setMapper(mapper); //Setter injection required to set in service
		
//		Field field = StudentServiceImpl.class.getDeclaredField("mapper"); // below lines are field injection all i single class
//		field.setAccessible(true);
//		field.set(service, mapper);
	}
	
	@Test
	void test_AddStudent_Success()
	{
		StudentRequestDTO requestDTO = new StudentRequestDTO();
		requestDTO.setStudentName("Bob");
		requestDTO.setStudentEmail("bob@gmail.com");
		requestDTO.setStudentContact("9988776655");
		requestDTO.setStudentMarks(88);
		
		Student student = new Student();
		student.setStudentId(1);
		student.setStudentName(requestDTO.getStudentName());
		student.setStudentEmail(requestDTO.getStudentEmail());
		student.setStudentContact(requestDTO.getStudentContact());
		student.setStudentMarks(requestDTO.getStudentMarks());
		
		when(repo.save(any(Student.class))).thenReturn(student);
		
		ResponseEntity<?> response = service.isAddStudent(requestDTO);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>)response.getBody();
		
		assertNotNull(body);
		
		assertEquals(Messages.STUDENT_ADDED_SUCCESSFULLY, body.getMessage());
		
		StudentResponseDTO responseDTO = (StudentResponseDTO) body.getData();
		
		assertNotNull(responseDTO);
		
		assertEquals(requestDTO.getStudentName(), responseDTO.getStudentName());
		assertEquals(requestDTO.getStudentEmail(), responseDTO.getStudentEmail());
		assertEquals(requestDTO.getStudentContact(), responseDTO.getStudentContact());
		assertEquals(requestDTO.getStudentMarks(), responseDTO.getStudentMarks());
	}
	
	
	void test_addStudent_emailAlreadyExists()
	{
		
	}
}

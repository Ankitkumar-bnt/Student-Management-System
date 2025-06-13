package com.manageStudent.restapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

//import java.lang.reflect.Field;

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
	private StudentRequestDTO request;
	private Student student;
	private int studentId;
	
	@InjectMocks
	private StudentServiceImpl service;

//=========================== Helper Method ===========================================================================
	
	private StudentRequestDTO buildStudentRequestDTO(String name, String email, String contact, Double mark)
	{
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentName(name);
		dto.setStudentEmail(email);
		dto.setStudentContact(contact);
		dto.setStudentMarks(mark);
		return dto;
	}
	
	private Student buildStudentEntity(int id, String name, String email, String contact, Double mark)
	{
		Student student = new Student();
		student.setStudentId(id);
		student.setStudentName(name);
		student.setStudentEmail(email);
		student.setStudentContact(contact);
		student.setStudentMarks(mark);
		return student;
	}
	@BeforeEach
	void setup() throws Exception{
		service.setMapper(mapper); //Setter injection required to set in service
		
//		Field field = StudentServiceImpl.class.getDeclaredField("mapper"); // below lines are field injection all in single class
//		field.setAccessible(true);
//		field.set(service, mapper);
		studentId = 5;
		request = buildStudentRequestDTO("Rock", "rock@gamil.com", "9966332255", 88.00);
		student = buildStudentEntity(5, "Rock", "rock@gamil.com", "9966332255", 88.00);
	}
	
//================================= Add Student Test ==================================================================	
	
	@Test
	void addStudent_whenValidRequest_returnsCreatedResponse()
	{
//		StudentRequestDTO request = new StudentRequestDTO();
//		request.setStudentName("Bob");
//		request.setStudentEmail("bob@gmail.com");
//		request.setStudentContact("9988776655");
//		request.setStudentMarks(88);
		
//		Student student = new Student();
//		student.setStudentId(1);
//		student.setStudentName(request.getStudentName());
//		student.setStudentEmail(request.getStudentEmail());
//		student.setStudentContact(request.getStudentContact());
//		student.setStudentMarks(request.getStudentMarks());
		
		when(repo.save(any(Student.class))).thenReturn(student);
		
		ResponseEntity<?> response = service.isAddStudent(request);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>)response.getBody();
		
		assertNotNull(body);
		
		assertEquals(Messages.STUDENT_ADDED_SUCCESSFULLY, body.getMessage());
		
		StudentResponseDTO responseDTO = (StudentResponseDTO) body.getData();
		
		assertNotNull(responseDTO);
		
		assertEquals(request.getStudentName(), responseDTO.getStudentName());
		assertEquals(request.getStudentEmail(), responseDTO.getStudentEmail());
		assertEquals(request.getStudentContact(), responseDTO.getStudentContact());
		assertEquals(request.getStudentMarks(), responseDTO.getStudentMarks());
	}
	
	@Test
	void addStudent_whenEmailExists_returnsBadRequest()
	{
//		StudentRequestDTO request = new StudentRequestDTO();
//		request.setStudentName("Bob");
//		request.setStudentEmail("bob@gmail.com");
//		request.setStudentContact("9988776655");
//		request.setStudentMarks(88);
		
		when(repo.existsByStudentEmail(request.getStudentEmail())).thenReturn(true);
		
		ResponseEntity<?> response = service.isAddStudent(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		assertEquals(Messages.EMAIL_ALREADY_EXISTS, body.getMessage());
	}
	
	@Test
	void addStudent_whenContactExists_returnsBadRequest()
	{
//		StudentRequestDTO request = new StudentRequestDTO();
//		request.setStudentName("Bob");
//		request.setStudentEmail("bob@gmail.com");
//		request.setStudentContact("9988776655");
//		request.setStudentMarks(88);
		
		when(repo.existsByStudentContact(request.getStudentContact())).thenReturn(true);
		
		ResponseEntity<?> response = service.isAddStudent(request);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		assertEquals(Messages.CONTACT_ALREADY_EXISTS, body.getMessage());
	}
	
	
	@Test
	void addStudent_whenDatabaseFails_returnsInternalServerError()
	{
//		StudentRequestDTO request = new StudentRequestDTO();
//		request.setStudentName("Bob");
//		request.setStudentEmail("bob@gmail.com");
//		request.setStudentContact("9988776655");
//		request.setStudentMarks(88);
		
		when(repo.existsByStudentEmail(request.getStudentEmail())).thenReturn(false);
		when(repo.existsByStudentContact(request.getStudentContact())).thenReturn(false);
		when(repo.save(any(Student.class))).thenThrow(new RuntimeException("DB error"));
		
		ResponseEntity<?> response = service.isAddStudent(request);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		
		assertEquals(Messages.STUDENT_NOT_ADDED, body.getMessage());
	}
//================================== Find All Student Test ==========================================================
	
	@Test
	void findAllStudents_whenStudentsExist_returnsStudentList(){
		
		when(repo.findAll()).thenReturn(List.of(student));
		
		ResponseEntity<?> response = service.findAllStudent();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		
		assertEquals(Messages.ALL_STUDENT_FOUND, body.getMessage());
		
		@SuppressWarnings("unchecked")
		List<StudentResponseDTO> responseDTO = (List<StudentResponseDTO>) body.getData();
		
		StudentResponseDTO dto = responseDTO.get(0);
		
		assertEquals(student.getStudentName(), dto.getStudentName());
		assertEquals(student.getStudentEmail(), dto.getStudentEmail());
		assertEquals(student.getStudentContact(), dto.getStudentContact());
		assertEquals(student.getStudentMarks(), dto.getStudentMarks());
	}
	
	@Test
	void findAllStudents_whenStudentListEmpty_returnsNoContent()
	{
		when(repo.findAll()).thenReturn(List.of());
		
		ResponseEntity<?> response = service.findAllStudent();
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		
		assertEquals(Messages.STUDENT_NOT_FOUND, body.getMessage());
		assertNull(body.getData());
	}
	
	@Test
	void findAllStudents_whenDatabaseFails_returnsInternalServerError()
	{
		when(repo.findAll()).thenThrow(new RuntimeException("Database down"));
		
		ResponseEntity<?> response = service.findAllStudent();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		
		assertEquals(Messages.DATABASE_ERROR, body.getMessage());
		assertNull(body.getData());
	}
	
//======================== Find Student By Id ===========================================================
	
	@Test
	void findStudentById_whenIdExists_returnsStudentData()
	{
		when(repo.findById(studentId)).thenReturn(Optional.of(student));
		
		ResponseEntity<?> response = service.isFoundStudentById(studentId);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		ResponseMessage<?> body = (ResponseMessage<?>) response.getBody();
		assertNotNull(body);
		assertEquals(Messages.STUDENT_FOUND_BY_ID, body.getMessage());
		
	    StudentResponseDTO dto = (StudentResponseDTO) body.getData();
	    assertNotNull(dto);
		assertEquals(student.getStudentName(), dto.getStudentName());
		assertEquals(student.getStudentEmail(), dto.getStudentEmail());
		assertEquals(student.getStudentContact(), dto.getStudentContact());
		assertEquals(student.getStudentMarks(), dto.getStudentMarks());
	}
	
}













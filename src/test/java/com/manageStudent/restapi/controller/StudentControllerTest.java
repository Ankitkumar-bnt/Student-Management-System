package com.manageStudent.restapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manageStudent.restapi.constants.Messages;
import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.dto.StudentResponseDTO;
import com.manageStudent.restapi.mapper.StudentMapperImpl;
import com.manageStudent.restapi.service.StudentServiceImpl;
import com.manageStudent.restapi.successResponce.ResponseMessage;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentServiceImpl service;

	@Autowired
	private ObjectMapper objectMapper;
	
	private final StudentMapperImpl mapper = new StudentMapperImpl();
	private StudentRequestDTO request;
	private StudentRequestDTO updateRequest;
	private StudentResponseDTO responseDto;
	private StudentResponseDTO updatedResponseDto;
	private int studentId;
	private int studentNotExitId;
	
	@BeforeEach
	void setup() throws Exception{
		service.setMapper(mapper); //Setter injection required to set in service
		studentId = 5;
		studentNotExitId = 99;
		request = new StudentRequestDTO("Rock", "rock@gamil.com", "9966332255", 88.00);
		responseDto = new StudentResponseDTO(studentId, "Rock", "rock@gamil.com", "9966332255", 88.00);
		updatedResponseDto = new StudentResponseDTO(studentId, "The Rock", "the.rock@gmail.com", "9922111111", 99.99);
		updateRequest = new StudentRequestDTO("The Rock", "the.rock@gmail.com", "9922111111", 99.99);
	}
		
//============================ Add Student API =====================================================================
	
	@Test
	void addStudent_returnsCreated() throws Exception
	{
		ResponseMessage<StudentResponseDTO> responseMessage = new ResponseMessage<>(HttpStatus.CREATED,Messages.STUDENT_ADDED_SUCCESSFULLY,responseDto);
		when(service.isAddStudent(any(StudentRequestDTO.class)))
		.thenReturn(new ResponseEntity<>(responseMessage,HttpStatus.CREATED));
		
		String requestJson = new ObjectMapper().writeValueAsString(request);
		
		mockMvc.perform(post("/student/addStudent").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value(Messages.STUDENT_ADDED_SUCCESSFULLY))
				.andReturn();
	}
	
//============================= Find All API ==========================================================================
	
	@Test
	void findAllStudent_returnsStudentList() throws Exception
	{
		List<StudentResponseDTO> studentList = List.of(responseDto);
		
		ResponseMessage<List<StudentResponseDTO>> response = new ResponseMessage<>(HttpStatus.OK,Messages.ALL_STUDENT_FOUND,studentList);
		
		when(service.findAllStudent()).thenReturn(new ResponseEntity<ResponseMessage<?>>(response, HttpStatus.OK));
		
		mockMvc.perform(get("/student/findAll").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isArray())
				.andExpect(jsonPath("$.message").value(Messages.ALL_STUDENT_FOUND))
				.andExpect(jsonPath("$.data.length()").value(1))
				.andExpect(jsonPath("$.data[0].studentName").value(responseDto.getStudentName()));
	}
	
//============================== Find Student By Id API =============================================================
	
	@Test
	void findStudentById_returnStudent() throws Exception
	{
		ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.OK, Messages.STUDENT_FOUND_BY_ID, responseDto);
		
		when(service.isFoundStudentById(studentId)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
		
		mockMvc.perform(post("/student/findById").contentType(MediaType.APPLICATION_JSON)
				.param("id", String.valueOf(studentId))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(Messages.STUDENT_FOUND_BY_ID))
			.andExpect(jsonPath("$.data.studentName").value(responseDto.getStudentName()))
			.andExpect(jsonPath("$.data.studentEmail").value(responseDto.getStudentEmail()));
	}
	
//================================ Delete Student By Id ===============================================================
	
	@Test
	void deleteStudentById_returnDeletedStudent() throws Exception
	{	
		ResponseMessage<StudentResponseDTO> response = new ResponseMessage<>(HttpStatus.OK, Messages.STUDENT_DELETED_SUCCESSFULLY, responseDto);

		when(service.isStudentDeleted(studentId)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

		mockMvc.perform(delete("/student/deleteById").contentType(MediaType.APPLICATION_JSON)
				.param("id", String.valueOf(studentId))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(Messages.STUDENT_DELETED_SUCCESSFULLY))
			.andExpect(jsonPath("$.data.studentName").value(responseDto.getStudentName()))
			.andExpect(jsonPath("$.data.studentEmail").value(responseDto.getStudentEmail()));
	}
	
//================================ Update Student By Id ==================================================================
	
	
	
	
}
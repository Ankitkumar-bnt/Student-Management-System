package com.manageStudent.restapi.mapper;

import org.springframework.stereotype.Component;

import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.dto.StudentResponseDTO;
import com.manageStudent.restapi.entity.Student;

@Component
public class StudentMapperImpl implements StudentMapper {
	
	@Override
	public Student toEntity(StudentRequestDTO requestDTO) {
		Student student = new Student();
		if(requestDTO == null)
			return null;
		student.setStudentName(requestDTO.getStudentName());
		student.setStudentEmail(requestDTO.getStudentEmail());
		student.setStudentContact(requestDTO.getStudentContact());
		student.setStudentMarks(requestDTO.getStudentMarks());
		
		return student;
	}

	@Override
	public StudentResponseDTO toResponseDTO(Student student) {
		StudentResponseDTO dto = new StudentResponseDTO();	
		if(student == null)
			return null;
		dto.setStudentId(student.getStudentId());
		dto.setStudentName(student.getStudentName());
		dto.setStudentEmail(student.getStudentEmail());
		dto.setStudentContact(student.getStudentContact());
		dto.setStudentMarks(student.getStudentMarks());
		
		return dto;
	}

}

package com.manageStudent.restapi.mapper;


import com.manageStudent.restapi.dto.StudentRequestDTO;
import com.manageStudent.restapi.dto.StudentResponseDTO;
import com.manageStudent.restapi.entity.Student;

public interface StudentMapper {

	Student toEntity(StudentRequestDTO requestDTO);
	
	StudentResponseDTO toResponseDTO(Student student);
	
}

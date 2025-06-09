package com.manageStudent.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageStudent.restapi.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	boolean existsByStudentEmail(String email);
	
	boolean existsByStudentContact(String contact);
}

package com.manageStudent.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manageStudent.restapi.constants.Messages;
import com.manageStudent.restapi.entity.Student;
import com.manageStudent.restapi.exception.EmptyStudentListException;
import com.manageStudent.restapi.exception.StudentNotDeletedException;
import com.manageStudent.restapi.exception.StudentNotFoundException;
import com.manageStudent.restapi.exception.StudentNotSavedException;
import com.manageStudent.restapi.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	
	@Override
	public Student isAddStudent(Student studentData) {
		
		
		Student saveStudent = studentRepository.save(studentData);
		if(saveStudent == null)
			throw new StudentNotSavedException(Messages.STUDENT_NOT_ADDED);
		return saveStudent;
		
	}

	@Override
	public List<Student> findAllStudent() {
		List<Student> all = studentRepository.findAll();
		if(all.isEmpty())
			throw new EmptyStudentListException(Messages.STUDENT_NOT_FOUND);
		return all;
	}

	@Override
	public Student isFoundStudentById(Integer studentId) {

		Optional<Student> studentData = studentRepository.findById(studentId);
		if(studentData.isEmpty()) {
			throw new StudentNotFoundException(Messages.STUDENT_NOT_FOUND);
		}
		return studentData.get();
	}

	@Override
	public Student isStudentDeleted(Integer id) {
		
		Student deletedStudent = studentRepository.findById(id).orElseThrow(()->
		new StudentNotDeletedException(Messages.STUDENT_NOT_DELETED));//Supplier required
		studentRepository.deleteById(id);
		return deletedStudent;
	}

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

}

package com.app.registration.service;

import java.util.List;

import com.app.registration.model.Student;

public interface StudentService {

	public List<Student> getAllStudents() throws Exception;
	
	public Student createStudent(Student student) throws Exception;
	
	public Student updateStudent(Student student) throws Exception;
	
	public int deleteStudent(Long id) throws Exception;

}

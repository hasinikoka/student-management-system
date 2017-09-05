package com.app.registration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.registration.dao.StudentDao;
import com.app.registration.model.Student;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentDao studentDao;
	
	@Override
	public List<Student> getAllStudents() throws Exception{
		return studentDao.loadAllStudents();
	}

	@Override
	public Student createStudent(Student student) throws Exception{
		return studentDao.persistStudent(student);
	}

	@Override
	public Student updateStudent(Student student) throws Exception{
		return studentDao.updateStudent(student);
	}

	@Override
	public int deleteStudent(Long id) throws Exception{
		return studentDao.deleteStudent(id);
	}

}

package com.app.registration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.app.registration.controller.advice.ResourceNotFoundException;
import com.app.registration.model.Student;

@Repository
public class StudentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		createTable();
	}

	private void createTable() {
		final String query = "create table if not exists  students(firstName text, lastName text, age integer, id integer primary key autoincrement)";
		jdbcTemplate.update(query);
	}

	public Student persistStudent(final Student student) throws Exception {
		final String query = "insert into students(firstName, lastName, age) values(?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, student.getFirstName());
				ps.setString(2, student.getLastName());
				ps.setInt(3, student.getAge());

				return ps;
			}
		}, keyHolder);
		student.setId(keyHolder.getKey().longValue());
		return student;
	}

	public List<Student> loadAllStudents() throws Exception {

		List<Student> students = new ArrayList<Student>();
		String query = "select * from students";
		students = jdbcTemplate.query(query, new RowMapper<Student>() {

			@Override
			public Student mapRow(ResultSet rs, int arg1) throws SQLException {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("firstName"));
				student.setLastName(rs.getString("lastName"));
				student.setAge(rs.getInt("age"));
				return student;
			}
		});

		return students;
	}

	public Student updateStudent(Student student) throws Exception {
		String query = "update students set firstName=?,lastName=?,age=? where id=?";
		int numRows = jdbcTemplate.update(query,
				new Object[] { student.getFirstName(), student.getLastName(), student.getAge(), student.getId() });
		if (numRows == 0) {
			throw new ResourceNotFoundException("Student with id " + student.getId() + " not found");
		}
		return student;
	}

	public int deleteStudent(Long id) throws Exception {
		String query = "delete from students where id=?";
		int numRows = jdbcTemplate.update(query, new Object[] { id });
		if (numRows == 0) {
			throw new ResourceNotFoundException("Student with id " + id + " not found");
		}
		return numRows;

	}

}

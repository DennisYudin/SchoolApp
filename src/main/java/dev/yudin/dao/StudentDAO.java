package dev.yudin.dao;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;

import java.sql.PreparedStatement;
import java.util.List;

public interface StudentDAO {
	List<Student> findAll();
	void save(List<Student> students);
	void save(Student student);
	List<StudentDTO> findAllBy(String courseName);
	void deleteById(int id);
}

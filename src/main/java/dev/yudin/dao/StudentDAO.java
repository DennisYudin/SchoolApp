package dev.yudin.dao;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;

import java.util.List;

public interface StudentDAO {
	List<Student> findAll();
	void save(List<Student> students);
	List<StudentDTO> findAllBy(String courseName);
}

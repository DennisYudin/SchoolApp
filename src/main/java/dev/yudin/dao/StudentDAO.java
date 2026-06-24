package dev.yudin.dao;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public interface StudentDAO extends GenericDAO<Student>{
	Optional<Student> getBy(int id);

	void save(List<Student> students);
	void save(Student student);
	List<StudentDTO> findAllBy(String courseName);
	void deleteById(int id);
	void findAllTest();
}

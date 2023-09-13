package dev.yudin.services;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentsService {
	Optional<Student> getBy(int id);
	List<Student> findAll();
	void save(List<Student> students);
	void save(Student student);
	Map<Student, Integer> convert(List<Student> studentsFromTable);
	List<StudentDTO> findAllBy(String courseName);
	void deleteById(int id);
}

package dev.yudin.services;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;
import org.apache.log4j.pattern.LiteralPatternConverter;

import java.util.List;
import java.util.Map;

public interface StudentsService {
	List<Student> findAll();
	void save(List<Student> students);
	Map<Student, Integer> convert(List<Student> studentsFromTable);
	List<StudentDTO> findAllBy(String courseName);
}

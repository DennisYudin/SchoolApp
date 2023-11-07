package dev.yudin.dao;

import dev.yudin.entities.Course;
import dev.yudin.entities.Student;

import java.util.List;

public interface CourseDAO extends GenericDAO {
	List<Course> findAll();
	List<String> findAllBy(Student student);
	void save(List<Course> courses);
}

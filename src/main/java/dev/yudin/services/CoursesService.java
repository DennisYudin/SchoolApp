package dev.yudin.services;

import dev.yudin.entities.Course;
import dev.yudin.entities.Group;

import java.util.List;
import java.util.Map;

public interface CoursesService {

	Map<String, Integer> convert(List<Course> courses);
	List<Course> findAll();
	void save(List<Course> courses);
}

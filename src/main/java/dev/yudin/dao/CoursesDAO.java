package dev.yudin.dao;

import dev.yudin.entities.Course;

import java.util.List;

public interface CoursesDAO extends GenericDAO {

	List<Course> findAll();

	void save(List<Course> courses);
}

package dev.yudin.dao;

import dev.yudin.entities.Course;

import java.util.List;

public interface CoursesDAO {

	List<Course> findAll();

	void fillCourseTable(List<Course> courses);
}

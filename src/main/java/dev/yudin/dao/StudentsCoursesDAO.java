package dev.yudin.dao;

import dev.yudin.entities.StudentCourseDTO;

import java.util.List;

public interface StudentsCoursesDAO {

	List<StudentCourseDTO> findAll();
	void save(List<StudentCourseDTO> studentCourseDTOS);
	void save(StudentCourseDTO studentCourseDTO);
}

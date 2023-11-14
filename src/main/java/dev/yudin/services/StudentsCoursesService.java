package dev.yudin.services;

import dev.yudin.entities.StudentCourseDTO;

import java.util.List;

public interface StudentsCoursesService {
	List<StudentCourseDTO> findAll();
	void save(List<StudentCourseDTO> list);
	void save(StudentCourseDTO studentCourseDTO);
	void delete(int studentId, int courseId);
}

package dev.yudin.dao;

import dev.yudin.entities.StudentCourseDTO;

import java.util.List;

public interface StudentsCoursesDAO extends GenericDAO<StudentCourseDTO>{

	void save(List<StudentCourseDTO> studentCourseDTOS);
	void save(StudentCourseDTO studentCourseDTO);
	void deleteRecord(int studentId, int courseId);
}

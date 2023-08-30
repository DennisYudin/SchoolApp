package dev.yudin.services;

import dev.yudin.entities.StudentCourseDTO;

import java.util.List;

public interface StudentCourseService {
	List<StudentCourseDTO> findAll();
	void save(List<StudentCourseDTO> list);
}

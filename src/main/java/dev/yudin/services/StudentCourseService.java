package dev.yudin.services;

import dev.yudin.entities.Student;
import dev.yudin.entities.StudentCourseDTO;

import java.util.List;
import java.util.Map;

public interface StudentCourseService {

	void save(List<StudentCourseDTO> list);
}

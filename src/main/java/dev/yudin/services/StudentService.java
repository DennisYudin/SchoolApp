package dev.yudin.services;

import dev.yudin.entities.Student;

import java.util.List;

public interface StudentService {
	List<Student> findAll();
	void save(List<Student> students);
}

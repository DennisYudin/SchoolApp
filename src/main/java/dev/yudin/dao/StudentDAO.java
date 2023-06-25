package dev.yudin.dao;

import dev.yudin.entities.Student;

import java.util.List;

public interface StudentDAO {

	List<Student> findAll();
}

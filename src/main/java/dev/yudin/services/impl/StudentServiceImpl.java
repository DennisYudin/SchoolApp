package dev.yudin.services.impl;

import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.StudentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements StudentService {
	private final Logger log = LogManager.getLogger(StudentServiceImpl.class);
	private StudentDAO studentDAO;

	public StudentServiceImpl(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	public Map<Student, Integer> convert(List<Student> studentsFromTable) {
		Map<Student, Integer> result = new HashMap<>();
		studentsFromTable.forEach(student -> result.put(student, student.getId()));
		return result;
	}

	@Override
	public List<Student> findAll() {
		try{
			return studentDAO.findAll();
		} catch (DAOException ex) {
			log.error("Error during findAll()");
			throw new ServiceException("Error during findAll()");
		}
	}

	@Override
	public void save(List<Student> students) {
		try {
			studentDAO.save(students);
		} catch (DAOException ex) {
			log.error("Error during save()");
			throw new ServiceException("Error during save()");
		}
	}
}

package dev.yudin.services.impl;

import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.StudentService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class StudentServiceImpl implements StudentService {
	private final Logger log = LogManager.getLogger(StudentServiceImpl.class);
	private StudentDAO studentDAO;

//	@Override
//	public Map<String, Integer> convert(List<Student> students) {
//		Map<String, Integer> result = new HashMap<>();
//		students.forEach(student -> result.put(student.getFirstName(), student.getId()));
//		return result;
//	}

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

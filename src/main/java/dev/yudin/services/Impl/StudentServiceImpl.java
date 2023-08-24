package dev.yudin.services.Impl;

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

package dev.yudin.services.impl;

import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.entities.StudentCourseDTO;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.StudentsCoursesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class StudentsCoursesServiceImpl implements StudentsCoursesService {
	private final Logger log = LogManager.getLogger(StudentsCoursesServiceImpl.class);

	private StudentsCoursesDAO studentsCoursesDAO;


	public StudentsCoursesServiceImpl(StudentsCoursesDAO studentsCoursesDAO) {
		this.studentsCoursesDAO = studentsCoursesDAO;
	}

	@Override
	public List<StudentCourseDTO> findAll() {
		try {
			return studentsCoursesDAO.findAll();
		} catch (DAOException ex) {
			log.error("Error during findAll()");
			throw new ServiceException("Error during findAll()");
		}
	}

	@Override
	public void save(List<StudentCourseDTO> list) {
		try {
			studentsCoursesDAO.save(list);
		} catch (DAOException ex) {
			log.error("Error during save()");
			throw new ServiceException("Error during save()");
		}
	}
}

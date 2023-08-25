package dev.yudin.services.impl;

import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.entities.StudentCourseDTO;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.StudentCourseService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class StudentCourseServiceImpl implements StudentCourseService {
	private final Logger log = LogManager.getLogger(StudentCourseServiceImpl.class);

	private StudentsCoursesDAO studentsCoursesDAO;


	public StudentCourseServiceImpl(StudentsCoursesDAO studentsCoursesDAO) {
		this.studentsCoursesDAO = studentsCoursesDAO;
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

package dev.yudin.services.Impl;

import dev.yudin.dao.CourseDAO;
import dev.yudin.entities.Course;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.CoursesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class CoursesServiceImpl implements CoursesService {
	private final Logger log = LogManager.getLogger(CoursesServiceImpl.class);
	private CourseDAO courseDAO;

	public CoursesServiceImpl(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	@Override
	public void save(List<Course> courses) {
		try {
			courseDAO.save(courses);
		} catch (DAOException ex) {
			log.error("Error during save()");
			throw new ServiceException("Error during save()");
		}
	}
}

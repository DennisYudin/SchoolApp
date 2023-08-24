package dev.yudin.services.Impl;

import dev.yudin.dao.CourseDAO;
import dev.yudin.entities.Course;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.CoursesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseServiceImpl implements CoursesService {
	private final Logger log = LogManager.getLogger(CourseServiceImpl.class);
	private final CourseDAO courseDAO;
	public CourseServiceImpl(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	@Override
	public Map<String, Integer> convert(List<Course> courses) {
		Map<String, Integer> result = new HashMap<>();
		courses.forEach(course -> result.put(course.getName(), course.getId()));
		return result;
	}

	@Override
	public List<Course> findAll() {
		try {
			return courseDAO.findAll();
		} catch (DAOException ex) {
			log.error("Error during findAll()");
			throw new ServiceException("Error during findAll()");
		}
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

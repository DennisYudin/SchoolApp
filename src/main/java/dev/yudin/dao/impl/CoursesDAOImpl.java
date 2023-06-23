package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.CoursesDAO;
import dev.yudin.entities.Course;
import dev.yudin.exceptions.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAOImpl implements CoursesDAO {
	public static final String FIND_ALL_SQL = "SELECT * FROM courses";
	private final Logger log = LogManager.getLogger(CoursesDAOImpl.class);
	private Manager dataSource;
	public static final String INSERT_INTO_COURSES_TABLE_SQL = "INSERT INTO courses (name, description) VALUES(?,?)";

	public CoursesDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> findAll() {
		List <Course> courseList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			while (resultSet.next()) {
				Course course = new Course();

				long courseId = resultSet.getLong("id");
				String courseName = resultSet.getString("name");
				String courseDescription = resultSet.getString("description");

				course.setId(courseId);
				course.setName(courseName);
				course.setDescription(courseDescription);

				courseList.add(course);
			}
		} catch (SQLException e) {
			log.error("Error during findAll() call");
			throw new DAOException("Error during findAll() call", e);
		}
		return courseList;
	}

	@Override
	public void fillCourseTable(List<Course> courses) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(INSERT_INTO_COURSES_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {

			for (Course course : courses) {
				String name = course.getName();
				String description = course.getDescription();

				statement.setString(1, name);
				statement.setString(2, description);
				statement.execute();

				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						long courseId = resultSet.getLong("id");
						course.setId(courseId);
					}
				}
			}
		} catch (SQLException ex) {
			log.error("Error during fillCourseTable() call");
			throw new DAOException("Error during fillCourseTable() call", ex);
		}
	}
}

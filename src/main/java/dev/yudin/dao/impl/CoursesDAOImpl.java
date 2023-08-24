package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.CourseDAO;
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

public class CoursesDAOImpl implements CourseDAO {
	private final Logger log = LogManager.getLogger(CoursesDAOImpl.class);
	public static final String FIND_ALL_SQL = "SELECT * FROM courses";
	public static final String INSERT_INTO_COURSES_TABLE_SQL = "INSERT INTO courses (name, description) VALUES(?,?)";
	private final Manager dataSource;

	public CoursesDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> findAll() {
		List <Course> courses = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			while (resultSet.next()) {
				Course course = new Course();

				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String desc = resultSet.getString("description");

				course.setId(id);
				course.setName(name);
				course.setDescription(desc);

				courses.add(course);
			}
			return courses;
		} catch (SQLException e) {
			log.error("Error during findAll() call");
			throw new DAOException("Error during findAll() call", e);
		}
	}

	@Override
	public void save(List<Course> courses) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_COURSES_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {

			for (Course course : courses) {
				String name = course.getName();
				String description = course.getDescription();

				statement.setString(1, name);
				statement.setString(2, description);
				statement.execute();
			}
		} catch (SQLException ex) {
			log.error("Error during save() call");
			throw new DAOException("Error during save() call", ex);
		}
	}
}

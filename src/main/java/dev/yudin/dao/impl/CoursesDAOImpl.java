package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.CourseDAO;
import dev.yudin.entities.Course;
import dev.yudin.entities.Student;
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
	public static final String NAME_COLUMN = "name";
	public static final String ID_COLUMN = "id";
	public static final String DESC_COLUMN = "description";
	private final Logger log = LogManager.getLogger(CoursesDAOImpl.class);
	public static final String FIND_ALL_SQL = "SELECT id, name, description FROM courses";
	public static final String INSERT_INTO_COURSES_TABLE_SQL = "INSERT INTO courses (name, description) VALUES(?,?)";
	private static final String FIND_ALL_BY_STUDENT_SQL =
			"SELECT courses_table.name FROM courses AS courses_table\n" +
					"JOIN students_courses AS students_courses_table ON courses_table.id = students_courses_table.course_id\n" +
					"JOIN students AS students_table ON students_table.id = students_courses_table.student_id\n" +
					"WHERE students_table.first_name = ? AND students_table.last_name = ?";
	private final Manager dataSource;

	public CoursesDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> findAll() {
		List <Course> courses = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_ALL_SQL);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Course course = new Course();

				int id = resultSet.getInt(ID_COLUMN);
				String name = resultSet.getString(NAME_COLUMN);
				String desc = resultSet.getString(DESC_COLUMN);

				course.setId(id);
				course.setName(name);
				course.setDescription(desc);

				courses.add(course);
			}
			return courses;
		} catch (SQLException e) {
			log.error("Error during getting all courses");
			throw new DAOException("Error during getting all courses", e);
		}
	}

	@Override
	public List<String> findAllBy(Student student) {
		List<String> result = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_STUDENT_SQL)) {
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString(NAME_COLUMN);
				result.add(name);
			}
			return result;
		} catch (SQLException e) {
			log.error("Error during getting all courses by student: " + student);
			throw new DAOException("Error during getting all courses by student: " + student);
		}
	}

	@Override
	public void save(List<Course> courses) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement statement = conn.prepareStatement(
					 INSERT_INTO_COURSES_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {

			for (Course course : courses) {
				String name = course.getName();
				String description = course.getDescription();

				statement.setString(1, name);
				statement.setString(2, description);

				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException ex) {
			log.error("Error during saving...");
			throw new DAOException("Error during saving...", ex);
		}
	}
}

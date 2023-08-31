package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAOImpl implements StudentDAO {
	private final Logger log = LogManager.getLogger(StudentsDAOImpl.class);
	public static final String INSERT_INTO_STUDENTS_TABLE_SQL = "INSERT INTO students (first_name, last_name, group_id) VALUES(?,?,?)";
	public static final String FIND_ALL_SQL = "SELECT * FROM students";
	String FIND_ALL_BY_COURSE_NAME_SQL =
			"SELECT students_table.id,\n" +
					"    first_name,\n" +
					"    last_name,\n" +
					"    group_id\n" +
					"FROM students AS students_table\n" +
					"JOIN students_courses AS students_courses_table ON students_table.id = students_courses_table.student_id\n" +
					"JOIN courses AS courses_table ON courses_table.id = students_courses_table.course_id\n" +
					"WHERE courses_table.name = ?";
	private final Manager dataSource;

	public StudentsDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Student> findAll() {
		List<Student> students = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			while (resultSet.next()) {
				Student student = new Student();

				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lstName = resultSet.getString("last_name");
				int groupId = resultSet.getInt("group_id");

				student.setId(id);
				student.setFirstName(firstName);
				student.setLastName(lstName);
				student.setGroupId(groupId);

				students.add(student);
			}
			return students;
		} catch (SQLException e) {
			log.error("Error during findAll() call");
			throw new DAOException("Error during findAll() call", e);
		}
	}

	public void save(List<Student> students) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_STUDENTS_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			for (var student : students) {
				var name = student.getFirstName();
				var lastName = student.getLastName();
				var groupId = student.getGroupId();

				statement.setString(1, name);
				statement.setString(2, lastName);
				if (groupId == 0) {
					statement.setNull(3, Types.NULL);
				} else {
					statement.setInt(3, groupId);
				}
				statement.execute();
			}
		} catch (SQLException ex) {
			log.error("Error during save()");
			throw new DAOException("Error during save()", ex);
		}
	}

	@Override
	public List<Student> findAllBy(String courseName) {
		List<Student> result = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_COURSE_NAME_SQL)) {
			statement.setString(1, courseName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Student student = new Student();

				int studentId = resultSet.getInt("id");
				String studentName = resultSet.getString("first_name");
				String studentLastName = resultSet.getString("last_name");

				student.setId(studentId);
				student.setFirstName(studentName);
				student.setLastName(studentLastName);

				result.add(student);
			}
		} catch (SQLException e) {
			log.error("Error during findAllBy() course name: " + courseName);
			throw new DAOException("Error during findAllBy() course name: " + courseName);
		}
		return result;
	}
}

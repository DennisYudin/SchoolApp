package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;
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
import java.util.Optional;

public class StudentsDAOImpl implements StudentDAO {
	private final Logger log = LogManager.getLogger(StudentsDAOImpl.class);

	public static final String ID_COLUMN = "id";
	public static final String FIRST_NAME_COLUMN = "first_name";
	public static final String LAST_NAME_COLUMN = "last_name";
	public static final String GROUP_ID_COLUMN = "group_id";

	private static final String INSERT_INTO_STUDENTS_TABLE_SQL = "INSERT INTO students (first_name, last_name, group_id) VALUES(?,?,?)";
	private static final String FIND_ALL_SQL = "SELECT id, first_name, last_name, group_id FROM students";
	private static final String GET_STUDENT_BY_ID_SQL = "SELECT id, first_name, last_name, group_id FROM students WHERE id = ?";
	private static final String DELETE_STUDENT_BY_ID_SQL = "DELETE FROM students WHERE id = ?";
	private static final String FIND_ALL_BY_COURSE_NAME_SQL =
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
	public void findAllTest() { // test purpose
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {

			var result = statement.executeUpdate(); //SELECT + executeUpdate() wrong way => SQL exception
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<Student> getBy(int id) {
		try (Connection con = dataSource.getConnection();
			 PreparedStatement stmt = con.prepareStatement(GET_STUDENT_BY_ID_SQL)) {
			stmt.setInt(1, id);

			try(ResultSet resultSet = stmt.executeQuery()) {
				Student student = new Student();
				while (resultSet.next()) {
					int idFromTable = resultSet.getInt(ID_COLUMN);
					String firstName = resultSet.getString(FIRST_NAME_COLUMN);
					String lstName = resultSet.getString(LAST_NAME_COLUMN);
					int groupId = resultSet.getInt(GROUP_ID_COLUMN);

					student.setId(idFromTable);
					student.setFirstName(firstName);
					student.setLastName(lstName);
					student.setGroupId(groupId);
				}
				if (student.getLastName() == null || student.getFirstName() == null) {
					return Optional.empty();
				} else {
					return Optional.of(student);
				}
			}
		} catch (SQLException e) {
			log.error("Error during getBy()");
			throw new DAOException("Error during getBy()", e);
		}
	}

	@Override
	public List<Student> findAll() {
		List<Student> students = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			while (resultSet.next()) {
				Student student = new Student();

				int id = resultSet.getInt(ID_COLUMN);
				String firstName = resultSet.getString(FIRST_NAME_COLUMN);
				String lstName = resultSet.getString(LAST_NAME_COLUMN);
				int groupId = resultSet.getInt(GROUP_ID_COLUMN);

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

	@Override
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
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException ex) {
			log.error("Error during save list of students");
			throw new DAOException("Error during save list of students", ex);
		}
	}

	@Override
	public void save(Student student) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_STUDENTS_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
			statement.executeUpdate();
		} catch (SQLException ex) {
			log.error("Error during save single student");
			throw new DAOException("Error during save single student", ex);
		}
	}

	@Override
	public List<StudentDTO> findAllBy(String courseName) {
		List<StudentDTO> result = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_COURSE_NAME_SQL)) {
			statement.setString(1, courseName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				StudentDTO student = new StudentDTO();

				int studentId = resultSet.getInt(ID_COLUMN);
				String studentName = resultSet.getString(FIRST_NAME_COLUMN);
				String studentLastName = resultSet.getString(LAST_NAME_COLUMN);

				student.setId(studentId);
				student.setFirstName(studentName);
				student.setLastName(studentLastName);

				result.add(student);
			}
			return result;
		} catch (SQLException e) {
			log.error("Error during findAllBy() course name: " + courseName);
			throw new DAOException("Error during findAllBy() course name: " + courseName);
		}
	}

	@Override
	public void deleteById(int id) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID_SQL)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Error during delete by id: " + id);
			throw new DAOException("Error during delete by id: " + id, e);
		}
	}
}

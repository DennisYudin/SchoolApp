package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Group;
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

public class StudentsDAOImpl implements StudentDAO {
	public static final String INSERT_INTO_STUDENTS_TABLE_SQL = "INSERT INTO students (first_name, last_name, group_id) VALUES(?,?,?)";
	private final Logger log = LogManager.getLogger(StudentsDAOImpl.class);
	public static final String FIND_ALL_SQL = "SELECT * FROM students";
	public static final String INSERT_INTO_COURSES_TABLE_SQL = "INSERT INTO courses (name, description) VALUES(?,?)";
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

				long id = resultSet.getLong("id");
				String firstName = resultSet.getString("first_name");
				String lstName = resultSet.getString("last_name");
				String groupId = resultSet.getString("group_id");

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

	public void save(List<Group> groups) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_STUDENTS_TABLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			int amountGroups = groups.size();
//			for (int currentGroup = 0; currentGroup < amountGroups; currentGroup++) {
//				int amountStudentsInGroup = groups.get(currentGroup).getStudents().size();
//
//				setStudentsInTable(amountStudentsInGroup, statement, groupsData, currentGroup);
//			}
//			int amountStudents = studentsData.size();
//			setStudentsWithoutGroup(amountStudents, statement, studentsData);

		} catch (SQLException ex) {
			throw new DAOException("Could not insert student into student table", ex);
		}
	}

	private void setStudentsInTable(int amountStudents, PreparedStatement preparedStatement,List<Group> dataInput, int actualGroup)
			throws SQLException {
//		for (int currentStudent = 0; currentStudent < amountStudents; currentStudent++) {
//			Group group = dataInput.get(actualGroup);
//			Student student = group.getStudentsInGroup().get(currentStudent);
//
//			String firstName = student.getFirstName();
//			String lastName = student.getLastName();
//			long groupId = group.getGroupId();
//
//			preparedStatement.setString(1, firstName);
//			preparedStatement.setString(2, lastName);
//			preparedStatement.setLong(3, groupId);
//
//			preparedStatement.execute();
//
//			getStudentId(preparedStatement, student);
//	}
		}


	private void setStudentsWithoutGroup(int quantityStudents, PreparedStatement preparedStatement,
										 List<Student> dataInput) throws SQLException {
		for (int currentStudentWithoutGroup = 0; currentStudentWithoutGroup < quantityStudents; currentStudentWithoutGroup++) {
			Student student = dataInput.get(currentStudentWithoutGroup);
			String firstName = student.getFirstName();
			String lastName = student.getLastName();

			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setNull(3, java.sql.Types.INTEGER);

			preparedStatement.execute();

			getStudentId(preparedStatement, student);
		}
	}

	private void getStudentId(PreparedStatement preparedStatement, Student studentObject) {
//		try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
//			if (resultSet.next()) {
//				long studentId = resultSet.getLong("student_id");
//				studentObject.setStudentId(studentId);
//			} else {
//				throw new WrongDataException("Failed, no ID obtained.");
//			}
//		} catch (SQLException ex) {
//			throw new WrongDataException("Could not get student ID", ex);
//		}
	}
}

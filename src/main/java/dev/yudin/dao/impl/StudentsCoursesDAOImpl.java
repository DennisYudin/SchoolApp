package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.entities.StudentCourseDTO;
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

public class StudentsCoursesDAOImpl implements StudentsCoursesDAO {
	private final Logger log = LogManager.getLogger(StudentsCoursesDAOImpl.class);
	public static final String FIND_ALL_SQL = "SELECT * FROM students_courses";
	public static final String INSERT_INTO_TABLES_SQL = "INSERT INTO students_courses (student_id, course_id) VALUES(?,?)";
	private final Manager dataSource;

	public StudentsCoursesDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<StudentCourseDTO> findAll() {
		List<StudentCourseDTO> dtos = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL)) {
			while (resultSet.next()) {
				StudentCourseDTO dto = new StudentCourseDTO();

				int studentId = resultSet.getInt("student_id");
				int courseId = resultSet.getInt("course_id");

				dto.setStudentId(studentId);
				dto.setCourseId(courseId);

				dtos.add(dto);
			}
			return dtos;
		} catch (SQLException e) {
			log.error("Error during findAll() call");
			throw new DAOException("Error during findAll() call", e);
		}
	}

	@Override
	public void save(List<StudentCourseDTO> studentCourseDTOS) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_TABLES_SQL, Statement.RETURN_GENERATED_KEYS)) {
			for (var studentCourseDTO : studentCourseDTOS) {
				statement.setInt(1, studentCourseDTO.getStudentId());
				statement.setInt(2, studentCourseDTO.getCourseId());
				statement.execute();
			}
		} catch (SQLException ex) {
			log.error("Error during save()");
			throw new DAOException("Error during save()", ex);
		}
	}
}

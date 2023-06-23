package dev.yudin.dao.impl;

import dev.yudin.ConnectionManager;
import dev.yudin.entities.Course;
import dev.yudin.exceptions.AppConfigurationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAOImpl {

	ConnectionManager dataSource = new ConnectionManager();

	public List<Course> findAll() {
		String sqlQuery = "SELECT * FROM courses";

		List <Course> courseList = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
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
			throw new AppConfigurationException("Could not get all courses", e);
		}
		return courseList;
	}
}

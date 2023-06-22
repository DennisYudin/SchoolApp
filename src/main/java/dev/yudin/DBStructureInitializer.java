package dev.yudin;

import dev.yudin.entities.Course;
import dev.yudin.exceptions.AppConfigurationException;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBStructureInitializer {
	Reader reader = new FileReader();
	ConnectionManager connectionManager = new ConnectionManager();
	Runnable scriptRunner = new ScriptExecutor(connectionManager, reader);

	public void init() {
		scriptRunner.run("databaseStructure.sql");
	}

	public List<Course> mapToObject(String input) {

		var courses = reader.read(input);

		return courses.stream()
				.map(value -> value.split("-"))
				.map(this::convert)
				.collect(Collectors.toList());
	}

	public Course convert(String[] array) {
		Course course = new Course();
		course.setName(array[0]);
		course.setDescription(array[1]);
		return course;
	}

	public void fillInCourseTable(List<Course> courses) {

		String sqlQuery = "" +
				"INSERT INTO courses (name, description) " +
				"VALUES(?,?)";

		try (Connection connection = connectionManager.getConnection("org.h2.Driver", "jdbc:h2:mem:schooldb");
			 PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

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
				} catch (SQLException ex) {
					throw new AppConfigurationException("Could not get course ID", ex);
				}
			}
		} catch (SQLException ex) {
			throw new AppConfigurationException("Could not insert course into course table", ex);
		}
	}
}

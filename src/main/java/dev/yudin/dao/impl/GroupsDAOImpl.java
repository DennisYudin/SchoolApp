package dev.yudin.dao.impl;

import dev.yudin.connection.Manager;
import dev.yudin.dao.GroupDAO;
import dev.yudin.entities.Group;
import dev.yudin.entities.GroupsAmountStudentDTO;
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

public class GroupsDAOImpl implements GroupDAO {
	private final Logger log = LogManager.getLogger(GroupsDAOImpl.class);

	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";

	public static final String INSERT_INTO_GROUPS_TABLES_SQL = "INSERT INTO groups (name) VALUES(?)";
	public static final String FIND_ALL_SQL = "SELECT id, name FROM groups";
	public static final String FIND_ALL_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS_SQL =
			"SELECT name,\n" +
					"    COUNT(*)\n" +
					"FROM groups\n" +
					"LEFT JOIN students ON students.group_id = groups.id\n" +
					"GROUP BY name\n" +
					"HAVING COUNT(*) <= ?\n" +
					"ORDER BY name";

	private final Manager dataSource;

	public GroupsDAOImpl(Manager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Group> findAll() {
		List<Group> groups = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement statement = conn.prepareStatement(FIND_ALL_SQL);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Group group = new Group();

				int id = resultSet.getInt(ID_COLUMN);
				String name = resultSet.getString(NAME_COLUMN);

				group.setId(id);
				group.setName(name);

				groups.add(group);
			}
			return groups;
		} catch (SQLException e) {
			log.error("Error during getting all groups");
			throw new DAOException("Error during getting all groups", e);
		}
	}

	@Override
	public void save(List<String> groups) {
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 INSERT_INTO_GROUPS_TABLES_SQL, Statement.RETURN_GENERATED_KEYS)) {
			for (String name : groups) {
				statement.setString(1, name);
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException ex) {
			log.error("Error during saving...");
			throw new DAOException("Error during saving...", ex);
		}
	}

	@Override
	public List<GroupsAmountStudentDTO> findAll(int amountStudents) {
		List<GroupsAmountStudentDTO> result = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 FIND_ALL_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS_SQL)) {

			statement.setInt(1, amountStudents);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				GroupsAmountStudentDTO dto = new GroupsAmountStudentDTO();

				String name = resultSet.getString("name");
				int amount = resultSet.getInt("count(*)");

				dto.setGroupName(name);
				dto.setAmountStudents(amount);

				result.add(dto);
			}
			return result;
		} catch (SQLException e) {
			log.error("Error during getting all groups for amount of students: " + amountStudents);
			throw new DAOException("Error during getting all groups for amount of students: " + amountStudents, e);
		}
	}
}

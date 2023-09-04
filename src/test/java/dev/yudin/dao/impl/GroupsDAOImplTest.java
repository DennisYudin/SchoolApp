package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.GroupDAO;
import dev.yudin.entities.Group;
import dev.yudin.entities.GroupsAmountStudentDTO;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupsDAOImplTest {

	private GroupDAO groupsDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");
		scriptRunner.run("fillGroupTable.sql");
		scriptRunner.run("fillStudentTable.sql");

		groupsDAO = new GroupsDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnListOfStudentsFromTable_WhenCallMethod() {
		List<Group> actual = groupsDAO.findAll();
		var actualGroup = actual.get(0);

		Group expectedGroup = new Group();
		expectedGroup.setId(1);
		expectedGroup.setName("AA-01");

		assertEquals(expectedGroup, actualGroup);
	}

	@Test
	@Order(1)
	void save_ShouldSaveDataIntoGroupsTable_WhenInputIsListOfObjects() {
		List<String> newGroups = List.of("XX-XX");

		groupsDAO.save(newGroups);

		List<Group> actualGroups = groupsDAO.findAll();

		Group firstGroup = new Group();
		firstGroup.setId(1);
		firstGroup.setName("AA-01");
		Group secondGroup = new Group();
		secondGroup.setId(2);
		secondGroup.setName("XX-XX");

		var expectedGroups = List.of(
				firstGroup,
				secondGroup
		);
		assertEquals(expectedGroups, actualGroups);
	}

	@Test
	void findAll_ShouldReturnListOfGroupsWithLessOrEqualsAmountOfStudents_WhenInputIsAmountOfStudents() {
		var actual = groupsDAO.findAll(1);
		var actualDTO = actual.get(0);

		GroupsAmountStudentDTO expectedDTO = new GroupsAmountStudentDTO();
		expectedDTO.setGroupName("AA-01");
		expectedDTO.setAmountStudents(1);

		assertEquals(expectedDTO, actualDTO);
	}
	@Test
	void findAll_ShouldReturnEmptyListOfGroups_WhenInputIsZeroAmountOfStudents() {
		var actual = groupsDAO.findAll(0);

		assertTrue(actual.isEmpty());
	}
}
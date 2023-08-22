package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.GroupDAO;
import dev.yudin.entities.Group;
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

	GroupDAO groupsDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);

		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");

		groupsDAO = new GroupsDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnEmptyList_WhenCallMethod() {

		List<Group> actual = groupsDAO.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Order(1)
	void save_ShouldSaveDataIntoGroupsTable_WhenInputIsListOfObjects() {
		List<Group> groupsTableBefore = groupsDAO.findAll();

		assumeTrue(groupsTableBefore.isEmpty());

		List<String> groups = List.of("XX-XX");

		groupsDAO.save(groups);

		List<Group> groupsTableAfter = groupsDAO.findAll();

		assertFalse(groupsTableAfter.isEmpty());
	}

	@Test
	@Order(2)
	void findAll_ShouldReturnListOfGroups_WhenCallMethod() {
		List<String> listExpectedGroups = List.of("XX-XX");
		Group expectedGroup = new Group();
		expectedGroup.setId(1);
		expectedGroup.setName(listExpectedGroups.get(0));

		groupsDAO.save(listExpectedGroups);

		List<Group> actual = groupsDAO.findAll();
		var actualGroup = actual.get(0);

		assertEquals(expectedGroup, actualGroup);
	}
}
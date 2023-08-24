package dev.yudin.services.Impl;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.GroupDAO;
import dev.yudin.dao.impl.GroupsDAOImpl;
import dev.yudin.entities.Group;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import dev.yudin.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GroupServiceImplTest {
	private GroupService groupService;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");

		GroupDAO groupsDAO = new GroupsDAOImpl(manager);
		groupService = new GroupServiceImpl(groupsDAO);
	}

	@Test
	void convert_ShouldConvertListIntoMap_WhenInputIsListOfGroups() {
		Group group = new Group();
		group.setId(1);
		group.setName("XX-XX");

		var actual = groupService.convert(List.of(group));

		assertTrue(actual.containsKey("XX-XX"));
		assertEquals(1, actual.get("XX-XX"));
	}
}
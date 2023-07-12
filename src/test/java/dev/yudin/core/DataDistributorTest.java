package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.entities.Group;
import dev.yudin.entities.Student;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

class DataDistributorTest {

	@Test
	void assignStudentsIntoCourses_ShouldAssignStudentsIntoCourses_WhenInputIsListOfStudentsAndListOfCourses() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		var actualListStudentsWithCourses = distributor.assignStudentsIntoCourses(dataGenerator.generateStudents(200), dataGenerator.getCourses());

		for (var currentStudent : actualListStudentsWithCourses) {
			int expectedAmountOfCourses = currentStudent.getCourses().size();

			assertTrue(expectedAmountOfCourses > 0);
		}
	}

	@Test
	void assignStudentsIntoGroups_ShouldReturnSevenGroupsWithStudentsAndThreeEmptyGroups_WhenInputIsListOfGroupsAndListOfStudents() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		Set<String> groups = dataGenerator.generateGroups(10);
		Set<Student> students = dataGenerator.generateStudents(200);

		List<Group> groupsWithStudents = distributor.assignStudentsIntoGroups(
				new ArrayList<>(groups), students);

		groupsWithStudents.stream().forEach(System.out::println);

		int expectedAmountGroups = 10;
		int actualAmountGroups = groupsWithStudents.size();

		assertEquals(expectedAmountGroups, actualAmountGroups);

		for (int i = 0; i < 7; i++) {
			var currentGroup = groupsWithStudents.get(i);
			int ActualGroupSizeWithStudent = currentGroup.getStudents().size();

			assertTrue(ActualGroupSizeWithStudent > 0);
		}
		for (int i = 7; i <= 9; i++) {
			var currentGroup = groupsWithStudents.get(i);

			int expectedSize = 0;
			int actualSizeEmptyGroups = currentGroup.getStudents().size();

			assertEquals(expectedSize, actualSizeEmptyGroups);
		}
	}

	@Test
	void getStudentsWithoutGroups() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		Set<String> groups = dataGenerator.generateGroups(10);
		Set<Student> students = dataGenerator.generateStudents(200);

		List<Group> groupsWithStudents = distributor.assignStudentsIntoGroups(
				new ArrayList<>(groups), students);

		var actual = distributor.getStudentsWithoutGroups(groupsWithStudents, students);
		
		assertEquals(199, actual.size());
	}
}
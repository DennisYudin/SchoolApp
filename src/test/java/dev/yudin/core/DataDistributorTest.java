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
	public static final int MAX_AMOUNT_COURSES_PER_STUDENT = 3;
	public static final int MIX_AMOUNT_OF_COURSES = 0;

	@Test
	void assignStudentsIntoCourses_ShouldAssignStudentsIntoCourses_WhenInputIsListOfStudentsAndListOfCourses() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		var actualListStudentsWithCourses = distributor.assignStudentsIntoCourses(dataGenerator.generateStudents(200), dataGenerator.getCourses());

		for (var currentStudent : actualListStudentsWithCourses) {
			int expectedAmountOfCourses = currentStudent.getCourses().size();
			assertTrue(expectedAmountOfCourses > MIX_AMOUNT_OF_COURSES && expectedAmountOfCourses <= MAX_AMOUNT_COURSES_PER_STUDENT);
		}
	}

	@Test
	void assignStudentsIntoGroups_ShouldReturnSevenGroupsWithStudentsAndThreeEmptyGroups_WhenInputIsListOfGroupsAndListOfStudents() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		List<String> groups = dataGenerator.generateGroups(10);
		Set<Student> students = dataGenerator.generateStudents(200);

		List<Group> groupsWithStudents = distributor.assignStudentsIntoGroups(groups, students);

		int expectedAmountGroups = 10;
		int actualAmountGroups = groupsWithStudents.size();

		assertEquals(expectedAmountGroups, actualAmountGroups);

		for (int i = 0; i < 7; i++) {
			var currentGroupWithStudent = groupsWithStudents.get(i);
			int actualGroupSizeWithStudent = currentGroupWithStudent.getStudents().size();

			assertTrue(actualGroupSizeWithStudent > 0);
		}
		for (int i = 7; i <= 9; i++) {
			var currentGroupWithoutStudents = groupsWithStudents.get(i);

			int expectedSize = 0;
			int actualSizeEmptyGroups = currentGroupWithoutStudents.getStudents().size();

			assertEquals(expectedSize, actualSizeEmptyGroups);
		}
	}

	@Test
	void getStudentsWithoutGroups_ShouldReturnListOfStudentWithoutGroups_WhenInputIsGroupsWithStudentsAndAllStudents() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		List<String> groups = dataGenerator.generateGroups(10);
		Set<Student> students = dataGenerator.generateStudents(200);

		List<Group> groupsWithStudents = distributor.assignStudentsIntoGroups(groups, students);

		var actual = distributor.getStudentsWithoutGroups(groupsWithStudents, students);

		int actualAmountStudentsWithoutGroups = actual.size();

		assertTrue(actualAmountStudentsWithoutGroups > 0 && actualAmountStudentsWithoutGroups < 50);
	}
}
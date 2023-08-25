package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.yudin.entities.Course;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class DataDistributorTest {
	public static final int MAX_AMOUNT_COURSES_PER_STUDENT = 3;
	public static final int MIX_AMOUNT_OF_COURSES = 0;

	@Test
	void merge_ShouldPrepareDataForSavingIntoStudentsCoursesTable_WhenInputIsStudentsWithCoursesAndStudentIdMapAndCoursesMap() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);
		DataDistributor distributor = new DataDistributor(random);

		Set<Student> students = dataGenerator.generateStudents(2);
		List<Course> courses = dataGenerator.getCourses();
		var studentsWithCourses = distributor.assignStudentsIntoCourses(students, courses);

		var courseNameID = Map.ofEntries(
			Map.entry("Algebra", 1),
			Map.entry("Biology", 2),
			Map.entry("Drawing", 3),
			Map.entry("Chemistry", 4),
			Map.entry("Geography", 5),
			Map.entry("Geometry", 6),
			Map.entry("History", 7),
			Map.entry("Literature", 8),
			Map.entry("Mathematics", 9),
			Map.entry("Music", 10)
		);
		Map<Student, Integer> studentID = convert(students);

		var actual = distributor.merge(studentsWithCourses, studentID, courseNameID);

		assertFalse(actual.isEmpty());
	}

	private Map<Student, Integer> convert(Set<Student> students) {
		generateIDs(students);
		Map<Student, Integer> studentID = new HashMap<>();
		students.forEach(student -> studentID.put(student, student.getId()));
		return studentID;
	}

	private void generateIDs(Set<Student> students) {
		int id = 1;
		for (var student : students) {
			student.setId(id);
			id++;
		}
	}

	@Test
	void merge_ShouldCombineStudentsIntoOneList_WhenInputIsListOfStudentsWithGroupsAndStudentsWithoutGroupsAndMap() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);
		DataDistributor distributor = new DataDistributor(random);

		List<String> groups = dataGenerator.generateGroups(10);
		Set<Student> allStudents = dataGenerator.generateStudents(200);

		List<Group> groupsWithStudents = distributor.assignStudentsIntoGroups(groups, allStudents);
		Set<Student> studentsWithoutGroups = distributor.getStudentsWithoutGroups(groupsWithStudents, allStudents);

		Map<String, Integer> map = new HashMap<>();
		int i = 1;
		for (var name : groups) {
			map.put(name, i);
			i++;
		}

		var actual = distributor.merge(groupsWithStudents, studentsWithoutGroups, map);

		assertEquals(200, actual.size());
	}

	@Test
	void assignStudentsIntoCourses_ShouldAssignStudentsIntoCourses_WhenInputIsListOfStudentsAndListOfCourses() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		DataDistributor distributor = new DataDistributor(random);

		var actualListStudentsWithCourses = distributor.assignStudentsIntoCourses(dataGenerator.generateStudents(200), dataGenerator.getCourses());

		for (var currentStudent : actualListStudentsWithCourses) {
			int expectedAmountOfCourses = currentStudent.getCourses().size();
			assertTrue(expectedAmountOfCourses > MIX_AMOUNT_OF_COURSES
					&& expectedAmountOfCourses <= MAX_AMOUNT_COURSES_PER_STUDENT);
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

		assertTrue(actualAmountStudentsWithoutGroups > 0 && actualAmountStudentsWithoutGroups < 55);
	}
}
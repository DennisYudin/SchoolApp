package dev.yudin.core;

import dev.yudin.entities.Course;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DataDistributor {
	private static final int AMOUNT_GROUPS_WITH_STUDENTS = 7;
	private static final int AMOUNT_GROUPS_WITHOUT_STUDENTS = 7;
	private static final int SELECTION_BOUNDARY = 3;
	private static final int MIN_AMOUNT_STUDENTS = 15;
	private static final int MAX_AMOUNT_STUDENTS = 30;
	private Random random;

	public DataDistributor(Random random) {
		this.random = random;
	}

	public List<Group> assignStudentsIntoGroups(List<String> groups, Set<Student> students) {
		List<Group> result = new ArrayList<>();
		Iterator<Student> studentsIterator = students.iterator();
		for (int currentGroupWithStudents = 0;
			 currentGroupWithStudents < AMOUNT_GROUPS_WITH_STUDENTS; currentGroupWithStudents++) {
			String groupName = groups.get(currentGroupWithStudents);
			int amountStudentsInGroup = random.nextInt(
					(MAX_AMOUNT_STUDENTS - MIN_AMOUNT_STUDENTS) + 1) + MIN_AMOUNT_STUDENTS;
			Group group = new Group();
			group.setName(groupName);
			for (int currentStudent = 0; currentStudent < amountStudentsInGroup; currentStudent++) {
				if (studentsIterator.hasNext()) {
					Student student = studentsIterator.next();
					List<Student> studentsList = group.getStudents();
					studentsList.add(student);
				}
			}
			result.add(group);
		}
		int amountGroups = groups.size();
		for (int currentGroupWithoutStudents = AMOUNT_GROUPS_WITHOUT_STUDENTS;
			 currentGroupWithoutStudents < amountGroups; currentGroupWithoutStudents++) {
			String name = groups.get(currentGroupWithoutStudents);

			Group group = new Group();
			group.setName(name);

			result.add(group);
		}
		return result;
	}

	public Set<Student> getStudentsWithoutGroups(List<Group> studentsWithGroups, Set<Student> allStudents) {
		for (var currentGroup : studentsWithGroups) {
			List<Student> studentsInGroup = currentGroup.getStudents();
			for (var student : studentsInGroup) {
				allStudents.remove(student);
			}
		}
		return allStudents;
	}

	public List<Student> assignStudentsIntoCourses(Set<Student> allStudents, List<Course> allCourses) {
		List<Student> result = new ArrayList<>();
		for (Student student : allStudents) {
			int amountCourses = random.nextInt(SELECTION_BOUNDARY) + 1;
			while (student.getCourses().size() < amountCourses) {
				int coursesSample = allCourses.size();
				int randomCourse = random.nextInt(coursesSample);
				Course courseName = allCourses.get(randomCourse);

				if (!student.getCourses().contains(courseName)) {
					student.getCourses().add(courseName);
				}
			}
			result.add(student);
		}
		return result;
	}
}

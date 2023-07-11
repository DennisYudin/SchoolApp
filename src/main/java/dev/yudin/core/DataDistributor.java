package dev.yudin.core;

import dev.yudin.entities.Course;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DataDistributor {
	private static final int AMOUNT_GROUPS_WITH_STUDENTS = 7;
	private static final int AMOUNT_GROUPS_WITHOUT_STUDENTS = 7;
	private static final int SELECTION_BOUNDARY = 3;
	private static final int MIN_AMOUNT_STUDENTS = 15;
	private static final int MAX_AMOUNT_STUDENTS = 30;

	private Random random = new Random();
	private List<Group> groupsWithStudents = new ArrayList<>();
	private List<Student> studentsWithoutGroups = new ArrayList<>();
	private List<Student> studentsWithCourses = new ArrayList<>();

	public List<Group> getGroupsWithStudents() {
		return groupsWithStudents;
	}

	public List<Student> getStudentsWithoutGroups() {
		return studentsWithoutGroups;
	}

	public List<Student> getStudentsWithCourses() {
		return studentsWithCourses;
	}

	public void assignStudentsIntoGroups(List<String> groups, List<Student> students) {
		Iterator<Student> iteratorStudents = students.iterator();
		int amountGroups = groups.size();

		for (int currentGroup = 0; currentGroup < AMOUNT_GROUPS_WITH_STUDENTS; currentGroup++) {
			String groupName = groups.get(currentGroup);
			int amountStudentsInGroup = random.nextInt((MAX_AMOUNT_STUDENTS - MIN_AMOUNT_STUDENTS) + 1)
					+ MIN_AMOUNT_STUDENTS;

			Group group = new Group();
			group.setName(groupName);

			for (int currentStudent = 0; currentStudent < amountStudentsInGroup; currentStudent++) {
				if (iteratorStudents.hasNext()) {
					Student student = iteratorStudents.next();
					List<Student> studentsList = group.getStudents();
					studentsList.add(student);
				}
			}
			groupsWithStudents.add(group);
		}
		for (int currentGroupWithoutStudents = AMOUNT_GROUPS_WITHOUT_STUDENTS; currentGroupWithoutStudents < amountGroups;
			 currentGroupWithoutStudents++) {
			String name = groups.get(currentGroupWithoutStudents);

			Group group = new Group();
			group.setName(name);

			groupsWithStudents.add(group);
		}
		while (iteratorStudents.hasNext()) {
			Student student = iteratorStudents.next();
			studentsWithoutGroups.add(student);
		}
	}

	private void assignStudentsIntoCourses(List<Student> allStudents, List<Course> allCourses) {
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
			studentsWithCourses.add(student);
		}
	}
}

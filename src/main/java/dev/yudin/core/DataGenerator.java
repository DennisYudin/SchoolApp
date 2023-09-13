package dev.yudin.core;


import dev.yudin.entities.Course;
import dev.yudin.entities.Student;
import dev.yudin.filereader.Reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DataGenerator {
	private static final String SYMBOLS_FOR_SELECTION = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS_FOR_SELECTION = "0123456789";
	private static final int CHAR_LENGTH = 2;
	public static final String STUDENTS_NAMES_FILE = "students_names.txt";
	public static final String STUDENTS_SURNAMES_FILE = "students_surnames.txt";
	public static final String COURSES_FILE = "courses.txt";

	private Random random;
	private Reader reader;

	public DataGenerator(Random random, Reader reader) {
		this.random = random;
		this.reader = reader;
	}

	public List<String> generateGroups(int amountGroups) {
		Set<String> groups = new HashSet<>();

		StringBuilder name = new StringBuilder();

		while (groups.size() < amountGroups) {
			getTwoRandomChar(name, SYMBOLS_FOR_SELECTION, CHAR_LENGTH);
			name.append("-");
			getTwoRandomChar(name, NUMBERS_FOR_SELECTION, CHAR_LENGTH);

			groups.add(name.toString());

			name.delete(0, name.length());
		}
		return new ArrayList<>(groups);
	}

	private StringBuilder getTwoRandomChar(StringBuilder result, String characterData, int amount) {
		for (int i = 0; i < amount; i++) {
			int symbolSampleSize = characterData.length();
			int randomSymbol = random.nextInt(symbolSampleSize);
			char symbol = characterData.charAt(randomSymbol);

			result.append(symbol);
		}
		return result;
	}

	public Set<Student> generateStudents(int amountStudents) {
		List<String> names = reader.read(STUDENTS_NAMES_FILE);
		List<String> surnames = reader.read(STUDENTS_SURNAMES_FILE);

		Set<Student> result = new HashSet<>();
		int nameSampleSize = names.size();
		int surnameSampleSize = surnames.size();

		while (result.size() < amountStudents) {
			int randomName = random.nextInt(nameSampleSize);
			int randomSurname = random.nextInt(surnameSampleSize);
			String firstName = names.get(randomName);
			String lastName = surnames.get(randomSurname);

			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);

			result.add(student);
		}
		return result;
	}

	public List<Course> getCourses() {
		List<String> courses = reader.read(COURSES_FILE);
		return map(courses);
	}

	private List<Course> map(List<String> input) {
		return input.stream()
				.map(value ->  value.split("-"))
				.map(this::mapToEntity)
				.collect(Collectors.toList());
	}

	private Course mapToEntity(String[] array) {
		Course course = new Course();
		course.setName(array[0]);
		course.setDescription(array[1]);
		return course;
	}
}

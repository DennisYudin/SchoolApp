package dev.yudin.core;



import dev.yudin.entities.Course;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;
import dev.yudin.filereader.Reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DataGenerator {
    private static final String SYMBOLS_FOR_SELECTION = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS_FOR_SELECTION = "0123456789";
    private static final int CHAR_LENGTH = 2;
    private static final int AMOUNT_GROUPS = 10;
    private static final int AMOUNT_STUDENTS = 200;
    private static final int AMOUNT_COURSES = 10;
    private static final int AMOUNT_GROUPS_WITH_STUDENTS = 7;
    private static final int AMOUNT_GROUPS_WITHOUT_STUDENTS = 7;
    private static final int SELECTION_BOUNDARY = 3;
    private static final int MIN_AMOUNT_STUDENTS = 15;
    private static final int MAX_AMOUNT_STUDENTS = 30;
    public static final String SPLIT_SYMBOL = " ";

    private Random random = new Random();


    private List<Group> groupsWithStudents = new ArrayList<>();
    private List<Student> studentsWithoutGroups = new ArrayList<>();
    private List<Student> studentsWithCourses = new ArrayList<>();
    private Reader reader;

    public DataGenerator(Reader reader) {
        this.reader = reader;
    }

    public List<Group> getGroupsWithStudents() {
        return groupsWithStudents;
    }
    
    public List<Student> getStudentsWithoutGroups() {
        return studentsWithoutGroups;
    }
    
    public List<Student> getStudentsWithCourses() {
        return studentsWithCourses;
    }
    
    public void collectData() {
        generateGroups(AMOUNT_GROUPS);
//        generateStudents(AMOUNT_STUDENTS);
//        generateCourses(AMOUNT_COURSES);
//
//        assignStudentsIntoCourses(studentsData, coursesData);
//        assignStudentsIntoGroups(groups, studentsData);
    }
    
    public List<String> generateGroups(int amountGroups) {
        List<String> groups = new ArrayList<>();

        StringBuilder name = new StringBuilder();
        
        while (groups.size() < amountGroups) {
            getTwoRandomChar(name, SYMBOLS_FOR_SELECTION, CHAR_LENGTH);
            name.append("-");
            getTwoRandomChar(name, NUMBERS_FOR_SELECTION, CHAR_LENGTH);
            if (!groups.contains(name.toString())) {
                groups.add(name.toString());
            }
            name.delete(0, name.length());
        }
        return groups;
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

    public List<Student> generateStudents() {
        var studentsList = reader.read("src/main/resources/students.txt");
        return mapToEntity(studentsList);
    }

    public List<Student> mapToEntity(List<String> students) {
        return students.stream()
                .map(value -> value.split(SPLIT_SYMBOL))
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public Student convert(String[] array) {
        Student student = new Student();
        student.setFirstName(array[0]);
        student.setLastName(array[1]);
        return student;
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


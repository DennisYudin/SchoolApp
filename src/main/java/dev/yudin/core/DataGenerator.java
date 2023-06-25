package dev.yudin.core;



import dev.yudin.entities.Course;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final String SYMBOLS_FOR_CHOOSE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS_FOR_CHOOSE = "0123456789";
    private static final int CHAR_LENGTH = 2;
    private static final int AMOUNT_GROUPS = 10;
    private static final int AMOUNT_STUDENTS = 200;
    private static final int AMOUNT_COURSES = 10;
    private static final int AMOUNT_GROUPS_WITH_STUDENTS = 7;
    private static final int AMOUNT_GROUPS_WITHOUT_STUDENTS = 7;
    private static final int SELECTION_BOUNDARY = 3;
    private static final int MIN_AMOUNT_STUDENTS = 15;
    private static final int MAX_AMOUNT_STUDENTS = 30;
    
    private static final List<String> STUDENT_NAMES = new ArrayList<>();
    private static final List<String> STUDENT_SURNAMES = new ArrayList<>();
    private static final List<String> COURSE_NAMES = new ArrayList<>();
    private static final List<String> COURSE_DESCRIPTION = new ArrayList<>();
    static {
        STUDENT_NAMES.add("Morgan");
        STUDENT_NAMES.add("Leonardo");
        STUDENT_NAMES.add("Robert");
        STUDENT_NAMES.add("Brad");
        STUDENT_NAMES.add("Michael");
        STUDENT_NAMES.add("Matt");
        STUDENT_NAMES.add("Tom");
        STUDENT_NAMES.add("Christian");
        STUDENT_NAMES.add("Al");
        STUDENT_NAMES.add("Gary");
        STUDENT_NAMES.add("Edward");
        STUDENT_NAMES.add("Harrison");
        STUDENT_NAMES.add("Johnny");
        STUDENT_NAMES.add("Cillian");
        STUDENT_NAMES.add("Jack");
        STUDENT_NAMES.add("Bruce");
        STUDENT_NAMES.add("Ralph");
        STUDENT_NAMES.add("Kevin");
        STUDENT_NAMES.add("Samuel");
        STUDENT_NAMES.add("Robert");
        
        STUDENT_SURNAMES.add("Freeman");
        STUDENT_SURNAMES.add("DiCaprio");
        STUDENT_SURNAMES.add("De Niro");
        STUDENT_SURNAMES.add("Pitt");
        STUDENT_SURNAMES.add("Caine");
        STUDENT_SURNAMES.add("Damon");
        STUDENT_SURNAMES.add("Hanks");
        STUDENT_SURNAMES.add("Bale");
        STUDENT_SURNAMES.add("Pacino");
        STUDENT_SURNAMES.add("Oldman");
        STUDENT_SURNAMES.add("Norton");
        STUDENT_SURNAMES.add("Ford");
        STUDENT_SURNAMES.add("Depp");
        STUDENT_SURNAMES.add("Murphy");
        STUDENT_SURNAMES.add("Nicholson");
        STUDENT_SURNAMES.add("Willis");
        STUDENT_SURNAMES.add("Spacey");
        STUDENT_SURNAMES.add("Jackson");
        STUDENT_SURNAMES.add("Duvall");
        STUDENT_SURNAMES.add("Fiennes");
        
        COURSE_NAMES.add("Algebra");
        COURSE_NAMES.add("Biology");
        COURSE_NAMES.add("Drawing");
        COURSE_NAMES.add("Chemistry");
        COURSE_NAMES.add("Geography");
        COURSE_NAMES.add("Geometry");
        COURSE_NAMES.add("History");
        COURSE_NAMES.add("Literature");
        COURSE_NAMES.add("Mathematics");
        COURSE_NAMES.add("Music");
        
        COURSE_DESCRIPTION.add("Difficult subject");
        COURSE_DESCRIPTION.add("Delightful subject");
        COURSE_DESCRIPTION.add("Interesting subject");
        COURSE_DESCRIPTION.add("Boring subject");
        COURSE_DESCRIPTION.add("Depressed subject");
        COURSE_DESCRIPTION.add("Easy to understand subject");
        COURSE_DESCRIPTION.add("Impossible to understand");
        COURSE_DESCRIPTION.add("Hilarious subject");
        COURSE_DESCRIPTION.add("Lovely subject");
        COURSE_DESCRIPTION.add("Odd subject");
    }
    
    private Random random = new Random();
    
    private List<String> groups = new ArrayList<>();
    private List<Student> studentsData = new ArrayList<>();
    private List<Course> coursesData = new ArrayList<>();
    
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
    
    public List<String> getGroups() {
        return groups;
    }
    
    public List<Student> getStudentsData() {
        return studentsData;
    }
    
    public List<Course> getCoursesData() {
        return coursesData;
    }
    
    public void collectData() {
        generateGroups(AMOUNT_GROUPS);
        generateStudents(AMOUNT_STUDENTS);
        generateCourses(AMOUNT_COURSES);
        
        assignStudentsIntoCourses(studentsData, coursesData);
        assignStudentsIntoGroups(groups, studentsData);
    }
    
    public List<String> generateGroups(int amountGroups) {
        List<String> groups = new ArrayList<>();

        StringBuilder name = new StringBuilder();
        
        while (groups.size() < amountGroups) {
            getTwoRandomChar(name, SYMBOLS_FOR_CHOOSE, CHAR_LENGTH);
            name.append("-");
            getTwoRandomChar(name, NUMBERS_FOR_CHOOSE, CHAR_LENGTH);
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

    
    private void assignStudentsIntoGroups(List<String> groups, List<Student> students) {
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


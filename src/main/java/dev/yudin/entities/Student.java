package dev.yudin.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int groupId;
    private final List<Course> courses = new ArrayList<>();
    
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Student() {
        
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id
                && firstName.equals(student.firstName)
                && lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, groupId, courses);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", courses=" + courses +
                '}';
    }
}


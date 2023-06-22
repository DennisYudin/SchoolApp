package dev.yudin.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private long id;
    private String firstName;
    private String lastName;

    private List<Course> coursesList = new ArrayList<>();
    
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Student() {
        
    }
    
    public List<Course> getCoursesList() {
        return coursesList;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
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
    public String toString() {
        return "Student [firstName=" + firstName + ", lastName=" + lastName + "]";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
    }   
}


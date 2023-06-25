package dev.yudin.entities;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private long id;
    private String name;
    private final List<Student> students = new ArrayList<>();
    
    public List<Student> getStudents() {
        return students;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Group [goupName=" + name + ", studentsInGroup=" + students + "]";
    }
}


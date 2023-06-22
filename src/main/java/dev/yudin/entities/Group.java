package dev.yudin.entities;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private long id;
    private String name;

    private List<Student> studentsInGroup = new ArrayList<>();
    
    public List<Student> getStudentsInGroup() {
        return studentsInGroup;
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
        return "Group [goupName=" + name + ", studentsInGroup=" + studentsInGroup + "]";
    }
}


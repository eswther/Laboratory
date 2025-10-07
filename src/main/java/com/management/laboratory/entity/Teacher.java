package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Teacher extends User{

    private int teacherId;
    private String department;
    private String number;
    private String name;
    private List<Student> students = null;

    public Teacher() {
    }

    public Teacher(String account, String password, int permission, String department, String name, String number) {
        super(account, password, permission);
        this.department = department;
        this.name = name;
        this.number = number;
    }

    public Teacher(int userId, String account, String password, int permission, int teacherId, String department, String name, List<Student> students,String number) {
        super(userId, account, password, permission);
        this.teacherId = teacherId;
        this.department = department;
        this.name = name;
        this.students = students;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

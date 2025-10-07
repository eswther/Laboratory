package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 教师实体类
 * 每个教师实体对应多个学生
 */
@Getter
@Setter
public class Teacher extends User{

    private int teacherId;
    private String department;
    private String name;
    private List<Student> students;

    public Teacher() {
    }

    public Teacher(String account, String password, int permission, String department, String name) {
        super(account, password, permission);
        this.department = department;
        this.name = name;
    }

    public Teacher(int userId, String account, String password, int permission, int teacherId, String department, String name, List<Student> students) {
        super(userId, account, password, permission);
        this.teacherId = teacherId;
        this.department = department;
        this.name = name;
        this.students = students;
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

package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 学生实体类
 * 每个学生实体对应一个负责教师
 */
@Getter
@Setter
public class Student extends User{

    private int studentId;
    private String department;
    private String name;
    private String major;
    private Teacher teacher;

    public Student() {
    }

    public Student(String account, String password, int permission, String major, String name, String department) {
        super(account, password, permission);
        this.major = major;
        this.name = name;
        this.department = department;
    }

    public Student(int userId, String account, String password, int permission, int studentId, String department, String name, String major, Teacher teacher) {
        super(userId, account, password, permission);
        this.studentId = studentId;
        this.department = department;
        this.name = name;
        this.major = major;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}

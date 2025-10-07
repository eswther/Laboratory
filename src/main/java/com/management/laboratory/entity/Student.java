package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student extends User{

    private int studentId;
    private String department;
    private String name;
    private String number;
    private String major;
    private Teacher teacher;

    public Student() {
    }

    public Student(String account, String password, int permission, String major, String name, String department, String number) {
        super(account, password, permission);
        this.major = major;
        this.name = name;
        this.department = department;
        this.number = number;
    }

    public Student(int userId, String account, String password, int permission, int studentId, String department, String name, String major, Teacher teacher, String number) {
        super(userId, account, password, permission);
        this.studentId = studentId;
        this.department = department;
        this.name = name;
        this.major = major;
        this.teacher = teacher;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", major='" + major + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}

package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student extends User{

    private String studentId;
    private String department;
    private String major;
}

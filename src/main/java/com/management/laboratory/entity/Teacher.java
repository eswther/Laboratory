package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher extends User{

    private String teacherId;
    private String department;
}

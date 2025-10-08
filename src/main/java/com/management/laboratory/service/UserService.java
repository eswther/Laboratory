package com.management.laboratory.service;

import com.management.laboratory.entity.Admin;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
@Setter
@Getter
@Service
public class UserService {
    User shareUser;
    Student shareStudent;
    Teacher shareTeacher;
    Admin shareAdmin;
}

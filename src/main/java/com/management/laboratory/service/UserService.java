package com.management.laboratory.service;

import com.management.laboratory.entity.Admin;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 * 存储用户信息，提供用户信息获取和设置方法
 */
@Setter
@Getter
@Service
public class UserService {
    User shareUser;
    Student shareStudent;
    Teacher shareTeacher;
    Admin shareAdmin;
}

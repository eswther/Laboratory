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

@Service
public class UserService {
    private ThreadLocal<User> shareUser = new ThreadLocal<>();
    private ThreadLocal<Student> shareStudent = new ThreadLocal<>();
    private ThreadLocal<Teacher> shareTeacher = new ThreadLocal<>();
    private ThreadLocal<Admin> shareAdmin = new ThreadLocal<>();

    public User getShareUser() {
        return shareUser.get();
    }

    public void setShareUser(User shareUser) {
        this.shareUser.set(shareUser);
    }

    public Student getShareStudent() {
        return shareStudent.get();
    }

    public void setShareStudent(Student shareStudent) {
        this.shareStudent.set(shareStudent);
    }

    public Teacher getShareTeacher() {
        return shareTeacher.get();
    }

    public void setShareTeacher(Teacher shareTeacher) {
        this.shareTeacher.set(shareTeacher);
    }

    public Admin getShareAdmin() {
        return shareAdmin.get();
    }

    public void setShareAdmin(Admin shareAdmin) {
        this.shareAdmin.set(shareAdmin);
    }
}

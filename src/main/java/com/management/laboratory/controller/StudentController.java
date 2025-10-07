package com.management.laboratory.controller;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class StudentController {

    @Autowired
    private UserService userService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TeacherMapper teacherMapper;

    Student student;

    @RequestMapping("/register/student")
    public int register(@RequestBody Map<String, String> studentInfo){
        Student newStudent = new Student(userService.getShareUser().getAccount(), userService.getShareUser().getPassword(),
                userService.getShareUser().getPermission(), studentInfo.get("major"),
                studentInfo.get("name"), studentInfo.get("department"), studentInfo.get("number"));
        int result0 = 0;
        int result1 = 0;

        if(studentMapper.selectStudentByNumber(newStudent.getNumber()) != null) {
            result1 = 2; // number already exists
        }else {
            Teacher teacher = teacherMapper.selectTeacherById(Integer.parseInt(studentInfo.get("teacherId")));
            newStudent.setTeacher(teacher);
            student = newStudent;
            userService.setShareStudent(newStudent);
            result0 = userMapper.insertUser(userService.getShareUser());
            result1 = studentMapper.insertStudent(newStudent);
        }
        if (result0 == 1 && result1 == 1) {
            return 1;
        } else if (result1 == 2) {
            return 2; // number already exists
        } else {
            return 0; // registration failed
        }
    }


}

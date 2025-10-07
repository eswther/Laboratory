package com.management.laboratory.controller;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TeacherMapper teacherMapper;

    Teacher teacher;

    @RequestMapping("/register/teacher")
    public int register(@RequestBody Map<String, String> teacherInfo){
        Teacher newTeacher = new Teacher(userService.getShareUser().getAccount(), userService.getShareUser().getPassword(),
                userService.getShareUser().getPermission(), teacherInfo.get("department"), teacherInfo.get("name"), teacherInfo.get("number"));
        int result0 = 0;
        int result1 = 0;
        List<Student> students = null;
        newTeacher.setStudents(students);
        if(studentMapper.selectStudentByNumber(newTeacher.getNumber()) != null) {
            result1 = 2; // number already exists
        }else {
            teacher = newTeacher;
            userService.setShareTeacher(newTeacher);
            result0 = userMapper.insertUser(userService.getShareUser());
            result1 = teacherMapper.insertTeacher(teacher);
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

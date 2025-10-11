package com.management.laboratory.controller;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 注册学生
     * 接收学生信息，将信息保存到数据库中，返回注册结果
     * @param studentInfo 学生信息
     * @return 1: 注册成功 2: 账号已存在 0: 注册失败
     */
    @RequestMapping("/register/student")
    public int register(@RequestBody Map<String, String> studentInfo){
        // 从userService中获取用户信息
        User shareUser = userService.getShareUser();

        // 创建学生对象
        Student newStudent = new Student(shareUser.getAccount(), shareUser.getPassword(),
                shareUser.getPermission(), studentInfo.get("major"),
                studentInfo.get("name"), studentInfo.get("department"), studentInfo.get("number"));

        // 判断添加是否成功的两个返回结果
        int result0 = 0;
        int result1 = 0;

        // 判断number是否已存在
        if(studentMapper.selectStudentByNumber(newStudent.getNumber()) != null) {
            result1 = 2; // 当number已存在时，result1为2
        }else {
            // 从数据库中获取教师信息
            Teacher teacher = teacherMapper.selectTeacherById(Integer.parseInt(studentInfo.get("teacherId")));

            if (teacher == null) return 3; // 当教师不存在时，返回3

            // 设置学生的指导教师
            newStudent.setTeacher(teacher);

            // 添加学生信息
            student = newStudent;
            userService.setShareStudent(newStudent);

            result0 = userMapper.insertUser(userService.getShareUser()); // 添加用户信息到数据库中,得到返回结果
            result1 = studentMapper.insertStudent(newStudent); // 添加学生信息到数据库中,得到返回结果
        }

        if (result0 == 1 && result1 == 1) {
            // 当两个结果都为1时，表示注册成功
            return 0;
        } else if (result1 == 2) {
            // 当result1为2时，表示number已存在
            return 2;
        } else {
            // 当两个结果都不为1时，表示注册失败
            return 1;
        }
    }

    /**
     * 获取学生信息
     * @param studentInfo 学生信息
     * @return 学生信息
     */
    @RequestMapping("/studentInfo")
    public Student getStudentInfo(@RequestBody Map<String, String> studentInfo) {
        // 获取学生信息
        Student student = studentMapper.selectStudentByUserId(Integer.parseInt(studentInfo.get("userId")));

        User user = userMapper.selectUserByUserId(Integer.parseInt(studentInfo.get("userId")));

        // 设置用户信息
        student.setUserId(user.getUserId());
        student.setPassword(user.getPassword());
        student.setAccount(user.getAccount());
        student.setPermission(user.getPermission());

        return student; // 返回学生信息
    }

    /**
     * 更新学生信息
     * @param studentInfo 学生信息
     * @return 更新结果
     */
    @PostMapping("/updateStudentInfo")
    public boolean updateStudentInfo(@RequestBody Map<String, String> studentInfo) {
        // 获取学生信息
        Student existingStudent = studentMapper.selectStudentByUserId(Integer.parseInt(studentInfo.get("userId")));
        if (existingStudent == null) {
            return false; // 学生不存在，返回false
        }

        // 更新学生信息
        existingStudent.setName(studentInfo.get("name"));
        existingStudent.setDepartment(studentInfo.get("department"));
        existingStudent.setMajor(studentInfo.get("major"));
        existingStudent.setNumber(studentInfo.get("number"));

        // 这里假设有一个方法可以更新学生信息到数据库中
        int updateResult = studentMapper.updateStudent(existingStudent);
        return updateResult == 1; // 返回更新是否成功
    }




}

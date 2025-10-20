package com.management.laboratory.controller;
import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
    public ApiResponse<Map<String , String>> register(HttpSession session, @RequestBody Map<String, String> studentInfo){
        // 从userService中获取用户信息
        User shareUser = (User) session.getAttribute("registerUser");
        Map<String , String> resultMap = new HashMap<>();
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

            if (teacher == null) {
                resultMap.put("result", "3");
                return ResponseUtils.fail(ResponseCode.TEACHER_NOT_EXIST);
            } // 当教师不存在时，返回3

            // 设置学生的指导教师
            newStudent.setTeacher(teacher);
            // 添加学生信息
            student = newStudent;
            userService.setShareStudent(newStudent);
            session.setAttribute("registerStudent", newStudent);
            // 设置Session过期时间（例如10分钟）
            session.setMaxInactiveInterval(10 * 60);
            result0 = userMapper.insertUser(shareUser); // 添加用户信息到数据库中,得到返回结果
            student.setUserId(shareUser.getUserId());
            result1 = studentMapper.insertStudent(newStudent); // 添加学生信息到数据库中,得到返回结果
        }

        if (result0 == 1 && result1 == 1) {
            // 当两个结果都为1时，表示注册成功
            resultMap.put("result", "0");
            resultMap.put("userId", String.valueOf(((User) session.getAttribute("registerUser")).getUserId()));
            resultMap.put("Id", String.valueOf(((Student) session.getAttribute("registerStudent")).getStudentId()));
            return ResponseUtils.ok("注册成功", resultMap);
        } else if (result1 == 2) {
            resultMap.put("result", "2");
            // 当result1为2时，表示number已存在
            return ResponseUtils.fail(ResponseCode.STUDENT_EXIST);
        } else {
            resultMap.put("result", "1");
            // 当两个结果都不为1时，表示注册失败
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 获取学生信息
     * @param studentInfo 学生信息
     * @return 学生信息
     */
    @RequestMapping("/studentInfo")
    public ApiResponse<Student> getStudentInfo(@RequestBody Map<String, String> studentInfo) {
        // 获取学生信息
        Student student = studentMapper.selectStudentByUserId(Integer.parseInt(studentInfo.get("userId")));

        User user = userMapper.selectUserByUserId(Integer.parseInt(studentInfo.get("userId")));
        if (user == null){
            return ResponseUtils.fail(ResponseCode.USER_NOT_EXIST); //
        }
        if (student == null){
            return ResponseUtils.fail(ResponseCode.STUDENT_NOT_EXIST); //
        }
        // 设置用户信息
        student.setUserId(user.getUserId());
        student.setPassword(user.getPassword());
        student.setAccount(user.getAccount());
        student.setPermission(user.getPermission());

        return ResponseUtils.ok("获取成功",student); // 返回学生信息
    }

    /**
     * 更新学生信息
     * @param studentInfo 学生信息
     * @return 更新结果
     */
    @PostMapping("/updateStudentInfo")
    public ApiResponse<Void> updateStudentInfo(@RequestBody Map<String, String> studentInfo) {
        // 获取学生信息
        Student existingStudent = studentMapper.selectStudentByUserId(Integer.parseInt(studentInfo.get("userId")));
        if (existingStudent == null) {
            return ResponseUtils.fail(ResponseCode.STUDENT_NOT_EXIST); // 学生不存在，返回false
        }

        // 更新学生信息
        existingStudent.setName(studentInfo.get("name"));
        existingStudent.setDepartment(studentInfo.get("department"));
        existingStudent.setMajor(studentInfo.get("major"));
        existingStudent.setNumber(studentInfo.get("number"));
        if(studentMapper.selectStudentByNumber(existingStudent.getNumber()) != null){
            return ResponseUtils.fail(ResponseCode.STUDENT_EXIST); // 学生 number 已存在，返回false
        }
        // 这里假设有一个方法可以更新学生信息到数据库中
        int updateResult = studentMapper.updateStudent(existingStudent);
        if (updateResult == 1) {
            return ResponseUtils.ok("学生信息更新成功"); // 返回更新结果
        } else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

}

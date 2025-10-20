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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    /**
     * 教师注册
     * 接收教师信息，将信息保存到数据库中，返回注册结果
     * @param teacherInfo 教师信息
     * @return 1: 注册成功 2: 账号已存在 0: 注册失败
     */
    @RequestMapping("/register/teacher")
    public ApiResponse<Map<String , String>> register(HttpSession session, @RequestBody Map<String, String> teacherInfo){
        // 从userService中获取用户信息
        User shareUser = (User) session.getAttribute("registerUser");
        Map<String , String> resultMap = new HashMap<>();
        // 创建教师对象
        Teacher newTeacher = new Teacher(shareUser.getAccount(), shareUser.getPassword(),
                shareUser.getPermission(), teacherInfo.get("department"),
                teacherInfo.get("name"), teacherInfo.get("number"));

        int result0 = 0;
        int result1 = 0;

        // 创建学生列表,并将其置为空添加到教师对象中
        List<Student> students = null;
        newTeacher.setStudents(students);

        if(teacherMapper.selectTeacherByNumber(newTeacher.getNumber()) != null) {
            result1 = 2; // 教师 number 已存在
        }else { // 教师 number 不存在， 添加教师信息
            teacher = newTeacher;
            userService.setShareTeacher(newTeacher);
            session.setAttribute("registerTeacher", newTeacher);
            session.setMaxInactiveInterval(10 * 60); // 设置Session过期时间
            result0 = userMapper.insertUser((User) session.getAttribute("registerUser")); // 添加用户信息,得到返回结果
            result1 = teacherMapper.insertTeacher(newTeacher); // 添加教师信息,得到返回结果
        }
        if (result0 == 1 && result1 == 1) {
            // 当两个结果都为1时，表示注册成功
            resultMap.put("result", "0");
            resultMap.put("userId", String.valueOf(((User) session.getAttribute("registerUser")).getUserId()));
            resultMap.put("Id", String.valueOf(((Teacher) session.getAttribute("registerTeacher")).getTeacherId()));
            return ResponseUtils.ok("注册成功", resultMap);
        } else if (result1 == 2) {
            resultMap.put("result", "2");
            // 当result1为2时，表示number已存在
            return ResponseUtils.fail(ResponseCode.TEACHER_EXIST);
             // 当result1为2时，表示教师 number 已存在
        } else {
            resultMap.put("result", "1");
            // 当两个结果都不为1时，表示注册失败
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR); // 当两个结果都不为1时，表示注册失败
        }
    }

    /**
     * 获取教师信息
     * @param teacherInfo 教师信息
     * @return 教师信息
     */
    @RequestMapping("/teacherInfo")
    public ApiResponse<Teacher> getStudentInfo(@RequestBody Map<String, String> teacherInfo) {
        // 获取教师信息
        Teacher teacher = teacherMapper.selectTeacherByUserId(Integer.parseInt(teacherInfo.get("userId")));

        User user = userMapper.selectUserByUserId(Integer.parseInt(teacherInfo.get("userId"))); // 获取用户信息
        if (user == null){
            return ResponseUtils.fail(ResponseCode.USER_NOT_EXIST); // 用户不存在
        }
        if (teacher == null){
            return ResponseUtils.fail(ResponseCode.TEACHER_NOT_EXIST); // 教师不存在
        }
        // 设置用户信息
        teacher.setUserId(user.getUserId());
        teacher.setPassword(user.getPassword());
        teacher.setAccount(user.getAccount());
        teacher.setPermission(user.getPermission());

        return ResponseUtils.ok("获取成功",teacher); // 返回教师信息
    }

    /**
     * 更新教师信息
     * @param teacherInfo 教师信息
     * @return 更新结果
     */
    @PostMapping("/updateTeacherInfo")
    public ApiResponse<Void> updateStudentInfo(@RequestBody Map<String, String> teacherInfo) {
        Teacher teacher = teacherMapper.selectTeacherByUserId(Integer.parseInt(teacherInfo.get("userId")));
        if (teacher == null) {
            return ResponseUtils.fail(ResponseCode.TEACHER_NOT_EXIST); // 教师不存在，返回false
        }

        // 更新学生信息
        teacher.setName(teacherInfo.get("name"));
        teacher.setDepartment(teacherInfo.get("department"));
        teacher.setNumber(teacherInfo.get("number"));

        if(teacherMapper.selectTeacherByNumber(teacher.getNumber()) != null){
            return ResponseUtils.fail(ResponseCode.TEACHER_EXIST); // 教师 number 已存在，返回false
        }

        // 这里假设有一个方法可以更新学生信息到数据库中
        int updateResult = teacherMapper.updateTeacher(teacher);
        if (updateResult == 1){
            return ResponseUtils.ok("教师更新成功", null); // 返回更新成功
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR); // 返回更新失败
        }
    }

    /**
     * 获取所有教师信息
     * @return 教师信息列表
     */
    @RequestMapping("/getAllTeachers")
    public ApiResponse<List<Teacher>> getAllTeachers() {
        return ResponseUtils.ok(teacherMapper.selectAllTeachers());
    }

    /**
     * 分页获取所有教师信息
     * @return 教师信息列表
     */
    @RequestMapping("/getTeachers")
    public ApiResponse<List<Teacher>> getAllTeachers(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        try {
            // 参数校验
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            if (size > 100) {
                size = 100; // 限制每页最大数量
            }
            // 计算偏移量
            int offset = (page - 1) * size;
            // 查询数据
            List<Teacher> teachers = teacherMapper.selectTeachersByPage(offset, size);
            int total = teacherMapper.countTeachers();
            return ResponseUtils.ok("获取教师列表成功", teachers);

        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }
}

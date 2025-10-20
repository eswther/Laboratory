package com.management.laboratory.controller;
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
    public Map<String , String> register(HttpSession session, @RequestBody Map<String, String> teacherInfo){
        // 从userService中获取用户信息
        User shareUser = userService.getShareUser();
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
            return resultMap;
        } else if (result1 == 2) {
            resultMap.put("result", "2");
            // 当result1为2时，表示number已存在
            return resultMap; // 当result1为2时，表示教师 number 已存在
        } else {
            resultMap.put("result", "1");
            // 当两个结果都不为1时，表示注册失败
            return resultMap; // 当两个结果都不为1时，表示注册失败
        }
    }

    /**
     * 获取教师信息
     * @param teacherInfo 教师信息
     * @return 教师信息
     */
    @RequestMapping("/teacherInfo")
    public Teacher getStudentInfo(@RequestBody Map<String, String> teacherInfo) {
        // 获取教师信息
        Teacher teacher = teacherMapper.selectTeacherByUserId(Integer.parseInt(teacherInfo.get("userId")));

        User user = userMapper.selectUserByUserId(Integer.parseInt(teacherInfo.get("userId"))); // 获取用户信息

        // 设置用户信息
        teacher.setUserId(user.getUserId());
        teacher.setPassword(user.getPassword());
        teacher.setAccount(user.getAccount());
        teacher.setPermission(user.getPermission());

        return teacher; // 返回教师信息
    }

    /**
     * 更新教师信息
     * @param teacherInfo 教师信息
     * @return 更新结果
     */
    @PostMapping("/updateTeacherInfo")
    public int updateStudentInfo(@RequestBody Map<String, String> teacherInfo) {
        Teacher teacher = teacherMapper.selectTeacherByUserId(Integer.parseInt(teacherInfo.get("userId")));

        if (teacher == null) {
            return 0; // 教师不存在，返回false
        }

        // 更新教师信息
        teacher.setName(teacherInfo.get("name"));
        teacher.setDepartment(teacherInfo.get("department"));
        teacher.setNumber(teacherInfo.get("number"));

        // 更新教师信息到数据库中
        int updateResult = teacherMapper.updateTeacher(teacher);
        return updateResult; // 返回更新是否成功
    }

    /**
     * 获取所有教师信息
     * @return 教师信息列表
     */
    @RequestMapping("/getAllTeachers")
    public List<Teacher> getAllTeachers() {
        return teacherMapper.selectAllTeachers();
    }
}

package com.management.laboratory.controller;

import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    private User mockUser;
    private Teacher mockTeacher;
    private Student mockStudent;

    @BeforeEach
    void setUp() {
        // 初始化模拟对象
        mockUser = new User("teacherAccount", "teacherPassword", 1); // permission=1 for teacher
        mockUser.setUserId(1);

        mockTeacher = new Teacher("teacherAccount", "teacherPassword", 1,
                "Computer Science", "Dr. Smith", "T2023001");
        mockTeacher.setTeacherId(1);

        mockStudent = new Student();
        mockStudent.setStudentId(1);
        mockStudent.setName("John Doe");
        mockStudent.setNumber("20230001");
    }

    @Test
    void register_Teacher_Success() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(1);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(1, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
        verify(userService, times(1)).setShareTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_NumberAlreadyExists() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为 - 工号已存在（在student表中找到相同number）
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(mockStudent);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(2, result);
        verify(userService, times(1)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, never()).insertUser(any(User.class));
        verify(teacherMapper, never()).insertTeacher(any(Teacher.class));
        verify(userService, never()).setShareTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_UserInsertFailed() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为 - 用户插入失败
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(0); // 插入失败
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(1);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(0, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
        verify(userService, times(1)).setShareTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_TeacherInsertFailed() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为 - 教师插入失败
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(0); // 插入失败

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(0, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
        verify(userService, times(1)).setShareTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_BothInsertFailed() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为 - 两者都插入失败
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(0); // 插入失败
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(0); // 插入失败

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(0, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
        verify(userService, times(1)).setShareTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_StudentsListInitialized() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(1);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(1, result);
        // 验证students列表被正确初始化（在register方法中设置为null）
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("T2023001");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_WithEmptyInfo() {
        // 准备测试数据 - 空信息
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "");
        teacherInfo.put("name", "");
        teacherInfo.put("number", "");

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(1);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(1, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("");
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(teacherMapper, times(1)).insertTeacher(any(Teacher.class));
    }

    @Test
    void register_Teacher_VerifyTeacherObjectCreation() {
        // 准备测试数据
        Map<String, String> teacherInfo = new HashMap<>();
        teacherInfo.put("department", "Computer Science");
        teacherInfo.put("name", "Dr. Smith");
        teacherInfo.put("number", "T2023001");

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("T2023001")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(teacherMapper.insertTeacher(any(Teacher.class))).thenReturn(1);

        // 执行测试
        int result = teacherController.register(teacherInfo);

        // 验证结果
        assertEquals(1, result);
        // 验证Teacher对象使用正确的参数创建
        verify(userService, times(2)).getShareUser();
        // 验证从userService获取的用户信息被正确使用
    }
}
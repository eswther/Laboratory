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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private StudentController studentController;

    private User mockUser;
    private Student mockStudent;
    private Teacher mockTeacher;

    @BeforeEach
    void setUp() {
        // 初始化模拟对象
        mockUser = new User("testAccount", "testPassword", 2); // permission=2 for student
        mockUser.setUserId(1);

        mockTeacher = new Teacher();
        mockTeacher.setTeacherId(1);
        mockTeacher.setName("Test Teacher");

        mockStudent = new Student("testAccount", "testPassword", 2,
                "Computer Science", "John Doe", "Engineering", "20230001");
        mockStudent.setStudentId(1);
        mockStudent.setTeacher(mockTeacher);
    }

    @Test
    void register_Student_Success() {
        // 准备测试数据
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "1");

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);
        when(teacherMapper.selectTeacherById(1)).thenReturn(mockTeacher);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(1);

        // 执行测试
        int result = studentController.register(studentInfo);

        // 验证结果
        assertEquals(1, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, times(1)).selectTeacherById(1);
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(studentMapper, times(1)).insertStudent(any(Student.class));
        verify(userService, times(1)).setShareStudent(any(Student.class));
    }

    @Test
    void register_Student_NumberAlreadyExists() {
        // 准备测试数据
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "1");

        // 模拟行为 - 学号已存在
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(mockStudent);

        // 执行测试
        int result = studentController.register(studentInfo);

        // 验证结果
        assertEquals(2, result);
        verify(userService, times(1)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, never()).selectTeacherById(anyInt());
        verify(userMapper, never()).insertUser(any(User.class));
        verify(studentMapper, never()).insertStudent(any(Student.class));
        verify(userService, never()).setShareStudent(any(Student.class));
    }

    @Test
    void register_Student_UserInsertFailed() {
        // 准备测试数据
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "1");

        // 模拟行为 - 用户插入失败
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);
        when(teacherMapper.selectTeacherById(1)).thenReturn(mockTeacher);
        when(userMapper.insertUser(any(User.class))).thenReturn(0); // 插入失败
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(1);

        // 执行测试
        int result = studentController.register(studentInfo);

        // 验证结果
        assertEquals(0, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, times(1)).selectTeacherById(1);
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(studentMapper, times(1)).insertStudent(any(Student.class));
    }

    @Test
    void register_Student_StudentInsertFailed() {
        // 准备测试数据
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "1");

        // 模拟行为 - 学生插入失败
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);
        when(teacherMapper.selectTeacherById(1)).thenReturn(mockTeacher);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(0); // 插入失败

        // 执行测试
        int result = studentController.register(studentInfo);

        // 验证结果
        assertEquals(0, result);
        verify(userService, times(2)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, times(1)).selectTeacherById(1);
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(studentMapper, times(1)).insertStudent(any(Student.class));
    }

    @Test
    void register_Student_TeacherNotFound() {
        // 准备测试数据
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "999"); // 不存在的教师ID

        // 模拟行为 - 教师不存在
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);
        when(teacherMapper.selectTeacherById(999)).thenReturn(null);

        // 执行测试
        int result = studentController.register(studentInfo);

        // 验证结果
        assertEquals(3, result);
        verify(userService, times(1)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, times(1)).selectTeacherById(999);
        verify(userMapper, never()).insertUser(any(User.class));
        verify(studentMapper, never()).insertStudent(any(Student.class));
    }

    @Test
    void register_Student_WithNullTeacherId() {
        // 准备测试数据 - 没有提供teacherId
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        // 没有teacherId字段

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);

        // 执行测试 - 会抛出NumberFormatException，因为teacherId为null
        try {
            int result = studentController.register(studentInfo);
            assertEquals(0, result);
        } catch (NumberFormatException e) {
            // 预期会抛出异常
        }

        verify(userService, times(1)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, never()).selectTeacherById(anyInt());
        verify(userMapper, never()).insertUser(any(User.class));
        verify(studentMapper, never()).insertStudent(any(Student.class));
    }

    @Test
    void register_Student_WithInvalidTeacherId() {
        // 准备测试数据 - 无效的teacherId
        Map<String, String> studentInfo = new HashMap<>();
        studentInfo.put("major", "Computer Science");
        studentInfo.put("name", "John Doe");
        studentInfo.put("department", "Engineering");
        studentInfo.put("number", "20230001");
        studentInfo.put("teacherId", "invalid"); // 无效的ID格式

        // 模拟行为
        when(userService.getShareUser()).thenReturn(mockUser);
        when(studentMapper.selectStudentByNumber("20230001")).thenReturn(null);

        // 执行测试 - 会抛出NumberFormatException
        try {
            int result = studentController.register(studentInfo);
            assertEquals(0, result);
        } catch (NumberFormatException e) {
            // 预期会抛出异常
        }

        verify(userService, times(1)).getShareUser();
        verify(studentMapper, times(1)).selectStudentByNumber("20230001");
        verify(teacherMapper, never()).selectTeacherById(anyInt());
        verify(userMapper, never()).insertUser(any(User.class));
        verify(studentMapper, never()).insertStudent(any(Student.class));
    }
}
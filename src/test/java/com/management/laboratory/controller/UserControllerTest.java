package com.management.laboratory.controller;

import com.management.laboratory.entity.Admin;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.AdminMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private Teacher testTeacher;
    private Student testStudent;
    private Admin testAdmin;

    @BeforeEach
    void setUp() {
        testUser = new User("testAccount", "testPassword", 1);
        testUser.setUserId(1);

        testTeacher = new Teacher();
        testTeacher.setTeacherId(100);
        testTeacher.setUserId(1);

        testStudent = new Student();
        testStudent.setStudentId(200);
        testStudent.setUserId(1);

        testAdmin = new Admin();
        testAdmin.setAdminId(300);
        testAdmin.setUserId(1);
    }

    @Test
    void register_Success() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "newUser");
        userInfo.put("password", "password123");
        userInfo.put("permission", "1");

        // 模拟行为
        when(userMapper.selectUserByAccount("newUser")).thenReturn(null);
        doNothing().when(userService).setShareUser(any(User.class));

        // 执行测试
        int result = userController.register(userInfo);

        // 验证结果
        assertEquals(1, result);
        verify(userMapper, times(1)).selectUserByAccount("newUser");
        verify(userService, times(1)).setShareUser(any(User.class));
    }

    @Test
    void register_AccountExists() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "existingUser");
        userInfo.put("password", "password123");
        userInfo.put("permission", "1");

        // 模拟行为
        when(userMapper.selectUserByAccount("existingUser")).thenReturn(testUser);

        // 执行测试
        int result = userController.register(userInfo);

        // 验证结果
        assertEquals(2, result);
        verify(userMapper, times(1)).selectUserByAccount("existingUser");
        verify(userService, never()).setShareUser(any(User.class));
    }

    @Test
    void login_Success_Admin() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "testPassword");

        User adminUser = new User("testAccount", "testPassword", 0);
        adminUser.setUserId(1);

        // 模拟行为
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(adminUser);
        when(adminMapper.selectAdminByUserId(1)).thenReturn(testAdmin);
        doNothing().when(userService).setShareUser(adminUser);
        doNothing().when(userService).setShareAdmin(testAdmin);
        when(userService.getShareAdmin()).thenReturn(testAdmin);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.get("result"));
        assertEquals(1, result.get("userId"));
        assertEquals(300, result.get("Id"));

        verify(userService, times(1)).setShareUser(adminUser);
        verify(userService, times(1)).setShareAdmin(testAdmin);
        verify(userService, times(1)).getShareAdmin();
    }

    @Test
    void login_Success_Teacher() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "testPassword");

        User teacherUser = new User("testAccount", "testPassword", 1);
        teacherUser.setUserId(1);

        // 模拟行为
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(teacherUser);
        when(teacherMapper.selectTeacherByUserId(1)).thenReturn(testTeacher);
        doNothing().when(userService).setShareUser(teacherUser);
        doNothing().when(userService).setShareTeacher(testTeacher);
        when(userService.getShareTeacher()).thenReturn(testTeacher);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.get("result"));
        assertEquals(1, result.get("userId"));
        assertEquals(100, result.get("Id"));

        verify(userService, times(1)).setShareUser(teacherUser);
        verify(userService, times(1)).setShareTeacher(testTeacher);
        verify(userService, times(1)).getShareTeacher();
    }

    @Test
    void login_Success_Student() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "testPassword");

        User studentUser = new User("testAccount", "testPassword", 2);
        studentUser.setUserId(1);

        // 模拟行为
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(studentUser);
        when(studentMapper.selectStudentByUserId(1)).thenReturn(testStudent);
        doNothing().when(userService).setShareUser(studentUser);
        doNothing().when(userService).setShareStudent(testStudent);
        when(userService.getShareStudent()).thenReturn(testStudent);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.get("result"));
        assertEquals(1, result.get("userId"));
        assertEquals(200, result.get("Id"));

        verify(userService, times(1)).setShareUser(studentUser);
        verify(userService, times(1)).setShareStudent(testStudent);
        verify(userService, times(1)).getShareStudent();
    }

    @Test
    void login_UserNotFound() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "nonExistentUser");
        userInfo.put("password", "password123");

        // 模拟行为
        when(userMapper.selectUserByAccount("nonExistentUser")).thenReturn(null);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.get("result"));
        assertNull(result.get("userId"));

        verify(userService, never()).setShareUser(any());
    }

    @Test
    void login_WrongPassword() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "wrongPassword");

        // 模拟行为
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(testUser);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.get("result"));
        assertNull(result.get("userId"));

        verify(userService, never()).setShareUser(any());
    }

    @Test
    void login_InvalidPermission() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "testPassword");

        User invalidUser = new User("testAccount", "testPassword", 3); // 无效权限
        invalidUser.setUserId(1);

        // 模拟行为
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(invalidUser);
        doNothing().when(userService).setShareUser(invalidUser);

        // 执行测试
        Map<String, Object> result = userController.login(userInfo);

        // 验证结果
        assertNull(result);

        verify(userService, times(1)).setShareUser(invalidUser);
        verify(userService, never()).setShareAdmin(any());
        verify(userService, never()).setShareTeacher(any());
        verify(userService, never()).setShareStudent(any());
    }

    @Test
    void login_TeacherNotFound() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "testAccount");
        userInfo.put("password", "testPassword");

        User teacherUser = new User("testAccount", "testPassword", 1);
        teacherUser.setUserId(1);

        // 模拟行为 - teacherMapper 返回 null
        when(userMapper.selectUserByAccount("testAccount")).thenReturn(teacherUser);
        when(teacherMapper.selectTeacherByUserId(1)).thenReturn(null);
        doNothing().when(userService).setShareUser(teacherUser);
        doNothing().when(userService).setShareTeacher(null);
        when(userService.getShareTeacher()).thenReturn(null);

        // 执行测试 - 这里会抛出 NullPointerException，需要处理
        assertThrows(NullPointerException.class, () -> {
            userController.login(userInfo);
        });

        verify(userService, times(1)).setShareUser(teacherUser);
        verify(userService, times(1)).setShareTeacher(null);
    }
}
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
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeEach
    void setUp() {
        testUser = new User("testAccount", "testPassword", 1);
        testUser.setUserId(1); // 假设设置了用户ID
    }

    @Test
    void register_Success() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "newAccount");
        userInfo.put("password", "newPassword");
        userInfo.put("permission", "1");

        // 模拟行为
        when(userMapper.selectUserByAccount("newAccount")).thenReturn(null);
        doNothing().when(userService).setShareUser(any(User.class));

        // 执行测试
        int result = userController.register(userInfo);

        // 验证结果
        assertEquals(1, result);
        verify(userMapper, times(1)).selectUserByAccount("newAccount");
        verify(userService, times(1)).setShareUser(any(User.class));
    }

    @Test
    void register_AccountExists() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "existingAccount");
        userInfo.put("password", "password");
        userInfo.put("permission", "1");

        // 模拟行为
        when(userMapper.selectUserByAccount("existingAccount")).thenReturn(testUser);

        // 执行测试
        int result = userController.register(userInfo);

        // 验证结果
        assertEquals(2, result);
        verify(userMapper, times(1)).selectUserByAccount("existingAccount");
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
        doNothing().when(userService).setShareUser(adminUser);
        doNothing().when(userService).setShareAdmin(any());
        when(adminMapper.selectAdminByUserId(1)).thenReturn(new Admin()); // 假设返回Admin对象

        // 执行测试
        boolean result = userController.login(userInfo);

        // 验证结果
        assertTrue(result);
        verify(userMapper, times(1)).selectUserByAccount("testAccount");
        verify(userService, times(1)).setShareUser(adminUser);
        verify(userService, times(1)).setShareAdmin(any());
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
        doNothing().when(userService).setShareUser(teacherUser);
        doNothing().when(userService).setShareTeacher(any());
        when(teacherMapper.selectTeacherByUserId(1)).thenReturn(new Teacher()); // 假设返回Teacher对象

        // 执行测试
        boolean result = userController.login(userInfo);

        // 验证结果
        assertTrue(result);
        verify(userMapper, times(1)).selectUserByAccount("testAccount");
        verify(userService, times(1)).setShareUser(teacherUser);
        verify(userService, times(1)).setShareTeacher(any());
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
        doNothing().when(userService).setShareUser(studentUser);
        doNothing().when(userService).setShareStudent(any());
        when(studentMapper.selectStudentByUserId(1)).thenReturn(new Student()); // 假设返回Student对象

        // 执行测试
        boolean result = userController.login(userInfo);

        // 验证结果
        assertTrue(result);
        verify(userMapper, times(1)).selectUserByAccount("testAccount");
        verify(userService, times(1)).setShareUser(studentUser);
        verify(userService, times(1)).setShareStudent(any());
    }

    @Test
    void login_AccountNotExists() {
        // 准备测试数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("account", "nonExistingAccount");
        userInfo.put("password", "password");

        // 模拟行为
        when(userMapper.selectUserByAccount("nonExistingAccount")).thenReturn(null);

        // 执行测试
        boolean result = userController.login(userInfo);

        // 验证结果
        assertFalse(result);
        verify(userMapper, times(1)).selectUserByAccount("nonExistingAccount");
        verify(userService, never()).setShareUser(any(User.class));
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
        boolean result = userController.login(userInfo);

        // 验证结果
        assertFalse(result);
        verify(userMapper, times(1)).selectUserByAccount("testAccount");
        verify(userService, never()).setShareUser(any(User.class));
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
        boolean result = userController.login(userInfo);

        // 验证结果
        assertFalse(result);
        verify(userMapper, times(1)).selectUserByAccount("testAccount");
        verify(userService, times(1)).setShareUser(invalidUser);
        verify(userService, never()).setShareAdmin(any());
        verify(userService, never()).setShareTeacher(any());
        verify(userService, never()).setShareStudent(any());
    }
}

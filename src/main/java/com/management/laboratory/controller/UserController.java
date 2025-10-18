package com.management.laboratory.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.AdminMapper;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.TeacherMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    UserService userService;

    User user;

    /**
     * 注册方法
     * 接受用户的注册请求，并将用户信息保存到数据库中，返回注册结果
     * @param userInfo 用户信息
     * @return 1: 注册成功 2: 账号已存在 0: 注册失败
     */
    @PostMapping("/register")
    public int register(@RequestBody Map<String, String> userInfo) {
        // 创建一个用户对象
        User newUser = new User(userInfo.get("account"), userInfo.get("password"), Integer.parseInt(userInfo.get("permission")));

        // 创建结果返回值，默认为0。
        int result = 0;

        // 判断账号是否已存在
        if(userMapper.selectUserByAccount(newUser.getAccount()) != null) {
            result = 2; // 将返回值设置为2，表示账号已存在
        }else {
            userService.setShareUser(newUser);
            user = newUser;
            result = 1; // 将返回值设置为1，表示注册成功
        }

        return result; // 返回注册结果。
    }

    /**
     * 登录方法
     * 接受用户的登录请求，并验证用户信息，返回登录结果
     * @param userInfo 用户信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> userInfo) {
        Map<String, Object> map = new HashMap<>();
        // 跟据用户输入的账号和密码，从数据库中查询用户信息
        User loginUser = userMapper.selectUserByAccount(userInfo.get("account"));


        if (loginUser == null){
            map.put("result", 1);
            map.put("userId", null);
            map.put("Id", null);
            return map; // 如果查询结果为空，则返回false，表示用户不存在。
        }else if (!loginUser.getPassword().equals(userInfo.get("password"))){
            map.put("result", 2);
            map.put("userId", null);
            map.put("Id", null);
            return map; // 如果密码不匹配，则返回false，表示登录失败。
        }else { // 如果用户名和密码都匹配，则继续进行登录操作。
            userService.setShareUser(loginUser);

            switch (loginUser.getPermission()){
                case 0: // 如果用户权限为0，则将用户信息保存到Admin对象中。
                    userService.setShareAdmin(adminMapper.selectAdminByUserId(loginUser.getUserId()));
                    map.put("result", 0);
                    map.put("userId", loginUser.getUserId());
                    map.put("Id", userService.getShareAdmin().getAdminId());
                    break;
                case 1: // 如果用户权限为1，则将用户信息保存到Teacher对象中。
                    userService.setShareTeacher(teacherMapper.selectTeacherByUserId(loginUser.getUserId()));
                    map.put("result", 0);
                    map.put("userId", loginUser.getUserId());
                    map.put("Id", userService.getShareTeacher().getTeacherId());
                    break;
                case 2: // 如果用户权限为2，则将用户信息保存到Student对象中。
                    userService.setShareStudent(studentMapper.selectStudentByUserId(loginUser.getUserId()));
                    map.put("result", 0);
                    map.put("userId", loginUser.getUserId());
                    map.put("Id", userService.getShareStudent().getStudentId());
                    break;
                default:
                    return null;
            }
        }
        return map; // 返回登录结果。
    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return 更新结果
     */
    @PostMapping("/updateUserInfo")
    public int updateUserInfo(@RequestBody Map<String, String> userInfo) {
        User existingUser = userMapper.selectUserByUserId(Integer.parseInt(userInfo.get("userId")));

        if (existingUser == null) {
            return 0; // 用户不存在，返回false
        }
        if(userMapper.selectUserByAccount(existingUser.getAccount()) != null) {
            return  2; // 将返回值设置为2，表示账号已存在
        }
        // 更新用户信息
        existingUser.setAccount(userInfo.get("account"));
        existingUser.setPassword(userInfo.get("password"));

        return userMapper.updateUser(existingUser); // 返回更新结果
    }

}

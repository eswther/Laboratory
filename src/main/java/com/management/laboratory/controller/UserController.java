package com.management.laboratory.controller;
import com.management.laboratory.entity.User;
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
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    User user;
    @PostMapping("/register")
    public int register(@RequestBody Map<String, String> userInfo) {
        User newUser = new User(userInfo.get("account"), userInfo.get("password"), Integer.parseInt(userInfo.get("permission")));
        int result = 0;
        if(userMapper.selectUserByAccount(newUser.getAccount()) != null) {
            result = 2; // Account already exists
        }else {
            userService.setShareUser(newUser);
            user = newUser;
            result = 1;
        }
        return result; // Placeholder return value
    }

    @PostMapping("/login")
    public boolean login(String username, String password) {
        // Implement login logic here
        return true; // Placeholder return value
    }


}

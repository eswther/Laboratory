package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 * 每个用户对应一个用户账号，一个密码，一个权限
 */
@Getter
@Setter
public class User {
    private int userId;//user id
    private String account;//user account
    private String password;//user password
    private int permission;//user permission 0 管理员 1教师 2学生

    public User(String account, String password, int permission) {
        this.account = account;
        this.password = password;
        this.permission = permission;
    }

    public User(int userId, String account, String password, int permission) {
        this.userId = userId;
        this.account = account;
        this.password = password;
        this.permission = permission;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}

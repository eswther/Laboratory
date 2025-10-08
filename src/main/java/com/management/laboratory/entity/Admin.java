package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理员实体类
 * 继承自用户实体，具有最高权限
 */
@Getter
@Setter
public class Admin extends User{

    private int adminId;
    private String name;

    public Admin() {
    }

    public Admin(String account, String password, int permission, String name) {
        super(account, password, permission);
        this.name = name;
    }

    public Admin(int userId, String account, String password, int permission, int adminId, String name) {
        super(userId, account, password, permission);
        this.adminId = adminId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

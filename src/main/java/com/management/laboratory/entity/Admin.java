package com.management.laboratory.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends User{ // 管理员实体，继承自用户实体，具有最高权限

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

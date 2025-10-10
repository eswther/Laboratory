package com.management.laboratory.mapper;

import com.management.laboratory.entity.Admin;
import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;

import org.apache.ibatis.annotations.*;

/**
 * 管理员信息映射类
 * 将管理员信息与数据库中admin表进行映射
 */
@Mapper
public interface AdminMapper {

    /**
     * 根据 user_id 查询管理员信息
     * @param id 用户 id
     * @return 管理员信息
     */
    @Select("SELECT " +
            "a.*," +
            "u.account, " +
            "u.password, " +
            "u.permission " +
            "FROM admin a " +
            "JOIN user u ON a.user_id = u.user_id " +
            "WHERE u.user_id = #{id}")
    @Results({
            @Result(property = "adminId", column = "admin_id"), // 映射 admin 表的 id
            @Result(property = "userId", column = "user_id"), // 映射外键 user_id
            @Result(property = "name", column = "name"), // 映射 admin 表的 role 字段
            @Result(property = "account", column = "account"), // 映射 user 表的 account 字段
            @Result(property = "password", column = "password"), // 映射 user 表的 password 字段
            @Result(property = "permission", column = "permission")// 映射 user 表的 permission 字段
    })
    Admin selectAdminByUserId(int id);

}

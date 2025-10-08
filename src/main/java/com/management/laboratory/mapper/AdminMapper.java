package com.management.laboratory.mapper;

import com.management.laboratory.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("SELECT " +
            "a.*" +
            "u.account, " +
            "u.password " +
            "u.permission " +
            "FROM admin a " +
            "JOIN user u ON a.user_id = u.user_id " +
            "WHERE u.user_id = #{id}")
    @Results({
            @Result(property = "adminId", column = "admin_id"), // 映射 admin 表的 id
            @Result(property = "userId", column = "user_id"), // 映射外键 user_id
            @Result(property = "name", column = "name"), // 映射 admin 表的 role 字段
            @Result(property = "account", column = "account"), //
            @Result(property = "password", column = "password"), //
            @Result(property = "permission", column = "permission")//
    })
    Admin selectAdminByUserId(int id);
}

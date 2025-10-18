package com.management.laboratory.mapper;

import com.management.laboratory.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 用户映射类
 * 将用户信息与数据库user表进行映射
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户账号查询用户信息
     * @param account 用户账号
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE account = #{account}")
    User selectUserByAccount(String account);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User selectUserByUserId(int userId);

    /**
     * 插入用户信息
     * @param user 用户信息
     * @return 插入成功返回1
     */
    @Insert("INSERT INTO user (account, password, permission) " +
            "VALUES (#{account}, #{password}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertUser(User user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新成功返回1
     */
    @Insert("UPDATE user SET account = #{account}, password = #{password} WHERE user_id = #{userId}")
    int updateUser(User user);


}

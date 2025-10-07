package com.management.laboratory.mapper;

import com.management.laboratory.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE account = #{account}")
    User selectUserByAccount(String account);

    @Insert("INSERT INTO user (account, password, permission) " +
            "VALUES (#{account}, #{password}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    public int insertUser(User user);

}

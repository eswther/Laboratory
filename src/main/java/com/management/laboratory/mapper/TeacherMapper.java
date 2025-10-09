package com.management.laboratory.mapper;

import com.management.laboratory.entity.Teacher;
import org.apache.ibatis.annotations.*;

/**
 * 教师信息映射类
 * 将教师信息保存在数据库中
 */
@Mapper
public interface TeacherMapper {

    /**
     * 根据 id 查询教师信息
     * @param id 教师 id
     * @return 教师信息
     */
    @Select("SELECT * FROM teacher WHERE teacher_id = #{id}")
    public Teacher selectTeacherById(int id);

    /**
     * 根据 user_id 查询教师信息
     * @param userId 教师 user_id
     * @return 教师信息
     */
    @Select("SELECT * FROM teacher WHERE user_id = #{userId}")
    public Teacher selectTeacherByUserId(int userId);

    /**
     * 根据 number 获取教师信息
     * @param number 教师 number
     * @return 教师信息
     */
    @Select("SELECT * FROM teacher WHERE number = #{number}")
    Teacher selectTeacherByNumber(String number);

    /**
     * 插入教师信息
     * @param teacher 教师信息
     * @return 插入结果
     */
    @Insert("INSERT INTO teacher (department, name, number) " +
            "VALUES (#{department}, #{name}, #{number})")
    @Options(useGeneratedKeys = true, keyProperty = "teacherId", keyColumn = "teacher_id")
    public int insertTeacher(Teacher teacher);

    /**
     * 更新教师信息
     * @param teacher 教师信息
     * @return 更新结果
     */
    @Update("UPDATE teacher SET department = #{department}, name = #{name}, number = #{number} WHERE teacher_id = #{teacherId}")
    public int updateTeacher(Teacher teacher);

}

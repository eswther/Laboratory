package com.management.laboratory.mapper;

import com.management.laboratory.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    public Teacher selectTeacherById0(int id);


    @Select("SELECT t.teacher_id, t.user_id, t.name, t.department, t.number "+// 只获取teacher_id用于关联查询
            "FROM teacher t " +
            "WHERE t.teacher_id = #{id}")
    @Results({
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "student", column = "teacher_id",javaType = java.util.List.class,
                    many = @Many(select = "com.management.laboratory.mapper.StudentMapper.selectStudentsByTeacherId"))
    })
    public Teacher selectTeacherById(int id);

    /**
     * 根据 user_id 查询教师信息
     * @param userId 教师 user_id
     * @return 教师信息
     */
    @Select("SELECT * FROM teacher WHERE user_id = #{userId}")
    public Teacher selectTeacherByUserI0(int userId);

    /**
     * 查询所有教师信息
     * @return 教师信息列表
     */
    @Select("SELECT t.teacher_id, t.user_id, t.name, t.department, t.number "+// 只获取teacher_id用于关联查询
            "FROM teacher t ")
    public List<Teacher> selectAllTeachers();

    /**
     * 分页查询所有教师信息
     * @return 教师信息列表
     */
    /**
     * 分页查询教师
     */
    /**
     * 分页查询教师
     */
    @Select({
            "SELECT t.teacher_id, t.user_id, t.name, t.department, t.number "+
            "FROM teacher t "+
            "LEFT JOIN user u ON t.user_id = u.user_id "+
            "WHERE 1=1 "+
            "LIMIT #{size} OFFSET #{offset}"
    })
    @Results({
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "user.account", column = "account"),
            @Result(property = "user.permission", column = "permission")
    })
    List<Teacher> selectTeachersByPage(@Param("offset") int offset,
                                       @Param("size") int size);

    /**
     * 统计教师数量
     */
    @Select("SELECT COUNT(*) FROM teacher t WHERE 1=1")
    int countTeachers();

    /**
     * 根据 user_id 查询教师信息
     * @param userId 教师 user_id
     * @return 教师信息
     */
    @Select("SELECT t.teacher_id, t.user_id, t.name, t.department, t.number "+// 只获取teacher_id用于关联查询
            "FROM teacher t " +
            "WHERE t.user_id = #{userId}")
    @Results({
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "students", column = "teacher_id",javaType = java.util.List.class,
                    many = @Many(select = "com.management.laboratory.mapper.StudentMapper.selectStudentsByTeacherId"))
    })
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
    @Options(useGeneratedKeys = true, keyProperty = "teacherId", keyColumn = "teacher_id")
    public int updateTeacher(Teacher teacher);

}

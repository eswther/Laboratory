package com.management.laboratory.mapper;

import com.management.laboratory.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 学生映射类
 * 将学生信息与数据库student表进行映射
 */
@Mapper
public interface StudentMapper {

    /**
     * 根据学号查询学生信息
     * @param number 学号
     * @return 学生信息
     */
    @Select("SELECT * FROM student WHERE number = #{number}")
    Student selectStudentByNumber(String number);

    /**
     * 根据用户id查询学生信息
     * @param userId 用户id
     * @return 学生信息
     */
    @Select("SELECT * FROM student WHERE user_id = #{userId}")
    Student selectStudentByUserId(int userId);

    /**
     * 插入学生信息
     * @param student 学生信息
     * @return 插入成功返回1
     */
    @Insert("INSERT INTO student (department, name, number, major, teacher_id) " +
            "VALUES (#{department}, #{name}, #{number}, #{major}, #{teacher.getId()})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", keyColumn = "student_id")
    public int insertStudent(Student student);

    /**
     * 根据教师id查询学生信息
     * @param teacherId 教师id
     * @return 学生信息列表
     */
    @Select("SELECT s.* FROM student s " +
            "WHERE s.teacher_id = #{teacherId}")
    List<Student> selectStudentsByTeacherId(int teacherId);

}

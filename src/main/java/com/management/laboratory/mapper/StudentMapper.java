package com.management.laboratory.mapper;

import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE number = #{number}")
    Student selectStudentByNumber(String number);

    @Select("SELECT * FROM student WHERE user_id = #{userId}")
    Student selectStudentByUserId(int userId);

    @Insert("INSERT INTO student (department, name, number, major, teacher_id) " +
            "VALUES (#{department}, #{name}, #{number}, #{major}, #{teacher.getId()})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", keyColumn = "student_id")
    public int insertStudent(Student student);

    @Select("SELECT s.* FROM student s " +
            "WHERE s.teacher_id = #{teacherId}")
    List<Student> selectStudentsByTeacherId(int teacherId);

}

package com.management.laboratory.mapper;

import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student WHERE number = #{number}")
    Student selectStudentByNumber(String number);

    @Insert("INSERT INTO student (department, name, number, major, teacher_id) " +
            "VALUES (#{department}, #{name}, #{number}, #{major}, #{teacher.getId()})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", keyColumn = "student_id")
    public int insertStudent(Student student);

    @Select("SELECT s.* FROM student s " +
            "WHERE s.teacher_id = #{teacherId}")
    List<Student> selectStudentsByTeacherId(int teacherId);

}

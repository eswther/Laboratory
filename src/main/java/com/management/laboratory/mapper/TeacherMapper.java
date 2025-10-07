package com.management.laboratory.mapper;

import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    public Teacher selectTeacherById(int id);

    @Select("SELECT * FROM teacher WHERE number = #{number}")
    Teacher selectTeacherByNumber(String number);

    @Insert("INSERT INTO teacher (department, name, number) " +
            "VALUES (#{department}, #{name}, #{number})")
    @Options(useGeneratedKeys = true, keyProperty = "teacherId", keyColumn = "teacher_id")
    public int insertTeacher(Teacher teacher);

}

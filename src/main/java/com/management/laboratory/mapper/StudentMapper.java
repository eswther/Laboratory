package com.management.laboratory.mapper;

import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.Teacher;
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
    @Select("SELECT s.student_id, s.user_id, s.name, s.department, s.number, s.major," +
            "       s.teacher_id " +  // 只获取teacher_id用于关联查询
            "FROM student s " +
            "WHERE s.user_id = #{userId}")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "major", column = "major"),
            @Result(property = "teacher", column = "teacher_id",
                    one = @One(select = "com.management.laboratory.mapper.TeacherMapper.selectByTeacherId0"))
    })
    Student selectStudentByUserId(int userId);

    /**
     * 根据用户id查询学生信息
     * @param userId 用户id
     * @return 学生信息
     */
    @Select("SELECT s.student_id, s.user_id, s.name, s.department, s.number, s.major " +
            "FROM student s " +
            "WHERE s.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "student_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "major", column = "major")
    })
    Student selectStudentByUserId0(int userId);

    /**
     * 根据学生id查询学生信息
     * @param studentId 学生id
     * @return 学生信息
     */
    @Select("SELECT s.student_id, s.user_id, s.name, s.department, s.number, s.major " +
            "FROM student s " +
            "WHERE s.student_id = #{studentId}")
    @Results({
            @Result(property = "id", column = "student_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "major", column = "major")
    })
    Student selectStudentById0(int studentId);

    /**
     * 根据学生id查询学生信息
     * @param studentId 学生id
     * @return 学生信息
     */
    @Select("SELECT s.student_id, s.user_id, s.name, s.department, s.number, s.major," +
            "       s.teacher_id " +  // 只获取teacher_id用于关联查询
            "FROM student s " +
            "WHERE s.student_id= #{studentId}")
    @Results({
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "department", column = "department"),
            @Result(property = "number", column = "number"),
            @Result(property = "major", column = "major"),
            @Result(property = "teacher", column = "teacher_id",
                    one = @One(select = "com.management.laboratory.mapper.TeacherMapper.selectByTeacherId0"))
    })
    Student selectStudentById(int studentId);

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

    /**
     * 更新学生信息
     * @param student 学生信息
     * @return 更新结果
     */
    @Update("UPDATE student SET " +
            "department = #{department}, " +
            "name = #{name}, " +
            "number = #{number}, " +
            "major = #{major}, " +
            "teacher_id = #{teacher.getTeacherId()} " +
            "WHERE student_id = #{studentId}")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", keyColumn = "student_id")
    public int updateStudent(Student student);

}

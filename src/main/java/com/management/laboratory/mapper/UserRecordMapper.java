package com.management.laboratory.mapper;

import com.management.laboratory.entity.UserRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRecordMapper {

    @Select("SELECT * FROM user_record WHERE record_id = #{recordId}")
    UserRecord selectUserRecordById0(int recordId);

    @Select("SELECT ur.record_id, ur.reservation_id, ur.student_id, ur.notes, ur.start_time, ur.end_time " +
            "FROM user_record ur " +
            "JOIN reservation r ON ur.reservation_id = r.reservation_id " +
            "JOIN student s ON ur.student_id = s.student_id " +
            "WHERE ur.record_id = #{recordId}")
    @Results({
            @Result(property = "recordId", column = "record_id"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "reservation", column = "reservation_id", one = @One(select = "com.your.mapper.ReservationMapper.selectReservationById0")),
            @Result(property = "student", column = "student_id", one = @One(select = "com.your.mapper.StudentMapper.selectStudentById0"))
    })
    UserRecord selectUserRecordById(int recordId);


    @Insert("INSERT INTO user_record (reservation_id, student_id, notes, start_time, end_time) " +
            "VALUES (#{reservation.reservationId}, #{student.studentId}, #{notes}, #{startTime}, #{endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId", keyColumn = "record_id")
    int insertUserRecord(UserRecord userRecord);


    @Update("UPDATE user_record " +
            "SET end_time = #{endTime} " +
            "WHERE record_id = #{recordId}")
    int setEndTime(UserRecord userRecord);

    @Select("SELECT ur.record_id, ur.reservation_id, ur.student_id, ur.notes, ur.start_time, ur.end_time " +
            "FROM user_record ur " +
            "JOIN reservation r ON ur.reservation_id = r.reservation_id " +
            "JOIN student s ON ur.student_id = s.student_id " +
            "WHERE ur.student_id = #{studentId}")
    @Results({
            @Result(property = "recordId", column = "record_id"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "reservation", column = "reservation_id", one = @One(select = "com.your.mapper.ReservationMapper.selectReservationById0")),
            @Result(property = "student", column = "student_id", one = @One(select = "com.your.mapper.StudentMapper.selectStudentById0"))
    })
    List<UserRecord> selectUserRecordsByStudentId(int studentId);


}

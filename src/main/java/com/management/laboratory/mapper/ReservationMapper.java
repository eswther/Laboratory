package com.management.laboratory.mapper;

import com.management.laboratory.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {
    /**
     * 根据教师 id 获取所有预约信息
     * @param teacherId 教师 id
     * @return 预约信息列表
     */
    @Select("SELECT r.* FROM reservation r " +
            "JOIN student s ON r.student_id = s.student_id " +
            "WHERE s.teacher_id = #{teacherId}")
    List<Reservation> selectAllReservationsByTeacher(int teacherId);


    /**
     * 获取所有预约信息
     * @return 预约信息列表
     */
    @Select("SELECT * FROM reservation")
    List<Reservation> selectAllReservations();
}

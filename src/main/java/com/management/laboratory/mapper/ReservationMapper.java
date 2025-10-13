package com.management.laboratory.mapper;

import com.management.laboratory.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
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
     * 根据预约 id 获取预约信息
     * @param reservationId 预约 id
     * @return 预约信息
     */
    @Select("SELECT * FROM reservation WHERE reservation_id = #{reservationId}")
    Reservation selectReservationById(int reservationId);

    /**
     * 获取所有预约信息
     * @return 预约信息列表
     */
    @Select("SELECT * FROM reservation")
    List<Reservation> selectAllReservations();

    /**
     * 根据学生 id 获取该学生的所有预约信息
     * @param studentId 学生 id
     * @return 该学生的预约信息列表
     */
    @Select("SELECT * FROM reservation WHERE student_id = #{studentId}")
    List<Reservation> selectReservationsByStudentId(int studentId);

    /**
     * 添加预约信息
     * @param reservation 预约信息
     * @return 影响的行数
     */
    @Select("INSERT INTO reservation (student_id, lab_id, project_name, start_time, end_time, status) " +
            "VALUES (#{studentId}, #{lab.labId}, #{projectName}, #{startTime}, #{endTime}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "reservationId", keyColumn = "reservation_id")
    int insertReservation(Reservation reservation);

    /**
     * 获取实验室在指定时间段内的预约数量
     * @param labId 实验室 id
     * @param startTime 预约开始时间
     * @param endTime 预约结束时间
     * @return 预约数量
     */
    @Select("SELECT COUNT(*) FROM reservation " +
            "WHERE lab_id = #{labId} " +
            "AND (" +
            "        (start_time < #{endTime} AND end_time > #{startTime}) " +
            "        OR (start_time = #{startTime} AND end_time = #{endTime})" +
            "    ) " +
            "AND status != 2")
    int getReservationCount(int labId, LocalDateTime startTime, LocalDateTime endTime);



}

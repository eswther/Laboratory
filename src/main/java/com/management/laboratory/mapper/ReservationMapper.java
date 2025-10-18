package com.management.laboratory.mapper;

import com.management.laboratory.entity.Reservation;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationMapper {

    /**
     * 根据教师 id 获取所有预约信息
     * @param teacherId 教师 id
     * @return 预约信息列表
     */
    @Select("SELECT r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time " +
            "FROM reservation r " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "LEFT JOIN student s ON r.student_id = s.student_id " +
            "WHERE s.teacher_id = #{teacherId}")
    @Results(id = "reservationMap", value = {
            @Result(property = "reservationId", column = "reservation_id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "lab.labId", column = "lab_id"),
            @Result(property = "lab.name", column = "lab_name"),
            @Result(property = "lab.location", column = "location"),
            @Result(property = "lab.capacity", column = "capacity"),
            @Result(property = "lab.openTime", column = "open_time"),
            @Result(property = "lab.closeTime", column = "close_time")
    })
    List<Reservation> selectAllReservationsByTeacher(int teacherId);

    /**
     * 根据学生 id 和状态获取预约信息
     * @param teacherId 学生 id
     * @param status 预约状态
     * @return 预约信息列表
     */
    @Select("SELECT r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time " +
            "FROM reservation r " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "LEFT JOIN student s ON r.student_id = s.student_id " +
            "WHERE s.teacher_id = #{teacherId} AND r.status = #{status}")
    @ResultMap("reservationMap")
    List<Reservation> selectAllReservationsByTeacherByStatus(@Param("teacherId") int teacherId,@Param("status") int status);



    /**
     * 根据预约 id 获取预约信息
     * @param reservationId 预约 id
     * @return 预约信息
     */
    @Select("SELECT * FROM reservation WHERE reservation_id = #{reservationId}")
    @ResultMap("reservationMap")
    Reservation selectReservationById(int reservationId);

    /**
     * 获取所有预约信息
     * @return 预约信息列表
     */
    @Select("SELECT r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time " +
            "FROM reservation r " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "ORDER BY r.start_time DESC")
    @ResultMap("reservationMap")
    List<Reservation> selectAllReservations();

    /**
     * 根据学生 id 获取该学生的所有预约信息
     * @param studentId 学生 id
     * @return 该学生的预约信息列表
     */
    @Select("SELECT r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time " +
            "FROM reservation r " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "WHERE r.student_id = #{studentId} " +
            "ORDER BY r.start_time DESC")
    @ResultMap("reservationMap")
    List<Reservation> selectReservationsByStudentId(int studentId);

    /**
     * 根据学生 id 和状态获取该学生的预约信息
     * @param studentId 学生 id
     * @param status 预约状态
     * @return 该学生的预约信息列表
     */
    @Select("SELECT r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time " +
            "FROM reservation r " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "WHERE r.student_id = #{studentId} AND r.status = #{status} " +
            "ORDER BY r.start_time DESC")
    @ResultMap("reservationMap")
    List<Reservation> selectReservationsByStudentIdByStatus(@Param("studentId") int studentId, @Param("status") int status);

    /**
     * 添加预约信息
     * @param reservation 预约信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO reservation (student_id, lab_id, project_name, start_time, end_time, status) " +
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

    /**
     * 更新预约状态
     * @param reservationId 预约 id
     * @param status 新的预约状态
     * @return 影响的行数
     */
    @Update("UPDATE reservation " +
            "SET status = #{status} " +
            "WHERE reservation_id = #{reservationId}")
    @Options(useGeneratedKeys = true, keyProperty = "reservationId", keyColumn = "reservation_id")
    int updateReservationStatus(@Param("reservationId") int reservationId, @Param("status") int status);

}

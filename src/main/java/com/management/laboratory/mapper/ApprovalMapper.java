package com.management.laboratory.mapper;

import com.management.laboratory.entity.Approval;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    /**
     * 获取所有审批信息
     * @return 审批信息列表
     */
    @Select("SELECT a.approval_id, a.reservation_id, a.notes, a.status, a.approval_time, a.teacher_id, " +
            "       r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status AS reservation_status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time, " +
            "       t.teacher_id, t.name AS teacher_name, t.number AS teacher_number, t.department AS teacher_department " +
            "FROM approval a " +
            "LEFT JOIN reservation r ON a.reservation_id = r.reservation_id " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "LEFT JOIN teacher t ON a.teacher_id = t.teacher_id " +
            "ORDER BY a.approval_time DESC")
    @Results(id = "approvalMap", value = {
            @Result(property = "approvalId", column = "approval_id"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "status", column = "status"),
            @Result(property = "approvalTime", column = "approval_time"),
            // Reservation 关联映射
            @Result(property = "reservation.reservationId", column = "reservation_id"),
            @Result(property = "reservation.studentId", column = "student_id"),
            @Result(property = "reservation.projectName", column = "project_name"),
            @Result(property = "reservation.startTime", column = "start_time"),
            @Result(property = "reservation.endTime", column = "end_time"),
            @Result(property = "reservation.status", column = "reservation_status"),
            // Laboratory 关联映射（通过 Reservation）
            @Result(property = "reservation.lab.labId", column = "lab_id"),
            @Result(property = "reservation.lab.name", column = "lab_name"),
            @Result(property = "reservation.lab.location", column = "location"),
            @Result(property = "reservation.lab.capacity", column = "capacity"),
            @Result(property = "reservation.lab.openTime", column = "open_time"),
            @Result(property = "reservation.lab.closeTime", column = "close_time"),
            // Teacher 关联映射
            @Result(property = "teacher.teacherId", column = "teacher_id"),
            @Result(property = "teacher.name", column = "teacher_name"),
            @Result(property = "teacher.number", column = "teacher_number"),
            @Result(property = "teacher.department", column = "teacher_department")
    })
    List<Approval> selectAllApprovals();

    /**
     * 根据 studentId 获取审批信息
     * @param studentId 学生 id
     * @return 审批信息列表
     */
    @Select("SELECT a.approval_id, a.reservation_id, a.notes, a.status, a.approval_time, a.teacher_id, " +
            "       r.reservation_id, r.student_id, r.project_name, r.start_time, r.end_time, r.status AS reservation_status, " +
            "       l.lab_id, l.name AS lab_name, l.location, l.capacity, l.open_time, l.close_time, " +
            "       t.teacher_id, t.name AS teacher_name, t.number AS teacher_number, t.department AS teacher_department " +
            "FROM approval a " +
            "LEFT JOIN reservation r ON a.reservation_id = r.reservation_id " +
            "LEFT JOIN laboratory l ON r.lab_id = l.lab_id " +
            "LEFT JOIN teacher t ON a.teacher_id = t.teacher_id " +
            "WHERE r.student_id = #{studentId}  " +
            "ORDER BY a.approval_time DESC")
    @ResultMap("approvalMap")
    List<Approval> selectApprovalsByStudentId(int studentId);

    /**
     * 添加审批信息
     * @param approval 审批信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO approval (reservation_id, notes, approval_time, status, teacher_id) " +
            "VALUES (#{reservation.reservationId}, #{notes}, #{approvalTime}, #{status}, #{teacher.teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "approvalId", keyColumn = "approval_id")
    int insertApproval(Approval approval);

}

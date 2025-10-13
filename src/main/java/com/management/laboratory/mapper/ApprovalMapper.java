package com.management.laboratory.mapper;

import com.management.laboratory.entity.Approval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApprovalMapper {
    /**
     * 获取所有审批信息
     * @return 审批信息列表
     */
    @Select("SELECT * FROM approval")
    List<Approval> selectAllApprovals();

    /**
     * 根据 studentId 获取审批信息
     * @param studentId 学生 id
     * @return 审批信息列表
     */
    @Select("SELECT a.* FROM approval a " +
            "JOIN reservation r  ON r.reservation_Id = a.reservation_Id " +
            "WHERE r.student_Id = #{studentId}")
    List<Approval> selectApprovalsByStudentId(int studentId);

    /**
     * 添加审批信息
     * @param approval 审批信息
     * @return 影响的行数
     */
    @Select("INSERT INTO approval (reservation_id, notes, approval_time, status) " +
            "VALUES (#{reservation.reservationId}, #{notes}, #{approvalTime}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "approvalId", keyColumn = "approval_id")
    int insertApproval(Approval approval);

}

package com.management.laboratory.controller;

import com.management.laboratory.entity.Approval;
import com.management.laboratory.entity.Reservation;
import com.management.laboratory.mapper.ApprovalMapper;
import com.management.laboratory.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ApprovalController {
    @Autowired
    ApprovalMapper approvalMapper;
    @Autowired
    ReservationMapper reservationMapper;
    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取所有审批信息
     * @return 审批信息列表
     */
    @RequestMapping("/getAllApprovals")
    public List<Approval> getAllApprovals() {
        return approvalMapper.selectAllApprovals();
    }


    /**
     * 根据学生ID获取该学生的所有审批信息
     * @param studentInfo 学生信息
     * @return 该学生的审批信息列表
     */
    @RequestMapping("/getApprovalsByStudentId")
    public List<Approval> getApprovalsByStudentId(@RequestBody Map<String, String> studentInfo) {
        return approvalMapper.selectApprovalsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
    }

    /**
     * 添加审批
     * @param approvalInfo 审批信息
     * @return 添加结果
     */
    @RequestMapping("/approvel")
    public int approvel(@RequestBody Map<String, String> approvalInfo) {
        Approval approval = new Approval();
        Reservation reservation = reservationMapper.selectReservationById(Integer.parseInt(approvalInfo.get("reservationId")));
        if (reservation == null){
            return 2; // 预约不存在
        }
        approval.setReservation(reservation);
        approval.setNotes(approvalInfo.get("notes"));
        approval.setStatus(Integer.parseInt(approvalInfo.get("status")));
        if (approval.getStatus() == 1) {
            reservation.setStatus(1); // 审批通过，更新预约状态为已批准
            if (reservationMapper.updateReservationStatus(reservation.getReservationId(), reservation.getStatus()) != 1) {
                return 3; // 预约状态更新失败
            }
        } else if (approval.getStatus() == 2) {
            reservation.setStatus(2); // 审批不通过，更新预约状态为未批准
            if (reservationMapper.updateReservationStatus(reservation.getReservationId(), reservation.getStatus()) != 1) {
                return 3; // 预约状态更新失败
            }
        }
        approval.setApprovalTime(LocalDateTime.parse(approvalInfo.get("approvalTime"), localDateTimeFormatter));
        return approvalMapper.insertApproval(approval);
    }

}


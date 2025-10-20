package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Approval;
import com.management.laboratory.entity.Reservation;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.mapper.ApprovalMapper;
import com.management.laboratory.mapper.ReservationMapper;
import com.management.laboratory.mapper.TeacherMapper;
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
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 获取所有审批信息
     * @return 审批信息列表
     */
    @RequestMapping("/getAllApprovals")
    public ApiResponse<List<Approval>> getAllApprovals() {
        try {
            List<Approval> approvals = approvalMapper.selectAllApprovals();
            return ApiResponse.success("获取所有审批数据", approvals);
        } catch (Exception e) {
            return ApiResponse.failure(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 分页获取所有审批信息
     * @return 审批信息列表
     */
    @RequestMapping("/getApprovalsByPage")
    public ApiResponse<List<Approval>> getApprovalsByPage(Integer page, Integer size){
        try {
            // 参数校验
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            if (size > 100) {
                size = 100; // 限制每页最大数量
            }
            // 计算偏移量
            int offset = (page - 1) * size;
            // 查询数据
            List<Approval> approvals = approvalMapper.selectApprovalsByPage(offset, size);
            return ApiResponse.success("获取审批列表成功", approvals);
        } catch (Exception e) {
            return ApiResponse.failure(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 根据学生ID获取该学生的所有审批信息
     * @param studentInfo 学生信息
     * @return 该学生的审批信息列表
     */
    @RequestMapping("/getApprovalsByStudentId")
    public ApiResponse<List<Approval>> getApprovalsByStudentId(@RequestBody Map<String, String> studentInfo) {
        try {
            List<Approval> approvals = approvalMapper.selectApprovalsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
            return ApiResponse.success("获取学生审批数据", approvals);
        } catch (Exception e) {
            return ApiResponse.failure(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 添加审批
     * @param approvalInfo 审批信息
     * @return 添加结果
     */
    @RequestMapping("/approvel")
    public ApiResponse<Void> approvel(@RequestBody Map<String, String> approvalInfo) {
        Approval approval = new Approval();
        Reservation reservation = reservationMapper.selectReservationById(Integer.parseInt(approvalInfo.get("reservationId")));
        if (reservation == null){
            return ResponseUtils.fail(ResponseCode.RESERVATION_NOT_EXIST); // 预约不存在
        }
        Teacher teacher = teacherMapper.selectTeacherById(Integer.parseInt(approvalInfo.get("teacherId")));
        if (teacher == null){
            return ResponseUtils.fail(ResponseCode.TEACHER_NOT_EXIST); // 教师不存在
        }
        approval.setTeacher(teacher);
        approval.setReservation(reservation);
        approval.setNotes(approvalInfo.get("notes"));
        approval.setStatus(Integer.parseInt(approvalInfo.get("status")));
        if (approval.getStatus() == 1) {
            reservation.setStatus(1); // 审批通过，更新预约状态为已批准
            if (reservationMapper.updateReservationStatus(reservation.getReservationId(), reservation.getStatus()) != 1) {
                return ResponseUtils.fail(ResponseCode.RESERVATION_UPDATE_FAILURE); // 预约状态更新失败
            }
        } else if (approval.getStatus() == 2) {
            reservation.setStatus(2); // 审批不通过，更新预约状态为未批准
            if (reservationMapper.updateReservationStatus(reservation.getReservationId(), reservation.getStatus()) != 1) {
                return ResponseUtils.fail(ResponseCode.RESERVATION_UPDATE_FAILURE);// 预约状态更新失败
            }
        }
        approval.setApprovalTime(LocalDateTime.parse(approvalInfo.get("approvalTime"), localDateTimeFormatter));
        if(approvalMapper.insertApproval(approval)==1) {
            return ResponseUtils.ok("审批添加成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

}


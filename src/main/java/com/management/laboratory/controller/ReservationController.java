package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Laboratory;
import com.management.laboratory.entity.Reservation;
import com.management.laboratory.mapper.LaboratoryMapper;
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
public class ReservationController {
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    LaboratoryMapper laboratoryMapper;

    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取所有该教师的学生预约信息
     * @return 预约信息列表
     */
    @RequestMapping("/getAllReservationsOfTeacher")
    public ApiResponse<List<Reservation>> getAllReservationsOfTeacher(@RequestBody Map<String, String> teacherInfo) {
        return ResponseUtils.ok("获取预约列表",reservationMapper.selectAllReservationsByTeacher(Integer.parseInt(teacherInfo.get("teacherId"))));
    }

    public ApiResponse<List<Reservation>> getAllReservationsOfTeacherByPage(@RequestBody Map<String, String> teacherInfo,
                                                                     Integer page, Integer size) {
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
            List<Reservation> reservations = reservationMapper.selectReservationsByTeacherByPage(
                    Integer.parseInt(teacherInfo.get("teacherId")), offset, size);
            return ResponseUtils.ok("获取预约列表成功", reservations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 获取所有该教师的学生预约信息待审核
     * @return 预约信息列表
     */
    @RequestMapping("/getAllReservationsOfTeacherByStatus")
    public ApiResponse<List<Reservation>> getAllReservationsOfTeacherByStatus(@RequestBody Map<String, String> teacherInfo) {
        try {
            List<Reservation> reservations = reservationMapper.selectAllReservationsByTeacherByStatus(Integer.parseInt(teacherInfo.get("teacherId")),
                    Integer.parseInt(teacherInfo.get("status")));
            return ResponseUtils.ok("获取预约列表成功", reservations);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 获取所有预约信息
     * @return 预约信息列表
     */
    @RequestMapping("/getAllReservations")
    public ApiResponse<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationMapper.selectAllReservations();
            return ResponseUtils.ok("获取预约列表成功", reservations);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }


    /**
     * 根据学生ID获取该学生的所有预约信息
     * @param studentInfo 学生信息
     * @return 该学生的预约信息列表
     */
    @RequestMapping("/getReservationsByStudentId")
    public ApiResponse<List<Reservation>> getReservationsByStudentId(@RequestBody Map<String, String> studentInfo) {
        try {
            List<Reservation> reservations = reservationMapper.selectReservationsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
            return ResponseUtils.ok("获取预约列表成功", reservations);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 根据学生ID获取该学生的所有预约信息
     * @param studentInfo 学生信息
     * @return 该学生的预约信息列表
     */
    @RequestMapping("/getReservationsByStudentIdByStatus")
    public ApiResponse<List<Reservation>> getReservationsByStudentIdByStatus(@RequestBody Map<String, String> studentInfo) {
        try {
            List<Reservation> reservations = reservationMapper.selectReservationsByStudentIdByStatus(Integer.parseInt(studentInfo.get("studentId"))
                    ,Integer.parseInt(studentInfo.get("status")));
            return ResponseUtils.ok("获取预约列表成功", reservations);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 添加预约
     * @param reservationInfo 预约信息
     * @return 添加结果
     */
    @RequestMapping("/reserve")
    public ApiResponse<Void> reserve(@RequestBody Map<String, String> reservationInfo) {
        try {
            // 参数解析
            Integer studentId = Integer.parseInt(reservationInfo.get("studentId"));
            Integer labId = Integer.parseInt(reservationInfo.get("labId"));
            String projectName = reservationInfo.get("projectName");
            LocalDateTime startTime = LocalDateTime.parse(reservationInfo.get("startTime"), localDateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(reservationInfo.get("endTime"), localDateTimeFormatter);

            // 基本参数验证
            if (studentId == null || labId == null || projectName == null ||
                    startTime == null || endTime == null) {
                return ResponseUtils.fail(ResponseCode.BAD_REQUEST); // 参数错误
            }

            // 验证时间合理性
            if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
                return ResponseUtils.fail(ResponseCode.BAD_REQUEST); // 时间参数错误
            }

            //验证是否在开放时间内
            Laboratory laboratory = laboratoryMapper.selectLaboratoryById0(labId);
            if (startTime.toLocalTime().isBefore(laboratory.getOpenTime()) ||
                    endTime.toLocalTime().isAfter(laboratory.getCloseTime())) {
                return ResponseUtils.fail(ResponseCode.LAB_UNAVAILABLE); // 不在开放时间内
            }

            // 检查实验室容量
            if (!checkLabCapacity(labId, startTime, endTime)) {
                return ResponseUtils.fail(ResponseCode.LAB_OUT_TIME); // 容量不足
            }

            // 创建预约记录
            Reservation reservation = new Reservation();
            reservation.setStudentId(studentId);

            reservation.setLab(laboratory);
            reservation.setProjectName(projectName);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setStatus(0); // 待审核状态
            if(reservationMapper.insertReservation(reservation)==1){
                return ResponseUtils.ok("预约成功", null);
            }else {
                return ResponseUtils.fail(ResponseCode.DATABASE_ERROR); // 系统异常
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.INTERNAL_SERVER_ERROR); // 系统异常
        }
    }

    /**
     * 检查实验室在指定时间段内的容量是否足够
     * @param labId 实验室ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return true-容量足够，false-容量不足
     */
    private boolean checkLabCapacity(Integer labId, LocalDateTime startTime,
                                     LocalDateTime endTime) {
        try {
            // 获取实验室总容量
            int totalCapacity = laboratoryMapper.getCapacity(labId);

            // 获取该时间段内的已预约人数
            int reservedCount = reservationMapper.getReservationCount(
                    labId, startTime, endTime);

            // 检查容量（这里假设每个预约占用1个位置，您可以根据实际情况调整）
            // 如果 reservedCount < totalCapacity 表示还有空位
            return reservedCount < totalCapacity;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}


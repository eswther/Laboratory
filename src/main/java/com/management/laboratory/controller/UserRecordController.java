package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Reservation;
import com.management.laboratory.entity.Student;
import com.management.laboratory.entity.UserRecord;
import com.management.laboratory.mapper.ReservationMapper;
import com.management.laboratory.mapper.StudentMapper;
import com.management.laboratory.mapper.UserMapper;
import com.management.laboratory.mapper.UserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserRecordController {
    @Autowired
    UserRecordMapper userRecordMapper;
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    StudentMapper studentMapper;

    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/addUserRecord")
    public ApiResponse<Map<String ,String>> addUserRecord(@RequestBody Map<String, String> userRecordInfo) {
        Map<String ,String> result = new HashMap<>();

        UserRecord userRecord = new UserRecord();
        Reservation  reservation = reservationMapper.selectReservationById(Integer.parseInt(userRecordInfo.get("reservationId")));
        if (reservation == null){
            result.put("result","2");
            return ResponseUtils.fail(ResponseCode.RESERVATION_NOT_EXIST); // 预约不存在
        }
        userRecord.setReservation(reservation);
        Student student = studentMapper.selectStudentById(Integer.parseInt(userRecordInfo.get("studentId")));
        if (student == null){
            result.put("result","3");
            return ResponseUtils.fail(ResponseCode.STUDENT_NOT_EXIST); // 学生不存在
        }
        userRecord.setStudent(student);
        userRecord.setStartTime(LocalDateTime.parse(userRecordInfo.get("startTime"), localDateTimeFormatter));
        userRecord.setEndTime(null);
        if (userRecord.getStartTime().isBefore(reservation.getStartTime()) || userRecord.getStartTime().isAfter(reservation.getEndTime())) {
            result.put("result","4");
            return ResponseUtils.fail(ResponseCode.RESERVATION_CONFLICT); // 开始时间不在预约时间范围内
        }
        userRecord.setNotes(userRecordInfo.get("notes"));
        int flag = userRecordMapper.insertUserRecord(userRecord);
        result.put("result",String.valueOf(flag));
        if (flag == 1){
            result.put("recordId",String.valueOf(userRecord.getRecordId()));
            return ResponseUtils.ok("使用记录添加成功", result);
        } else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    @RequestMapping("/endUsing")
    public ApiResponse<Void> endUsing(@RequestBody Map<String, String> userRecordInfo) {
        UserRecord userRecord = userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
        if (userRecord == null){
            return ResponseUtils.fail(ResponseCode.USER_RECORD_NOT_EXIST); // 使用记录不存在
        }
        userRecord.setEndTime(LocalDateTime.parse(userRecordInfo.get("endTime"), localDateTimeFormatter));
        if (userRecordMapper.insertUserRecord(userRecord)==1){
            return ResponseUtils.ok("结束使用成功", null);
        } else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    @RequestMapping("/getUserRecordById")
    public ApiResponse<UserRecord> getUserRecordById(@RequestBody Map<String, String> userRecordInfo) {
        try {
            UserRecord userRecord = userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
            return ResponseUtils.ok("获取使用记录成功", userRecord);
        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    @RequestMapping("/getUserRecordsByStudentId")
    public ApiResponse<List<UserRecord>> getUserRecordsByStudentId(@RequestBody Map<String, String> studentInfo) {
        try{
            List<UserRecord> userRecords = userRecordMapper.selectUserRecordsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
            return ResponseUtils.ok("获取使用记录列表成功", userRecords);
        }catch (Exception e){
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

}

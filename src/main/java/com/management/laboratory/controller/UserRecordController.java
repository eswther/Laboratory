package com.management.laboratory.controller;

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
    public Map<String ,String> addUserRecord(@RequestBody Map<String, String> userRecordInfo) {
        Map<String ,String> result = new HashMap<>();

        UserRecord userRecord = new UserRecord();
        Reservation  reservation = reservationMapper.selectReservationById(Integer.parseInt(userRecordInfo.get("reservationId")));
        if (reservation == null){
            result.put("result","2");
            return result; // 预约不存在
        }
        userRecord.setReservation(reservation);
        Student student = studentMapper.selectStudentById(Integer.parseInt(userRecordInfo.get("studentId")));
        if (student == null){
            result.put("result","3");
            return result; // 学生不存在
        }
        userRecord.setStudent(student);
        userRecord.setStartTime(LocalDateTime.parse(userRecordInfo.get("startTime"), localDateTimeFormatter));
        userRecord.setEndTime(null);
        if (userRecord.getStartTime().isBefore(reservation.getStartTime()) || userRecord.getStartTime().isAfter(reservation.getEndTime())) {
            result.put("result","4");
            return result; // 开始时间不在预约时间范围内
        }
        userRecord.setNotes(userRecordInfo.get("notes"));
        int flag = userRecordMapper.insertUserRecord(userRecord);
        result.put("result",String.valueOf(flag));
        if (flag == 1){
            result.put("recordId",String.valueOf(userRecord.getRecordId()));
            return result;
        } else {
            return result;
        }
    }

    @RequestMapping("/endUsing")
    public int endUsing(@RequestBody Map<String, String> userRecordInfo) {
        UserRecord userRecord = userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
        if (userRecord == null){
            return 2; // 使用记录不存在
        }
        userRecord.setEndTime(LocalDateTime.parse(userRecordInfo.get("endTime"), localDateTimeFormatter));
        return  userRecordMapper.insertUserRecord(userRecord);
    }

    @RequestMapping("/getUserRecordById")
    public UserRecord getUserRecordById(@RequestBody Map<String, String> userRecordInfo) {
        return userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
    }

    @RequestMapping("/getUserRecordsByStudentId")
    public List<UserRecord> getUserRecordsByStudentId(@RequestBody Map<String, String> studentInfo) {
        return userRecordMapper.selectUserRecordsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
    }

}

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
    public int addUserRecord(@RequestBody Map<String, String> userRecordInfo) {
        UserRecord userRecord = new UserRecord();
        Reservation  reservation = reservationMapper.selectReservationById(Integer.parseInt(userRecordInfo.get("reservationId")));
        userRecord.setReservation(reservation);
        Student student = studentMapper.selectStudentById(Integer.parseInt(userRecordInfo.get("studentId")));
        userRecord.setStudent(student);
        userRecord.setStartTime(LocalDateTime.parse(userRecordInfo.get("startTime"), localDateTimeFormatter));
        userRecord.setEndTime(null);
        if (userRecord.getStartTime().isBefore(reservation.getStartTime()) || userRecord.getStartTime().isAfter(reservation.getEndTime())) {
            return -1; // 开始时间不在预约时间范围内
        }
        userRecord.setNotes(userRecordInfo.get("notes"));
        return  userRecordMapper.insertUserRecord(userRecord);
    }

    @RequestMapping("/endUsing")
    public int endUsing(@RequestBody Map<String, String> userRecordInfo) {
        UserRecord userRecord = userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
        userRecord.setEndTime(LocalDateTime.parse(userRecordInfo.get("endTime"), localDateTimeFormatter));
        return  userRecordMapper.insertUserRecord(userRecord);
    }

    @RequestMapping("/getUserRecordById")
    public UserRecord getUserRecordById(@RequestBody Map<String, String> userRecordInfo) {
        return userRecordMapper.selectUserRecordById(Integer.parseInt(userRecordInfo.get("recordId")));
    }

    public List<UserRecord> getUserRecordsByStudentId(@RequestBody Map<String, String> studentInfo) {
        return userRecordMapper.selectUserRecordsByStudentId(Integer.parseInt(studentInfo.get("studentId")));
    }

}

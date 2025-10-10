package com.management.laboratory.controller;

import com.management.laboratory.entity.Reservation;
import com.management.laboratory.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ReservationController {
    @Autowired
    ReservationMapper reservationMapper;

    /**
     * 获取所有该教师的学生预约信息
     * @return 预约信息列表
     */
    @RequestMapping("/getAllReservationsOfTeacher")
    public List<Reservation> getAllReservationsOfTeacher(Map<String, String> teacherInfo) {
        return reservationMapper.selectAllReservationsByTeacher(Integer.parseInt(teacherInfo.get("teacherId")));
    }

    /**
     * 获取所有预约信息
     * @return 预约信息列表
     */
    @RequestMapping("/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationMapper.selectAllReservations();
    }
}


package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
/**
 * 预约实体类
 * 每个预约实体对应一个实验室
 */
public class Reservation {

    private int reservationId;

    private int labId;

    private String projectName;

    // 下面两个属性调用了 java.sql.Time 类，继承 java.util.Date 类，可能需要调换
    private Time startTime;

    private Time endTime;

    private int status;

    public Reservation() {
    }

    public Reservation(int labId, String projectName, Time startTime, Time endTime, int status) {
        this.labId = labId;
        this.projectName = projectName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Reservation(int reservationId, int labId, String projectName, Time startTime, Time endTime, int status) {
        this.reservationId = reservationId;
        this.labId = labId;
        this.projectName = projectName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", labId=" + labId +
                ", projectName='" + projectName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}

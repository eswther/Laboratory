package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 预约实体类
 * 每个预约实体对应一个实验室
 */
@Getter
@Setter
public class Reservation {

    private int reservationId;

    private int labId;

    private String projectName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int status;

    public Reservation() {
    }

    public Reservation(int labId, String projectName, LocalDateTime startTime, LocalDateTime endTime, int status) {
        this.labId = labId;
        this.projectName = projectName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Reservation(int reservationId, int labId, String projectName, LocalDateTime startTime, LocalDateTime endTime, int status) {
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

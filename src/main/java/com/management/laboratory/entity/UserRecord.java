package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户预约记录实体
 * 每个用户预约记录对应一个预约
 */
@Getter
@Setter
public class UserRecord {

    private int recordId;

    private Reservation  reservation;

    private Student student;

    private String notes;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public UserRecord() {
    }

    public UserRecord(Reservation  reservation, String notes, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservation = reservation;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UserRecord(int recordId, Reservation reservation, String notes, LocalDateTime startTime, LocalDateTime endTime) {
        this.recordId = recordId;
        this.reservation = reservation;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Userrecord{" +
                "recordId=" + recordId +
                ", reservationId=" + reservation +
                ", notes='" + notes + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

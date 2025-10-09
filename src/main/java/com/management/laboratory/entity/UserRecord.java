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

    private int reservationId;

    private String notes;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public UserRecord() {
    }

    public UserRecord(int reservationId, String notes, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = reservationId;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UserRecord(int recordId, int reservationId, String notes, LocalDateTime startTime, LocalDateTime endTime) {
        this.recordId = recordId;
        this.reservationId = reservationId;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Userrecord{" +
                "recordId=" + recordId +
                ", reservationId=" + reservationId +
                ", notes='" + notes + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

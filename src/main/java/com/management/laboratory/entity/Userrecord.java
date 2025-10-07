package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

/**
 * 用户预约记录实体
 * 每个用户预约记录对应一个预约
 */
@Getter
@Setter
public class Userrecord {

    private int recordId;

    private int reservationId;

    private String notes;

    // 下面属性调用了 java.sql.Time 类，继承 java.util.Date 类，可能需要调换
    private Time startTime;

    private Time endTime;

    public Userrecord() {
    }

    public Userrecord(int reservationId, String notes, Time startTime, Time endTime) {
        this.reservationId = reservationId;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Userrecord(int recordId, int reservationId, String notes, Time startTime, Time endTime) {
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

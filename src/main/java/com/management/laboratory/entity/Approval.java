package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 审核记录实体类
 * 记录对预约申请的审核结果
 */
@Getter
@Setter
public class Approval {

    private int approvalId;

    private Reservation reservation;

    private String notes;

    private int status;

    private LocalDateTime approvalTime;

    public Approval() {
    }

    public Approval(Reservation reservation, String notes, int status, LocalDateTime approvalTime) {
        this.reservation = reservation;
        this.notes = notes;
        this.status = status;
        this.approvalTime = approvalTime;
    }

    public Approval(int approvalId, Reservation reservation, String notes, int status, LocalDateTime approvalTime) {
        this.approvalId = approvalId;
        this.reservation = reservation;
        this.notes = notes;
        this.status = status;
        this.approvalTime = approvalTime;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "approvalId=" + approvalId +
                ", reservationId=" + reservation +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", approvalTime=" + approvalTime +
                '}';
    }
}

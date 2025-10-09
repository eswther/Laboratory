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

    private int reservationId;

    private String notes;

    private int status;

    private LocalDateTime approvalTime;

    public Approval() {
    }

    public Approval(int reservationId, String notes, int status, LocalDateTime approvalTime) {
        this.reservationId = reservationId;
        this.notes = notes;
        this.status = status;
        this.approvalTime = approvalTime;
    }

    public Approval(int approvalId, int reservationId, String notes, int status, LocalDateTime approvalTime) {
        this.approvalId = approvalId;
        this.reservationId = reservationId;
        this.notes = notes;
        this.status = status;
        this.approvalTime = approvalTime;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "approvalId=" + approvalId +
                ", reservationId=" + reservationId +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", approvalTime=" + approvalTime +
                '}';
    }
}

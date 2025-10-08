package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * 维修记录实体
 * ，每个维修记录由一个用户创建，每个维修记录对应一个设备实体
 */
@Getter
@Setter
public class Maintenance {

    private int maintenanceId;

    private int equipmentId;

    // 下面属性调用了 java.sql.Time 类，继承 java.util.Date 类，可能需要调换
    private LocalDateTime reportTime;

    private String notes;

    private int status;

    public Maintenance() {
    }

    public Maintenance(int equipmentId, LocalDateTime reportTime, String notes, int status) {
        this.equipmentId = equipmentId;
        this.reportTime = reportTime;
        this.notes = notes;
        this.status = status;
    }

    public Maintenance(int maintenanceId, int equipmentId, LocalDateTime reportTime, String notes, int status) {
        this.maintenanceId = maintenanceId;
        this.equipmentId = equipmentId;
        this.reportTime = reportTime;
        this.notes = notes;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId +
                ", equipmentId=" + equipmentId +
                ", reportTime=" + reportTime +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                '}';
    }
}

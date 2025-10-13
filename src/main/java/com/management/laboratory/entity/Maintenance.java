package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 维修记录实体
 * ，每个维修记录由一个用户创建，每个维修记录对应一个设备实体
 */
@Getter
@Setter
public class Maintenance {

    private int maintenanceId;

    private Equipment equipment;

    private LocalDateTime reportTime;

    private String notes;

    private int status;

    public Maintenance() {
    }

    public Maintenance(Equipment  equipment, LocalDateTime reportTime, String notes, int status) {
        this.equipment = equipment;
        this.reportTime = reportTime;
        this.notes = notes;
        this.status = status;
    }

    public Maintenance(int maintenanceId, Equipment  equipment, LocalDateTime reportTime, String notes, int status) {
        this.maintenanceId = maintenanceId;
        this.equipment = equipment;
        this.reportTime = reportTime;
        this.notes = notes;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId +
                ", equipmentId=" + equipment +
                ", reportTime=" + reportTime +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                '}';
    }
}

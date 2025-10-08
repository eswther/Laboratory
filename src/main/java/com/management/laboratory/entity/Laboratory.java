package com.management.laboratory.entity;


import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 实验室实体类
 * 每个实验室中有多个实验设备实体
 */
@Getter
@Setter
public class Laboratory {

    private int labId;
    private String labName;

    private String location;

    private int capacity;

    private List<Equipment> equipments;

    // 下面两个时间调用了 java.sql.Time 类，继承 java.util.Date 类
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    public Laboratory() {
    }

    public Laboratory(String labName, String location, int capacity, List<Equipment> equipments, LocalDateTime openTime, LocalDateTime closeTime) {
        this.labName = labName;
        this.location = location;
        this.capacity = capacity;
        this.equipments = equipments;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Laboratory(int labId, String labName, String location, int capacity, List<Equipment> equipments, LocalDateTime openTime, LocalDateTime closeTime) {
        this.labId = labId;
        this.labName = labName;
        this.location = location;
        this.capacity = capacity;
        this.equipments = equipments;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "labId=" + labId +
                ", labName='" + labName + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", equipments=" + equipments +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}

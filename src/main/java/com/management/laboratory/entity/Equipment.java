package com.management.laboratory.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * 设备实体类
 */
@Getter
@Setter
public class Equipment {

    private int equipmentId;

    private int labId;

    private String equipmentName;

    private String model;

    private boolean status;

    public Equipment() {
    }

    public Equipment(int labId,String equipmentName, String model, boolean status){
        this.labId = labId;
        this.equipmentName = equipmentName;
        this.model = model;
        this.status = status;
    }

    public Equipment(int labId,int equipmentId, String equipmentName, String model, boolean status){
        this.labId = labId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.model = model;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", model='" + model + '\'' +
                ", status=" + status +
                '}';
    }
}

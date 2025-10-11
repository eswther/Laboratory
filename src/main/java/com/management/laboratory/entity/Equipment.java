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

    private Laboratory  lab;

    private String equipmentName;

    private String model;

    private boolean status;

    public Equipment() {
    }

    public Equipment(Laboratory lab,String equipmentName, String model, boolean status){
        this.lab = lab;
        this.equipmentName = equipmentName;
        this.model = model;
        this.status = status;
    }

    public Equipment(Laboratory lab,int equipmentId, String equipmentName, String model, boolean status){
        this.lab = lab;
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

package com.management.laboratory.controller;

import com.management.laboratory.entity.Equipment;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.LaboratoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class EquipmentController {
    @Autowired
    LaboratoryMapper laboratoryMapper;
    @Autowired
    EquipmentMapper equipmentMapper;

    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取所有设备信息
     * @return 设备列表
     */
    @RequestMapping("/getAllEquipments")
    public List<Equipment> getAllEquipments() {
        return equipmentMapper.selectAllEquipments();
    }

    /**
     * 添加设备
     * @param equipInfo 设备信息
     * @return 添加结果
     */
    @RequestMapping("/addEquipment")
    public int addEquipment(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = new Equipment();
        equipment.setLab(laboratoryMapper.selectLaboratoryById0(Integer.parseInt(equipInfo.get("labId"))));
        if (equipment.getLab() == null) {
            return 2; // 实验室不存在
        }
        equipment.setEquipmentName(equipInfo.get("equipmentName"));
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        equipment.setModel(equipInfo.get("model"));
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 删除设备
     * @param equipInfo 设备信息
     * @return 删除结果
     */
    @RequestMapping("/deleteEquipment")
    public int deleteEquipment(@RequestBody Map<String, String> equipInfo) {
        return equipmentMapper.deleteEquipment(Integer.parseInt(equipInfo.get("equipmentId")));

    }

    /**
     * 修改设备信息
     * @param equipInfo 设备信息
     * @return 修改结果
     */
    @RequestMapping("/updateEquipment")
    public int updateEquipment(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(Integer.parseInt(equipInfo.get("equipmentId")));
        equipment.setLab(laboratoryMapper.selectLaboratoryById0(Integer.parseInt(equipInfo.get("labId"))));
        if (equipment.getLab() == null) {
            return 2; // 实验室不存在
        }
        equipment.setEquipmentName(equipInfo.get("equipmentName"));
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        equipment.setModel(equipInfo.get("model"));
        return equipmentMapper.updateEquipment(equipment);
    }

    /**
     * 查询设备信息
     * @param equipInfo 设备信息
     * @return 查询结果
     */
    @RequestMapping("/selectEquipment")
    public Equipment selectEquipment(@RequestBody Map<String, String> equipInfo) {
        return equipmentMapper.selectEquipmentById(Integer.parseInt(equipInfo.get("equipmentId")));
    }

    /**
     * 更新设备状态
     * @param equipInfo 设备信息
     * @return 更新结果
     */
    @RequestMapping("/updateEquipmentStatus")
    public int updateEquipmentStatus(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(equipInfo.get("equipmentId")));
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        return equipmentMapper.updateEquipment(equipment);
    }



}

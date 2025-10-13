package com.management.laboratory.controller;

import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Maintenance;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.MaintenanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class MaintenanceController {
    @Autowired
    MaintenanceMapper maintenanceMapper;
    @Autowired
    EquipmentMapper equipmentMapper;
    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取所有维修信息
     * @return 维修信息列表
     */
    @RequestMapping("/getAllMaintenances")
    public List<Maintenance> getAllMaintenances() {
        return maintenanceMapper.selectAllMaintenances();
    }

    /**
     * 添加维修信息
     * @return 添加结果
     */
    @RequestMapping("/addMaintenance")
    public int addMaintenance(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = new Maintenance();
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
        equipment.setStatus(false); // 设备状态设为维修中
        equipmentMapper.updateEquipment(equipment);
        maintenance.setEquipment(equipment);
        maintenance.setReportTime(LocalDateTime.parse(maintenanceInfo.get("reportTime"), localDateTimeFormatter));
        maintenance.setNotes(maintenanceInfo.get("notes"));
        maintenance.setStatus(Integer.parseInt(maintenanceInfo.get("status")));
        return maintenanceMapper.insertMaintenance(maintenance);
    }

    /**
     * 更新维修信息
     * @return 更新结果
     */
    @RequestMapping("/updateMaintenance")
    public int updateMaintenance(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = maintenanceMapper.selectMaintenanceById(Integer.parseInt(maintenanceInfo.get("maintenanceId")));
        if (maintenanceInfo.containsKey("equipmentId")) {
            Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
            maintenance.setEquipment(equipment);
        }
        if (maintenanceInfo.containsKey("reportTime")) {
            maintenance.setReportTime(LocalDateTime.parse(maintenanceInfo.get("reportTime"), localDateTimeFormatter));
        }
        if (maintenanceInfo.containsKey("notes")) {
            maintenance.setNotes(maintenanceInfo.get("notes"));
        }
        if (maintenanceInfo.containsKey("status")) {
            int newStatus = Integer.parseInt(maintenanceInfo.get("status"));
            // 如果状态从维修中变为已修复，更新设备状态
            if (maintenance.getStatus() == 0 && newStatus == 1) {
                Equipment equipment = maintenance.getEquipment();
                equipment.setStatus(true); // 设备状态设为可用
                equipmentMapper.updateEquipment(equipment);
            }
            maintenance.setStatus(newStatus);
        }
        return maintenanceMapper.updateMaintenance(maintenance);
    }

    /**
     * 更新维修信息状态
     * @return 更新结果
     */
    @RequestMapping("/updateMaintenanceStatus")
    public int updateMaintenanceStatus(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = maintenanceMapper.selectMaintenanceById(Integer.parseInt(maintenanceInfo.get("maintenanceId")));
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
        int newStatus = Integer.parseInt(maintenanceInfo.get("status"));
        if (maintenance.getStatus() == 0 && newStatus == 2) {
            equipment.setStatus(true); // 设备状态设为可用
            equipmentMapper.updateEquipmentStatus(equipment);
        }
        maintenance.setStatus(newStatus); // 设备状态设为维修中
        return  maintenanceMapper.updateMaintenanceStatus(maintenance.getMaintenanceId(), maintenance.getStatus());
    }
}

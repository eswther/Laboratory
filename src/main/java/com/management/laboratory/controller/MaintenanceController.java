package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Maintenance;
import com.management.laboratory.entity.User;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.MaintenanceMapper;
import com.management.laboratory.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有维修信息
     * @return 维修信息列表
     */
    @RequestMapping("/getAllMaintenances")
    public ApiResponse<List<Maintenance>> getAllMaintenances() {
        try {
            List<Maintenance> maintenances = maintenanceMapper.selectAllMaintenances();
            return ResponseUtils.ok("获取所有维修数据", maintenances);
        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    public ApiResponse<List<Maintenance>> getMaintenancesByPage(Integer page, Integer size){
        try {
            // 参数校验
            if (page == null || page < 1) {
                page = 1;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            if (size > 100) {
                size = 100; // 限制每页最大数量
            }
            // 计算偏移量
            int offset = (page - 1) * size;
            // 查询数据
            List<Maintenance> maintenances = maintenanceMapper.selectMaintenancesByPage(offset, size);
            int total = maintenanceMapper.countMaintenance();
            return ResponseUtils.ok("获取维修信息列表成功", maintenances);
        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 添加维修信息
     * @return 添加结果
     */
    @RequestMapping("/addMaintenance")
    public ApiResponse<Void> addMaintenance(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = new Maintenance();
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
        if (equipment == null){
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_NOT_EXIST); // 设备不存在
        }
        equipment.setStatus(false); // 设备状态设为维修中
        if(equipmentMapper.updateEquipment(equipment)!=1){;
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_UPDATE_FAILURE); // 设备状态更新失败
        }
        User user = userMapper.selectUserByUserId(Integer.parseInt(maintenanceInfo.get("userId")));
        if (user == null){
            return ResponseUtils.fail(ResponseCode.USER_NOT_EXIST); // 用户不存在
        }
        maintenance.setEquipment(equipment);
        maintenance.setUser(user);
        maintenance.setReportTime(LocalDateTime.parse(maintenanceInfo.get("reportTime"), localDateTimeFormatter));
        maintenance.setNotes(maintenanceInfo.get("notes"));
        maintenance.setStatus(Integer.parseInt(maintenanceInfo.get("status")));
        if (maintenanceMapper.insertMaintenance(maintenance)==1){
            return ResponseUtils.ok("维修信息添加成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 更新维修信息
     * @return 更新结果
     */
    @RequestMapping("/updateMaintenance")
    public ApiResponse<Void> updateMaintenance(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = maintenanceMapper.selectMaintenanceById(Integer.parseInt(maintenanceInfo.get("maintenanceId")));
        if (maintenance == null){
            return ResponseUtils.fail(ResponseCode.MAINTENANCE_NOT_EXIST); // 维修信息不存在
        }
        if (maintenanceInfo.containsKey("equipmentId")) {
            Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
            if (equipment == null){
                return ResponseUtils.fail(ResponseCode.EQUIPMENT_NOT_EXIST); // 设备不存在
            }
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
            if (maintenance.getStatus() == 1 && newStatus == 2) {
                Equipment equipment = maintenance.getEquipment();
                equipment.setStatus(true); // 设备状态设为可用
                if(equipmentMapper.updateEquipment(equipment)!=1){;
                    return ResponseUtils.fail(ResponseCode.EQUIPMENT_UPDATE_FAILURE); // 设备状态更新失败
                }
            }else if(maintenance.getStatus() == 2 && newStatus == 0){
                Equipment equipment = maintenance.getEquipment();
                equipment.setStatus(false); // 设备状态设为可用
                if(equipmentMapper.updateEquipment(equipment)!=1){;
                    return ResponseUtils.fail(ResponseCode.EQUIPMENT_UPDATE_FAILURE); // 设备状态更新失败
                }
            }
            maintenance.setStatus(newStatus);
        }
        if (maintenanceMapper.updateMaintenance(maintenance)==1){
            return ResponseUtils.ok("维修信息更新成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 更新维修信息状态
     * @return 更新结果
     */
    @RequestMapping("/updateMaintenanceStatus")
    public ApiResponse<Void>  updateMaintenanceStatus(@RequestBody Map<String, String> maintenanceInfo) {
        Maintenance maintenance = maintenanceMapper.selectMaintenanceById(Integer.parseInt(maintenanceInfo.get("maintenanceId")));
        if (maintenance == null){
            return ResponseUtils.fail(ResponseCode.MAINTENANCE_NOT_EXIST); // 维修信息不存在
        }
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(maintenanceInfo.get("equipmentId")));
        if (equipment == null){
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_NOT_EXIST); // 设备不存在
        }
        maintenance.setEquipment(equipment);
        int newStatus = Integer.parseInt(maintenanceInfo.get("status"));
        if (maintenance.getStatus() == 1 && newStatus == 2) {
            equipment.setStatus(true); // 设备状态设为可用
            if(equipmentMapper.updateEquipment(equipment)!=1){;
                return ResponseUtils.fail(ResponseCode.EQUIPMENT_UPDATE_FAILURE); // 设备状态更新失败
            }
        }else if(maintenance.getStatus() == 2 && newStatus == 0){
            equipment.setStatus(false); // 设备状态设为可用
            if(equipmentMapper.updateEquipment(equipment)!=1){;
                return ResponseUtils.fail(ResponseCode.EQUIPMENT_UPDATE_FAILURE); // 设备状态更新失败
            }
        }
        maintenance.setStatus(newStatus); // 设备状态设为维修中
        if(maintenanceMapper.updateMaintenanceStatus(maintenance.getMaintenanceId(), maintenance.getStatus())==1){
            return ResponseUtils.ok("维修信息状态更新成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }
}

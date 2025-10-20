package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.LaboratoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ApiResponse<List<Equipment>> getAllEquipments() {
        try {
            List<Equipment> equipments = equipmentMapper.selectAllEquipments();
            return ResponseUtils.ok("获取所有设备数据", equipments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }

    }

    /**
     * 分页获取所有设备信息
     * @return 设备列表
     */
    @RequestMapping("/getEquipmentsByPage")
    public ApiResponse<List<Equipment>> getEquipmentsByPage(@RequestParam(defaultValue = "1") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
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
            List<Equipment> equipments = equipmentMapper.selectEquipmentsByPage(offset, size);
            int total = equipmentMapper.countEquipments();
            return ResponseUtils.ok("获取设备列表成功", equipments);
        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 添加设备
     * @param equipInfo 设备信息
     * @return 添加结果
     */
    @RequestMapping("/addEquipment")
    public ApiResponse<Void> addEquipment(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = new Equipment();
        equipment.setLab(laboratoryMapper.selectLaboratoryById0(Integer.parseInt(equipInfo.get("labId"))));
        if (equipment.getLab() == null) {
            return ResponseUtils.fail(ResponseCode.LAB_NOT_EXIST); // 实验室不存在
        }
        equipment.setEquipmentName(equipInfo.get("equipmentName"));
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        equipment.setModel(equipInfo.get("model"));
        if (equipmentMapper.insertEquipment(equipment)==1){
            return ResponseUtils.ok("设备添加成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 删除设备
     * @param equipInfo 设备信息
     * @return 删除结果
     */
    @RequestMapping("/deleteEquipment")
    public ApiResponse<Void> deleteEquipment(@RequestBody Map<String, String> equipInfo) {
        if (equipmentMapper.deleteEquipment(Integer.parseInt(equipInfo.get("equipmentId")))==1){
            return ResponseUtils.ok("设备删除成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 修改设备信息
     * @param equipInfo 设备信息
     * @return 修改结果
     */
    @RequestMapping("/updateEquipment")
    public ApiResponse<Void> updateEquipment(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(Integer.parseInt(equipInfo.get("equipmentId")));
        equipment.setLab(laboratoryMapper.selectLaboratoryById0(Integer.parseInt(equipInfo.get("labId"))));
        if (equipment.getLab() == null) {
            return ResponseUtils.fail(ResponseCode.LAB_NOT_EXIST); // 实验室不存在
        }
        equipment.setEquipmentName(equipInfo.get("equipmentName"));
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        equipment.setModel(equipInfo.get("model"));
        if (equipmentMapper.updateEquipment(equipment)==1){
            return ResponseUtils.ok("设备更新成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 查询设备信息
     * @param equipInfo 设备信息
     * @return 查询结果
     */
    @RequestMapping("/selectEquipment")
    public ApiResponse<Equipment> selectEquipment(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(equipInfo.get("equipmentId")));
        if (equipment!=null){
            return ResponseUtils.ok("设备查询成功", equipment);
        }else {
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_NOT_EXIST);
        }
    }

    /**
     * 更新设备状态
     * @param equipInfo 设备信息
     * @return 更新结果
     */
    @RequestMapping("/updateEquipmentStatus")
    public ApiResponse<Void> updateEquipmentStatus(@RequestBody Map<String, String> equipInfo) {
        Equipment equipment = equipmentMapper.selectEquipmentById(Integer.parseInt(equipInfo.get("equipmentId")));
        if (equipment == null){
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_NOT_EXIST); // 设备不存在
        }
        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
        if (equipmentMapper.updateEquipmentStatus(equipment)==1){
            return ResponseUtils.ok("设备状态更新成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

}

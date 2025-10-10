package com.management.laboratory.controller;

import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;
import com.management.laboratory.mapper.AdminMapper;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.LaboratoryMapper;
import com.management.laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AdminController {


    @Autowired
    AdminMapper adminMapper;
    @Autowired
    LaboratoryMapper laboratoryMapper;
    @Autowired
    EquipmentMapper equipmentMapper;

    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter localTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

//    /**
//     * 获取所有实验室信息
//     * @return 实验室列表
//     */
//    @RequestMapping("/getAllLaboratories")
//    public List<Laboratory> getAllLaboratories() {
//        return laboratoryMapper.selectAllLaboratories();
//    }
//
//    /**
//     * 添加实验室
//     * @param labInfo 实验室信息
//     * @return 添加结果
//     */
//    @RequestMapping("/addLab")
//    public int addLab(Map<String, String> labInfo) {
//
//        Laboratory laboratory = new Laboratory();
//        laboratory.setLabName(labInfo.get("labName"));
//        laboratory.setLocation(labInfo.get("location"));
//        laboratory.setCapacity(Integer.parseInt(labInfo.get("capacity")));
//        laboratory.setOpenTime(LocalTime.parse(labInfo.get("openTime"), localTimeFormat));
//        laboratory.setCloseTime(LocalTime.parse(labInfo.get("closeTime"), localTimeFormat));
//        // 设备信息暂时为空
//        laboratory.setEquipments(null);
//        int result = laboratoryMapper.insertLaboratory(laboratory);
//        return result;
//    }
//
//    /**
//     * 删除实验室
//     * @param labInfo 实验室信息
//     * @return 删除结果
//     */
//    @RequestMapping("/deleteLab")
//    public int deleteLab(Map<String, String> labInfo) {
//        int result0 = 0;
//        int result1 = 0;
//        result0 = equipmentMapper.deleteEquipmentByLabId(Integer.parseInt(labInfo.get("labId")));
//        result0 = laboratoryMapper.deleteLaboratory(Integer.parseInt(labInfo.get("labId")));
//        if (result0 == 1 && result1 == 1){
//            return 0;// 删除成功
//        }else if(result0 == 0 && result1 == 1){
//            return 1;// 删除设备失败
//        }else if(result1 == 0){
//            return 2;// 删除实验室失败
//        }else {
//            return 3;// 删除设备和实验室都失败
//        }
//    }
//
//    /**
//     * 修改实验室信息
//     * @param labInfo 实验室信息
//     * @return 修改结果
//     */
//    @RequestMapping("/updateLab")
//    public int updateLab(Map<String, String> labInfo) {
//        Laboratory laboratory = new Laboratory();
//        laboratory.setLabName(labInfo.get("labName"));
//        laboratory.setLocation(labInfo.get("location"));
//        laboratory.setCapacity(Integer.parseInt(labInfo.get("capacity")));
//        laboratory.setOpenTime(LocalTime.parse(labInfo.get("openTime"), localTimeFormat));
//        laboratory.setCloseTime(LocalTime.parse(labInfo.get("closeTime"), localTimeFormat));
//        return laboratoryMapper.updateLaboratory(laboratory);
//    }
//
//    /**
//     * 查询实验室信息
//     * @param labInfo 实验室信息
//     * @return 查询结果
//     */
//    @RequestMapping("/selectLab")
//    public Laboratory selectLab(Map<String, String> labInfo) {
//        Laboratory laboratory = laboratoryMapper.selectLaboratoryById(Integer.parseInt(labInfo.get("labId")));
//        return laboratory;
//    }
//
//    /**
//     * 获取所有设备信息
//     * @return 设备列表
//     */
//    @RequestMapping("/getAllEquipments")
//    public List<Equipment> getAllEquipments() {
//        return equipmentMapper.selectAllEquipments();
//    }
//
//    /**
//     * 添加设备
//     * @param equipInfo 设备信息
//     * @return 添加结果
//     */
//    @RequestMapping("/addEquipment")
//    public int addEquipment(Map<String, String> equipInfo) {
//        Equipment equipment = new Equipment();
//        equipment.setLabId(Integer.parseInt(equipInfo.get("labId")));
//        equipment.setEquipmentName(equipInfo.get("equipmentName"));
//        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
//        equipment.setModel(equipInfo.get("model"));
//        equipmentMapper.insertEquipment(equipment);
//        return 0;
//    }
//
//    /**
//     * 删除设备
//     * @param equipInfo 设备信息
//     * @return 删除结果
//     */
//    @RequestMapping("/deleteEquipment")
//    public int deleteEquipment(Map<String, String> equipInfo) {
//        return equipmentMapper.deleteEquipment(Integer.parseInt(equipInfo.get("equipmentId")));
//
//    }
//
//    /**
//     * 修改设备信息
//     * @param equipInfo 设备信息
//     * @return 修改结果
//     */
//    @RequestMapping("/updateEquipment")
//    public int updateEquipment(Map<String, String> equipInfo) {
//        Equipment equipment = new Equipment();
//        equipment.setLabId(Integer.parseInt(equipInfo.get("labId")));
//        equipment.setEquipmentName(equipInfo.get("equipmentName"));
//        equipment.setStatus(Boolean.parseBoolean(equipInfo.get("status")));
//        equipment.setModel(equipInfo.get("model"));
//        return 0;
//    }
//
//    /**
//     * 查询设备信息
//     * @param equipInfo 设备信息
//     * @return 查询结果
//     */
//    @RequestMapping("/selectEquipment")
//    public Equipment selectEquipment(Map<String, String> equipInfo) {
//        return equipmentMapper.selectEquipmentById(Integer.parseInt(equipInfo.get("equipmentId")));
//    }

}

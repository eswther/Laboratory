package com.management.laboratory.controller;

import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;
import com.management.laboratory.mapper.AdminMapper;
import com.management.laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class AdminController {


    @Autowired
    AdminMapper adminMapper;

    /**
     * 添加实验室
     * @param labInfo 实验室信息
     * @return 添加结果
     */
    @RequestMapping("/addLab")
    public int addLab(Map<String, String> labInfo) {
        Laboratory laboratory = new Laboratory();

        return 0;
    }

    /**
     * 删除实验室
     * @param labInfo 实验室信息
     * @return 删除结果
     */
    @RequestMapping("/deleteLab")
    public int deleteLab(Map<String, String> labInfo) {
        Laboratory laboratory = new Laboratory();

        return 0;
    }

    /**
     * 修改实验室信息
     * @param labInfo 实验室信息
     * @return 修改结果
     */
    @RequestMapping("/updateLab")
    public int updateLab(Map<String, String> labInfo) {
        Laboratory laboratory = new Laboratory();

        return 0;
    }

    /**
     * 查询实验室信息
     * @param labInfo 实验室信息
     * @return 查询结果
     */
    @RequestMapping("/selectLab")
    public Laboratory selectLab(Map<String, String> labInfo) {
        Laboratory laboratory = new Laboratory();

        return laboratory;
    }

    /**
     * 添加设备
     * @param labInfo 设备信息
     * @return 添加结果
     */
    @RequestMapping("/addEquipment")
    public int addEquipment(Map<String, String> labInfo) {
        Equipment equipment = new Equipment();

        return 0;
    }

    /**
     * 删除设备
     * @param labInfo 设备信息
     * @return 删除结果
     */
    @RequestMapping("/deleteEquipment")
    public int deleteEquipment(Map<String, String> labInfo) {
        Equipment equipment = new Equipment();

        return 0;
    }

    /**
     * 修改设备信息
     * @param labInfo 设备信息
     * @return 修改结果
     */
    @RequestMapping("/updateEquipment")
    public int updateEquipment(Map<String, String> labInfo) {
        Equipment equipment = new Equipment();

        return 0;
    }

    /**
     * 查询设备信息
     * @param labInfo 设备信息
     * @return 查询结果
     */
    @RequestMapping("/selectEquipment")
    public Equipment selectEquipment(Map<String, String> labInfo) {
        Equipment equipment = new Equipment();

        return equipment;
    }
}

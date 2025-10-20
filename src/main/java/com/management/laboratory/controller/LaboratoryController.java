package com.management.laboratory.controller;

import com.management.laboratory.ApiResponse;
import com.management.laboratory.ResponseCode;
import com.management.laboratory.ResponseUtils;
import com.management.laboratory.entity.Laboratory;
import com.management.laboratory.entity.Teacher;
import com.management.laboratory.mapper.EquipmentMapper;
import com.management.laboratory.mapper.LaboratoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LaboratoryController {
    @Autowired
    LaboratoryMapper laboratoryMapper;
    @Autowired
    EquipmentMapper equipmentMapper;

    DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter localTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * 获取所有实验室信息
     * @return 实验室列表
     */
    @RequestMapping("/getAllLaboratories")
    public ApiResponse<List<Laboratory>> getAllLaboratories() {
        try {
            List<Laboratory> laboratories = laboratoryMapper.selectAllLaboratories();
            return ResponseUtils.ok("获取所有实验室数据", laboratories);
        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    public ApiResponse<List<Laboratory>> getLaboratoriesByPage(@RequestParam(defaultValue = "1") Integer page,
                                                               @RequestParam(defaultValue = "10") Integer size){
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
            List<Laboratory> laboratories = laboratoryMapper.selectLaboratoriesByPage(offset, size);
            int total = laboratoryMapper.countLaboratory();
            return ResponseUtils.ok("获取实验室列表成功", laboratories);

        } catch (Exception e) {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }



    /**
     * 添加实验室
     * @param labInfo 实验室信息
     * @return 添加结果
     */
    @RequestMapping("/addLab")
    public ApiResponse<Void> addLab(@RequestBody Map<String, String> labInfo) {

        Laboratory laboratory = new Laboratory();
        laboratory.setLabName(labInfo.get("labName"));
        laboratory.setLocation(labInfo.get("location"));
        laboratory.setCapacity(Integer.parseInt(labInfo.get("capacity")));
        laboratory.setOpenTime(LocalTime.parse(labInfo.get("openTime"), localTimeFormat));
        laboratory.setCloseTime(LocalTime.parse(labInfo.get("closeTime"), localTimeFormat));
        // 设备信息暂时为空
        laboratory.setEquipments(null);
        int result = laboratoryMapper.insertLaboratory(laboratory);
        if (result == 1){
            return ResponseUtils.ok("实验室添加成功", null);
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 删除实验室
     * @param labInfo 实验室信息
     * @return 删除结果
     */
    @RequestMapping("/deleteLab")
    public ApiResponse<Void> deleteLab(@RequestBody Map<String, String> labInfo) {
        int result0 = 0;
        int result1 = 0;
        result0 = equipmentMapper.deleteEquipmentByLabId(Integer.parseInt(labInfo.get("labId")));
        result1 = laboratoryMapper.deleteLaboratory(Integer.parseInt(labInfo.get("labId")));
        if (result1 == 1){
            return ResponseUtils.ok("删除成功", null);// 删除成功
        }else if(result0 == 0 && result1 == 0){
            return ResponseUtils.fail(ResponseCode.EQUIPMENT_DELETE_FAILURE);// 删除设备失败
        }else if(result1 == 0){
            return ResponseUtils.fail(ResponseCode.LABORATORY_DELETE_FAILURE);// 删除实验室失败
        }else {
            return ResponseUtils.fail(ResponseCode.DATABASE_ERROR);// 删除设备和实验室都失败
        }
    }

    /**
     * 修改实验室信息
     * @param labInfo 实验室信息
     * @return 修改结果
     */
    @RequestMapping("/updateLab")
    public ApiResponse<Void> updateLab(@RequestBody Map<String, String> labInfo) {
        Laboratory laboratory = new Laboratory();
        laboratory.setLabId(Integer.parseInt(labInfo.get("labId")));
        laboratory.setLabName(labInfo.get("labName"));
        laboratory.setLocation(labInfo.get("location"));
        laboratory.setCapacity(Integer.parseInt(labInfo.get("capacity")));
        laboratory.setOpenTime(LocalTime.parse(labInfo.get("openTime"), localTimeFormat));
        laboratory.setCloseTime(LocalTime.parse(labInfo.get("closeTime"), localTimeFormat));
        if (laboratoryMapper.updateLaboratory(laboratory)==1){
            return ResponseUtils.ok("实验室信息修改成功", null);
        }else {
            return  ResponseUtils.fail(ResponseCode.DATABASE_ERROR);
        }
    }

    /**
     * 查询实验室信息
     * @param labInfo 实验室信息
     * @return 查询结果
     */
    @RequestMapping("/selectLab")
    public ApiResponse<Laboratory> selectLab(@RequestBody Map<String, String> labInfo) {
        Laboratory laboratory = laboratoryMapper.selectLaboratoryById(Integer.parseInt(labInfo.get("labId")));
        if (laboratory == null){
            return  ResponseUtils.fail(ResponseCode.LAB_NOT_EXIST);
        }else {
            return ResponseUtils.ok("实验室信息查询成功", laboratory);
        }
    }
}

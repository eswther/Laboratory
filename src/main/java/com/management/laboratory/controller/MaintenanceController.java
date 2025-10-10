package com.management.laboratory.controller;

import com.management.laboratory.entity.Maintenance;
import com.management.laboratory.mapper.MaintenanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class MaintenanceController {
    @Autowired
    MaintenanceMapper maintenanceMapper;

    /**
     * 获取所有维修信息
     * @return 维修信息列表
     */
    @RequestMapping("/getAllMaintenances")
    public List<Maintenance> getAllMaintenances() {
        return maintenanceMapper.selectAllMaintenances();
    }
}

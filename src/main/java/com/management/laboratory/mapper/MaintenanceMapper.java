package com.management.laboratory.mapper;

import com.management.laboratory.entity.Maintenance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MaintenanceMapper {
    /**
     * 获取所有维修信息
     * @return 维修信息列表
     */
    @Select("SELECT * FROM maintenance")
    List<Maintenance> selectAllMaintenances();
}

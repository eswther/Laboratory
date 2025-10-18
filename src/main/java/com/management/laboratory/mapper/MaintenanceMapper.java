package com.management.laboratory.mapper;

import com.management.laboratory.entity.Maintenance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaintenanceMapper {
    /**
     * 获取所有维修信息
     * @return 维修信息列表
     */
    @Select("SELECT m.maintenance_id, m.equipment_id, m.report_time, m.notes, m.status, " +
            "       e.equipment_id, e.name AS equipment_name, e.model AS equipment_model, e.status AS equipment_status, " +
            "       l.lab_id, l.name AS lab_name, l.location AS lab_location " +
            "FROM maintenance m " +
            "JOIN equipment e ON m.equipment_id = e.equipment_id " +
            "JOIN laboratory l ON e.lab_id = l.lab_id ")
    @Results({
            @Result(property = "maintenanceId", column = "maintenance_id"),
            @Result(property = "reportTime", column = "report_time"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "status", column = "status"),
            @Result(property = "equipment.equipmentId", column = "equipment_id"),
            @Result(property = "equipment.equipmentName", column = "equipment_name"),
            @Result(property = "equipment.model", column = "equipment_model"),
            @Result(property = "equipment.status", column = "equipment_status"),
            @Result(property = "equipment.lab.labId", column = "lab_id"),
            @Result(property = "equipment.lab.name", column = "lab_name"),
            @Result(property = "equipment.lab.location", column = "lab_location")
    })
    List<Maintenance> selectAllMaintenances();



    /**
     * 根据维保id获取维修信息
     * @param maintenanceId 维保id
     * @return 维修信息
     */
    @Select("SELECT m.maintenance_id, m.equipment_id, m.report_time, m.notes, m.status, " +
            "       e.equipment_id, e.name AS equipment_name, e.model AS equipment_model, e.status AS equipment_status, " +
            "       l.lab_id, l.name AS lab_name, l.location AS lab_location " +
            "FROM maintenance m " +
            "JOIN equipment e ON m.equipment_id = e.equipment_id " +
            "JOIN laboratory l ON e.lab_id = l.lab_id " +
            "WHERE m.maintenance_id = #{maintenanceId}")
    @Results({
            @Result(property = "maintenanceId", column = "maintenance_id"),
            @Result(property = "reportTime", column = "report_time"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "status", column = "status"),
            @Result(property = "equipment.equipmentId", column = "equipment_id"),
            @Result(property = "equipment.equipmentName", column = "equipment_name"),
            @Result(property = "equipment.model", column = "equipment_model"),
            @Result(property = "equipment.status", column = "equipment_status"),
            @Result(property = "equipment.lab.labId", column = "lab_id"),
            @Result(property = "equipment.lab.name", column = "lab_name"),
            @Result(property = "equipment.lab.location", column = "lab_location")
    })
    Maintenance selectMaintenanceById(int maintenanceId);

    /**
     * 根据维保id获取维修信息（不包含关联信息）
     * @param maintenanceId 维保id
     * @return 维修信息
     */
    @Select("SELECT * FROM maintenance WHERE maintenance_id = #{maintenanceId}")
    Maintenance selectMaintenanceById0(int maintenanceId);


    /**
     * 插入维修信息
     * @param maintenance 维修信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO maintenance (equipment_id, report_time, notes, status) " +
            "VALUES (#{equipment.equipmentId}, #{reportTime}, #{notes}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "maintenanceId", keyColumn = "maintenance_id")
    int insertMaintenance(Maintenance maintenance);

    /**
     * 更新维修信息
     * @param maintenance 维修信息
     * @return 影响的行数
     */
    @Update("UPDATE maintenance " +
            "SET equipment_id = #{equipment.equipmentId}, " +
            "report_time = #{reportTime}, " +
            "notes = #{notes}, " +
            "status = #{status} " +
            "WHERE maintenance_id = #{maintenanceId}")
    @Options(useGeneratedKeys = true, keyProperty = "maintenanceId", keyColumn = "maintenance_id")
    int updateMaintenance(Maintenance maintenance);

    /**
    *  更新维保状态
    *  @param maintenanceId 维保id
    *  @param status 维保状态
    *  @return 影响的行数
    */
    @Update("UPDATE maintenance " +
            "SET status = #{status} " +
            "WHERE maintenance_id = #{maintenanceId}")
    @Options(useGeneratedKeys = true, keyProperty = "maintenanceId", keyColumn = "maintenance_id")
    int updateMaintenanceStatus(@Param("maintenanceId") int maintenanceId, @Param("status") int status);

}

package com.management.laboratory.mapper;

import com.management.laboratory.entity.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EquipmentMapper {
    /**
     * 添加设备
     * @param equipment 设备信息
     * @return 添加结果
     */
    @Insert("INSERT INTO equipment (lab_id, equipment_name, model, status) " +
            "VALUES (#{labId}, #{equipmentName}, #{model}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int insertEquipment(com.management.laboratory.entity.Equipment equipment);

    /**
     * 删除设备
     * @param id 设备 id
     * @return 删除结果
     */
    @Delete("DELETE FROM equipment WHERE equipment_id = #{id}")
    int deleteEquipment(int id);

    /**
     * 根据 id 查询设备信息
     * @param id 设备 id
     * @return 设备信息
     */
    @Select("SELECT * FROM equipment WHERE equipment_id = #{id}")
    Equipment selectEquipmentById(int id);

    /**
     * 根据实验室 id 删除设备
     * @param labId 实验室 id
     * @return 删除结果
     */
    @Delete("DELETE FROM equipment WHERE lab_id = #{labId}")
    int deleteEquipmentByLabId(int labId);

    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 更新结果
     */
    @Update("UPDATE equipment " +
            "SET equipment_name = #{equipmentName}, lab_id = #{labId}, " +
            "equipment_name = #{equipmentName}, model = #{model}, status = #{status}")
    @Options (useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int updateEquipment(com.management.laboratory.entity.Equipment equipment);

    /**
     * 根据 name 查询设备信息
     * @param name 设备 name
     * @return 设备信息
     */
    @Select("SELECT * FROM equipment WHERE equipment_name = #{name}")
    @Results({
            @Result(property = "equipmentId", column = "equipment_id"), // 映射 equipment 表的 id
            @Result(property = "labId", column = "lab_id"), // 映射 equipment 表的 lab_id 字段
            @Result(property = "equipmentName", column = "equipment_name"), // 映射 equipment 表的 equipment_name 字段
            @Result(property = "model", column = "model"), // 映射 equipment 表的 model 字段
            @Result(property = "status", column = "status") // 映射 equipment 表的 status 字段
    })
    com.management.laboratory.entity.Equipment selectEquipmentByName(String name);

    /**
     * 根据实验室 id 查询设备信息
     * @param labId 实验室 id
     * @return 设备信息列表
     */
    @Select("SELECT * FROM equipment WHERE lab_id = #{labId}")
    List<Equipment> selectEquipmentByLabId(int labId);

    /**
     * 查询所有设备信息
     * @return 设备信息列表
     */
    @Select("SELECT * FROM equipment")
    List<Equipment> selectAllEquipments();

}

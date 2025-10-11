package com.management.laboratory.mapper;

import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;
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
            "VALUES (#{lab.labId}, #{equipmentName}, #{model}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int insertEquipment(Equipment equipment);

    /**
     * 删除设备
     * @param equipmentId 设备 id
     * @return 删除结果
     */
    @Delete("DELETE FROM equipment WHERE equipment_id = #{equipmentId}")
    int deleteEquipment(int equipmentId);

    /**
     * 根据 id 查询设备信息
     * @param id 设备 id
     * @return 设备信息
     */
    @Select("SELECT * FROM equipment WHERE equipment_id = #{id}")
    @Results({
            @Result(property = "equipmentId", column = "equipment_id"),
            @Result(property = "equipmentName", column = "equipment_name"),
            @Result(property = "model", column = "model"),
            @Result(property = "status", column = "status"),
            @Result(property = "lab", column = "lab_id",
                    one = @One(select = "com.management.laboratory.mapper.LaboratoryMapper.selectLaboratoryById0"))
    })
    Equipment selectEquipmentById(int id);

    /**
     * 根据 id 查询设备信息（不包含实验室信息）
     * @param id 设备 id
     * @return 设备信息
     */
    @Select("SELECT * FROM equipment WHERE equipment_id = #{id}")
    Equipment selectEquipmentById0(int id);

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
            "SET equipment_name = #{equipmentName}, lab_id = #{lab.LabId}, " +
            "equipment_name = #{equipmentName}, model = #{model}, status = #{status}")
    @Options (useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int updateEquipment(com.management.laboratory.entity.Equipment equipment);

    /**
     * 根据设备名称查询设备信息（包含实验室信息）
     * @param name 设备名称
     * @return 设备信息
     */
    @Select("SELECT equipment_id, equipment_name, model, status, lab_id " +
            "FROM equipment " +
            "WHERE equipment_name = #{name}")
    @Results({
            @Result(property = "equipmentId", column = "equipment_id"),
            @Result(property = "equipmentName", column = "equipment_name"),
            @Result(property = "model", column = "model"),
            @Result(property = "status", column = "status"),
            @Result(property = "lab", column = "lab_id", javaType = Laboratory.class,
                    one = @One(select = "com.management.laboratory.mapper.LaboratoryMapper.selectLaboratoryById0"))
    })
    Equipment selectEquipmentByName(String name);

    /**
     * 根据设备名称查询设备信息（不包含实验室信息）
     * @param name 设备名称
     * @return 设备信息
     */
    @Select("SELECT * FROM equipment WHERE equipment_name = #{name}")
    Equipment selectEquipmentByName0(String name);


    /**
     * 根据实验室 id 查询设备信息
     * @param labId 实验室 id
     * @return 设备信息列表
     */
    @Select("SELECT e.equipment_id, e.equipment_name, e.model, e.status, " +
            "       l.lab_id, l.lab_name, l.location, l.capacity, l.openTime, l.closeTime " +
            "FROM equipment e " +
            "JOIN laboratory l ON e.lab_id = l.lab_id " +
            "WHERE e.lab_id = #{labId}")
    // 定义可重用的结果映射
    @Results(id = "equipmentWithLabMap", value = {
            @Result(property = "equipmentId", column = "equipment_id"),
            @Result(property = "equipmentName", column = "equipment_name"),
            @Result(property = "model", column = "model"),
            @Result(property = "status", column = "status"),
            // 直接映射 Laboratory 对象属性
            @Result(property = "lab.labId", column = "lab_id"),
            @Result(property = "lab.labName", column = "lab_name"),
            @Result(property = "lab.location", column = "location"),
            @Result(property = "lab.capacity", column = "capacity"),
            @Result(property = "lab.openTime", column = "openTime"),
            @Result(property = "lab.closeTime", column = "closeTime")
    })
    List<Equipment> selectEquipmentByLabId(int labId);

    /**
     * 根据实验室 id 查询设备信息（不包含实验室信息）
     * @param labId 实验室 id
     * @return 设备信息列表
     */
    @Select("SELECT * FROM equipment WHERE lab_id = #{labId}")
    List<Equipment> selectEquipmentByLabId0(int labId);

    /**
     * 查询所有设备信息
     * @return 设备信息列表
     */
    @Select("SELECT e.equipment_id, e.equipment_name, e.model, e.status, " +
            "       l.lab_id, l.lab_name, l.location, l.capacity, l.openTime, l.closeTime " +
            "FROM equipment e " +
            "JOIN laboratory l ON e.lab_id = l.lab_id ")
    @ResultMap("equipmentWithLabMap")
    List<Equipment> selectAllEquipments();
}

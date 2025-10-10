package com.management.laboratory.mapper;

import com.management.laboratory.entity.Admin;
import com.management.laboratory.entity.Equipment;
import com.management.laboratory.entity.Laboratory;

import org.apache.ibatis.annotations.*;

/**
 * 管理员信息映射类
 * 将管理员信息与数据库中admin表进行映射
 */
@Mapper
public interface AdminMapper {

    /**
     * 根据 user_id 查询管理员信息
     * @param id 用户 id
     * @return 管理员信息
     */
    @Select("SELECT " +
            "a.*," +
            "u.account, " +
            "u.password, " +
            "u.permission " +
            "FROM admin a " +
            "JOIN user u ON a.user_id = u.user_id " +
            "WHERE u.user_id = #{id}")
    @Results({
            @Result(property = "adminId", column = "admin_id"), // 映射 admin 表的 id
            @Result(property = "userId", column = "user_id"), // 映射外键 user_id
            @Result(property = "name", column = "name"), // 映射 admin 表的 role 字段
            @Result(property = "account", column = "account"), // 映射 user 表的 account 字段
            @Result(property = "password", column = "password"), // 映射 user 表的 password 字段
            @Result(property = "permission", column = "permission")// 映射 user 表的 permission 字段
    })
    Admin selectAdminByUserId(int id);

    /**
     * 根据 name 获取实验室信息
     * @param name 实验室 name
     * @return 实验室信息
     */
    @Select("SELECT * FROM laboratory WHERE lab_name = #{name}")
    @Results({
            @Result(property = "laboratoryId", column = "laboratory_id"), // 映射 laboratory 表的 id
            @Result(property = "name", column = "name"), // 映射 laboratory 表的 name 字段
            @Result(property = "location", column = "location"), // 映射 laboratory 表的 location 字段
            @Result(property = "capacity", column = "capacity"), // 映射 laboratory 表的 capacity 字段
            @Result(property = "status", column = "status") // 映射 laboratory 表的 status 字段
    })
    Laboratory selectLaboratoryByName(String  name);

    /**
     * 根据 status 获取实验室信息
     * @param status 实验室 status
     * @return 实验室信息
     */
    @Select("SELECT * FROM laboratory WHERE status = #{status}")
    @Results({
            @Result(property = "laboratoryId", column = "laboratory_id"), // 映射 laboratory 表的 id
            @Result(property = "name", column = "name"), // 映射 laboratory 表的 name 字段
            @Result(property = "location", column = "location"), // 映射 laboratory 表的 location 字段
            @Result(property = "capacity", column = "capacity"), // 映射 laboratory 表的 capacity 字段
            @Result(property = "status", column = "status") // 映射 laboratory 表的 status 字段
    })
    Laboratory selectLabotatoryByStatus(int status);

    /**
     * 增加实验室
     * @param laboratory 实验室信息
     * @return 增加结果
     */
    @Insert("INSERT INTO laboratory (lab_name, location, capacity, status) " +
            " VALUES (#{labName}, #{location}, #{capacity}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "laboratoryId", keyColumn = "laboratory_id")
    int insertLaboratory(Laboratory laboratory);

    /**
     * 更新实验室信息
     * @param laboratory 实验室信息
     * @return 更新结果
     */
    @Update("UPDATE laboratory " +
            "SET lab_name = #{labName}, location = #{location}, capacity = #{capacity}, status = #{status} " +
            "WHERE lab_id = #{laboratoryId}")
    int updateLaboratory(Laboratory laboratory);

    /**
     * 删除实验室
     * @param id 实验室 id
     * @return 删除结果
     */
    @Delete("DELETE FROM laboratory WHERE lab_id = #{id}")
    int deleteLaboratory(int id);

    /**
     * 添加设备
     * @param equipment 设备信息
     * @return 添加结果
     */
    @Insert("INSERT INTO equipment (lab_id, equipment_name, status) " +
            "VALUES (#{labId}, #{equipmentName}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int insertEquipment(Equipment equipment);

    /**
     * 删除设备
     * @param id 设备 id
     * @return 删除结果
     */
    @Delete("DELETE FROM equipment WHERE equipment_id = #{id}")
    int deleteEquipment(int id);

    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 更新结果
     */
    @Update("UPDATE equipment " +
            "SET equipment_name = #{equipmentName}, lab_id = #{labId}, " +
            "equipment_name = #{equipmentName}, model = #{model}, status = #{status}")
    @Options (useGeneratedKeys = true, keyProperty = "equipmentId", keyColumn = "equipment_id")
    int updateEquipment(Equipment equipment);

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
    Equipment selectEquipmentByName(String name);
}

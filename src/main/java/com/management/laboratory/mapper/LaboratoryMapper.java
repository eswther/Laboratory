package com.management.laboratory.mapper;

import com.management.laboratory.entity.Laboratory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LaboratoryMapper {
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
     * 增加实验室
     * @param laboratory 实验室信息
     * @return 增加结果
     */
    @Insert("INSERT INTO laboratory (lab_name, location, capacity) " +
            " VALUES (#{labName}, #{location}, #{capacity})")
    @Options(useGeneratedKeys = true, keyProperty = "laboratoryId", keyColumn = "laboratory_id")
    int insertLaboratory(Laboratory laboratory);

    /**
     * 更新实验室信息
     * @param laboratory 实验室信息
     * @return 更新结果
     */
    @Update("UPDATE laboratory " +
            "SET lab_name = #{labName}, location = #{location}, capacity = #{capacity}, open_Time = #{openTime}, close_Time = #{closeTime} " +
            "WHERE lab_id = #{labId}")
    int updateLaboratory(Laboratory laboratory);

    /**
     * 删除实验室
     * @param id 实验室 id
     * @return 删除结果
     */
    @Delete("DELETE FROM laboratory WHERE lab_id = #{id}")
    int deleteLaboratory(int id);

    /**
     * 根据 id 获取实验室信息
     * @param labId 实验室 id
     * @return 实验室信息
     */
    @Select("SELECT * FROM laboratory WHERE lab_id = #{labId}")
    @Results({
            @Result(property = "labId", column = "lab_id"), // 映射 laboratory 表的 id
            @Result(property = "labName", column = "labName"), // 映射 laboratory 表的 name 字段
            @Result(property = "location", column = "location"), // 映射 laboratory 表的 location 字段
            @Result(property = "capacity", column = "capacity"), // 映射 laboratory 表的 capacity 字段
            @Result(property = "openTime", column = "openTime"), // 映射 laboratory 表的 openTime 字段
            @Result(property = "closeTime", column = "closeTime"), // 映射 laboratory 表的 closeTime" 字段
            @Result(property = "equipments", column = "lab_id", javaType = java.util.List.class,
                    many = @Many(select = "com.management.laboratory.mapper.EquipmentMapper.selectEquipmentByLabId"))
    })
    Laboratory selectLaboratoryById(int labId);

    /**
     * 获取所有实验室信息
     * @return 实验室信息列表
     */
    @Select("SELECT * FROM laboratory")
    List<Laboratory> selectAllLaboratories();
}

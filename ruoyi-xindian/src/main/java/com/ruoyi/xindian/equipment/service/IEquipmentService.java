package com.ruoyi.xindian.equipment.service;

import java.util.List;
import com.ruoyi.xindian.equipment.domain.Equipment;

/**
 * 设备管理Service接口
 * 
 * @author hanhan
 * @date 2022-08-14
 */
public interface IEquipmentService 
{
    /**
     * 查询设备管理
     * 
     * @param equipmentId 设备管理主键
     * @return 设备管理
     */
    public Equipment selectEquipmentByEquipmentId(String equipmentId);

    /**
     * 查询设备管理列表
     * 
     * @param equipment 设备管理
     * @return 设备管理集合
     */
    public List<Equipment> selectEquipmentList(Equipment equipment);

    /**
     * 新增设备管理
     * 
     * @param equipment 设备管理
     * @return 结果
     */
    public int insertEquipment(Equipment equipment);

    /**
     * 修改设备管理
     * 
     * @param equipment 设备管理
     * @return 结果
     */
    public int updateEquipment(Equipment equipment);

    /**
     * 批量删除设备管理
     * 
     * @param equipmentIds 需要删除的设备管理主键集合
     * @return 结果
     */
    public int deleteEquipmentByEquipmentIds(String[] equipmentIds);

    /**
     * 删除设备管理信息
     * 
     * @param equipmentId 设备管理主键
     * @return 结果
     */
    public int deleteEquipmentByEquipmentId(String equipmentId);
}

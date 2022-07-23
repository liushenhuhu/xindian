package com.ruoyi.xindian.equipment.mapper;

import java.util.List;
import com.ruoyi.xindian.equipment.domain.Equipment;

/**
 * 设备管理Mapper接口
 * 
 * @author Han
 * @date 2022-07-22
 */
public interface EquipmentMapper 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    public Equipment selectEquipmentById(Long id);

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
     * 删除设备管理
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteEquipmentById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentByIds(Long[] ids);
}

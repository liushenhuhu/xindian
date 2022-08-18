package com.ruoyi.xindian.equipment.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;

/**
 * 设备Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-16
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService
{
    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 查询设备
     *
     * @param equipmentId 设备主键
     * @return 设备
     */
    @Override
    public Equipment selectEquipmentByEquipmentId(String equipmentId)
    {
        return equipmentMapper.selectEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备列表
     *
     * @param equipment 设备
     * @return 设备
     */
    @Override
    public List<Equipment> selectEquipmentList(Equipment equipment)
    {
        return equipmentMapper.selectEquipmentList(equipment);
    }

    /**
     * 新增设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int insertEquipment(Equipment equipment)
    {
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 修改设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int updateEquipment(Equipment equipment)
    {
        return equipmentMapper.updateEquipment(equipment);
    }

    /**
     * 批量删除设备
     *
     * @param equipmentIds 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentIds(String[] equipmentIds)
    {
        return equipmentMapper.deleteEquipmentByEquipmentIds(equipmentIds);
    }

    /**
     * 删除设备信息
     *
     * @param equipmentId 设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentId(String equipmentId)
    {
        return equipmentMapper.deleteEquipmentByEquipmentId(equipmentId);
    }
}

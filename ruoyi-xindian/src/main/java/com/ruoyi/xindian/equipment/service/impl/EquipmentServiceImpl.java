package com.ruoyi.xindian.equipment.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;

/**
 * 设备管理Service业务层处理
 * 
 * @author Han
 * @date 2022-07-22
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService 
{
    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    @Override
    public Equipment selectEquipmentById(Long id)
    {
        return equipmentMapper.selectEquipmentById(id);
    }

    /**
     * 查询设备管理列表
     * 
     * @param equipment 设备管理
     * @return 设备管理
     */
    @Override
    public List<Equipment> selectEquipmentList(Equipment equipment)
    {
        return equipmentMapper.selectEquipmentList(equipment);
    }

    /**
     * 新增设备管理
     * 
     * @param equipment 设备管理
     * @return 结果
     */
    @Override
    public int insertEquipment(Equipment equipment)
    {
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 修改设备管理
     * 
     * @param equipment 设备管理
     * @return 结果
     */
    @Override
    public int updateEquipment(Equipment equipment)
    {
        return equipmentMapper.updateEquipment(equipment);
    }

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByIds(Long[] ids)
    {
        return equipmentMapper.deleteEquipmentByIds(ids);
    }

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentById(Long id)
    {
        return equipmentMapper.deleteEquipmentById(id);
    }
}

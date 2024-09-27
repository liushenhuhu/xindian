package com.ruoyi.xindian.equipment.mapper;

import com.ruoyi.xindian.equipment.domain.EquipmentActivate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【equipment_activate(设备归属激活)】的数据库操作Mapper
* @createDate 2024-09-26 10:05:24
* @Entity com.ruoyi.xindian.equipment.domain.EquipmentActivate
*/
public interface EquipmentActivateMapper extends BaseMapper<EquipmentActivate> {

    /**
     * 查询设备归属激活
     *
     * @param equipmentId 设备归属激活主键
     * @return 设备归属激活
     */
    public EquipmentActivate selectEquipmentActivateByEquipmentId(Long equipmentId);

    /**
     * 查询设备归属激活列表
     *
     * @param equipmentActivate 设备归属激活
     * @return 设备归属激活集合
     */
    public List<EquipmentActivate> selectEquipmentActivateList(EquipmentActivate equipmentActivate);

    /**
     * 新增设备归属激活
     *
     * @param equipmentActivate 设备归属激活
     * @return 结果
     */
    public int insertEquipmentActivate(EquipmentActivate equipmentActivate);

    /**
     * 修改设备归属激活
     *
     * @param equipmentActivate 设备归属激活
     * @return 结果
     */
    public int updateEquipmentActivate(EquipmentActivate equipmentActivate);

    /**
     * 删除设备归属激活
     *
     * @param equipmentId 设备归属激活主键
     * @return 结果
     */
    public int deleteEquipmentActivateByEquipmentId(Long equipmentId);

    /**
     * 批量删除设备归属激活
     *
     * @param equipmentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentActivateByEquipmentIds(Long[] equipmentIds);

    int updateEquipmentActivateByPhoneIsNull(EquipmentActivate equipmentActivate);
}





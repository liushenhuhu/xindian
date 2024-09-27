package com.ruoyi.xindian.equipment.service;

import com.ruoyi.xindian.equipment.domain.EquipmentActivate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【equipment_activate(设备归属激活)】的数据库操作Service
* @createDate 2024-09-26 10:05:24
*/
public interface EquipmentActivateService extends IService<EquipmentActivate> {

    /**
     * 查询设备归属激活
     *
     * @param equipmentId 设备归属激活主键
     * @return 设备归属激活
     */
    public EquipmentActivate selectEquipmentActivateByEquipmentId(Long equipmentId);
    public EquipmentActivate selectEquipmentActivateByEquipmentCode(String equipCode);

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


    public int updateEquipmentActivateByPhoneIsNull(EquipmentActivate equipmentActivate);

    /**
     * 批量删除设备归属激活
     *
     * @param equipmentIds 需要删除的设备归属激活主键集合
     * @return 结果
     */
    public int deleteEquipmentActivateByEquipmentIds(Long[] equipmentIds);

    /**
     * 删除设备归属激活信息
     *
     * @param equipmentId 设备归属激活主键
     * @return 结果
     */
    public int deleteEquipmentActivateByEquipmentId(Long equipmentId);
}

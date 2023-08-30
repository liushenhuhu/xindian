package com.ruoyi.xindian.equipment.mapper;

import java.util.List;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.patient_management.vo.Limit;

/**
 * 设备Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface EquipmentMapper
{
    /**
     * 查询设备
     *
     * @param equipmentId 设备主键
     * @return 设备
     */
    public Equipment selectEquipmentByEquipmentId(Long equipmentId);

    /**
     * 查询设备列表
     *
     * @param equipment 设备
     * @return 设备集合
     */
    public List<Equipment> selectEquipmentList(Equipment equipment);

    /**
     * 新增设备
     *
     * @param equipment 设备
     * @return 结果
     */
    public int insertEquipment(Equipment equipment);

    /**
     * 修改设备
     *
     * @param equipment 设备
     * @return 结果
     */
    public int updateEquipment(Equipment equipment);

    /**
     * 删除设备
     *
     * @param equipmentId 设备主键
     * @return 结果
     */
    public int deleteEquipmentByEquipmentId(Long equipmentId);

    /**
     * 批量删除设备
     *
     * @param equipmentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentByEquipmentIds(Long[] equipmentIds);

    void updateEquipmentStatusAll();

    void updateEquipmentStatus(String[] equipmentList);

    public Equipment selectEquipmentByEquipmentCode(String equipmentCode);


    List<Equipment> selectALl(Limit limit);
}

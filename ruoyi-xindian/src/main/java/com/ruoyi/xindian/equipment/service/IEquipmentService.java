package com.ruoyi.xindian.equipment.service;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.xindian.equipment.domain.Equipment;

/**
 * 设备Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IEquipmentService
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
     * 统计心电卡使用量
     * @param equipment
     * @return
     */
    public List<Equipment> selectEquipmentListAll(Equipment equipment);



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
     * 修改设备的状态，并且将设备的患者姓名清空
     */
    int updateStatusAndPatientPhoneNull(Equipment equipment);


    /**
     * 批量删除设备
     *
     * @param equipmentIds 需要删除的设备主键集合
     * @return 结果
     */
    public int deleteEquipmentByEquipmentIds(Long[] equipmentIds);

    /**
     * 删除设备信息
     *
     * @param equipmentId 设备主键
     * @return 结果
     */
    public int deleteEquipmentByEquipmentId(Long equipmentId);

    public void updateEquipmentStatusAll();

    public void updateEquipmentStatus(String[] statusList);

    public Equipment selectEquipmentByEquipmentCode(String equipmentCode);





    void batchInsert(String path) throws Exception;
    List<String> batchSelect(String path,Integer sum) throws Exception;

    int selectEquipmentOnlineNum(List<String> hospitalIds);

    Equipment getCodeByHospital(String equipmentCode);

    int updateEquipmentStatusByEquipmentCode(String equipmentCode);
}

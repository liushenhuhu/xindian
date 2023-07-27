package com.ruoyi.xindian.equipment.service.impl;

import java.util.List;

import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;

import javax.annotation.Resource;

/**
 * 设备Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    @Resource
    private HospitalMapper hospitalMapper;

    /**
     * 查询设备
     *
     * @param equipmentId 设备主键
     * @return 设备
     */
    @Override
    public Equipment selectEquipmentByEquipmentId(Long equipmentId) {
        return equipmentMapper.selectEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备列表
     *
     * @param equipment 设备
     * @return 设备
     */
    @Override
    public List<Equipment> selectEquipmentList(Equipment equipment) {
        List<Equipment> equipment1 = equipmentMapper.selectEquipmentList(equipment);
        if (equipment.getHospitalCode()!=null){
            String hId = equipment.getHospitalCode();
            Hospital hospital = hospitalMapper.selectHospitalByHospitalCode(hId);
            AssociatedHospital associatedHospital = new AssociatedHospital();
            associatedHospital.setHospitalId(hospital.getHospitalId());
            List<AssociatedHospital> associatedHospitals = associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
            if (associatedHospitals!=null&&associatedHospitals.size()>0){
                for (AssociatedHospital c:associatedHospitals){
                    Hospital hospital1 = hospitalMapper.selectHospitalByHospitalId(c.getLowerLevelHospitalId());
                    equipment.setHospitalCode(hospital1.getHospitalCode());
                    List<Equipment> equipment2 = equipmentMapper.selectEquipmentList(equipment);
                    equipment1.addAll(equipment2);
                }
            }
        }
        return equipment1;
    }

    /**
     * 新增设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int insertEquipment(Equipment equipment) {
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 修改设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int updateEquipment(Equipment equipment) {
        return equipmentMapper.updateEquipment(equipment);
    }

    /**
     * 批量删除设备
     *
     * @param equipmentIds 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentIds(Long[] equipmentIds) {
        return equipmentMapper.deleteEquipmentByEquipmentIds(equipmentIds);
    }

    /**
     * 删除设备信息
     *
     * @param equipmentId 设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentId(Long equipmentId) {
        return equipmentMapper.deleteEquipmentByEquipmentId(equipmentId);
    }

    @Override
    public void updateEquipmentStatusAll() {
        equipmentMapper.updateEquipmentStatusAll();
    }

    @Override
    public void updateEquipmentStatus(String[] statusList) {
        equipmentMapper.updateEquipmentStatus(statusList);
    }

    @Override
    public Equipment selectEquipmentByEquipmentCode(String equipmentCode){
        return equipmentMapper.selectEquipmentByEquipmentCode(equipmentCode);
    }
}

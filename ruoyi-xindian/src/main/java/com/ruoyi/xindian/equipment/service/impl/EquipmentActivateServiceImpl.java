package com.ruoyi.xindian.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.equipment.domain.EquipmentActivate;
import com.ruoyi.xindian.equipment.service.EquipmentActivateService;
import com.ruoyi.xindian.equipment.mapper.EquipmentActivateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author w
* @description 针对表【equipment_activate(设备归属激活)】的数据库操作Service实现
* @createDate 2024-09-26 10:05:24
*/
@Service
public class EquipmentActivateServiceImpl extends ServiceImpl<EquipmentActivateMapper, EquipmentActivate>
    implements EquipmentActivateService{

    @Resource
    private EquipmentActivateMapper equipmentActivateMapper;

    /**
     * 查询设备归属激活
     *
     * @param equipmentId 设备归属激活主键
     * @return 设备归属激活
     */
    @Override
    public EquipmentActivate selectEquipmentActivateByEquipmentId(Long equipmentId)
    {
        return equipmentActivateMapper.selectEquipmentActivateByEquipmentId(equipmentId);
    }

    @Override
    public EquipmentActivate selectEquipmentActivateByEquipmentCode(String equipCode) {
        return equipmentActivateMapper.selectOne(new LambdaQueryWrapper<EquipmentActivate>().eq(EquipmentActivate::getEquipmentCode,equipCode).last("limit 1"));
    }

    /**
     * 查询设备归属激活列表
     *
     * @param equipmentActivate 设备归属激活
     * @return 设备归属激活
     */
    @Override
    public List<EquipmentActivate> selectEquipmentActivateList(EquipmentActivate equipmentActivate)
    {
        return equipmentActivateMapper.selectEquipmentActivateList(equipmentActivate);
    }

    /**
     * 新增设备归属激活
     *
     * @param equipmentActivate 设备归属激活
     * @return 结果
     */
    @Override
    public int insertEquipmentActivate(EquipmentActivate equipmentActivate)
    {
        equipmentActivate.setCreateTime(DateUtils.getNowDate());
        return equipmentActivateMapper.insertEquipmentActivate(equipmentActivate);
    }

    /**
     * 修改设备归属激活
     *
     * @param equipmentActivate 设备归属激活
     * @return 结果
     */
    @Override
    public int updateEquipmentActivate(EquipmentActivate equipmentActivate)
    {
        equipmentActivate.setUpdateTime(DateUtils.getNowDate());
        return equipmentActivateMapper.updateEquipmentActivate(equipmentActivate);
    }

    @Override
    public int updateEquipmentActivateByPhoneIsNull(EquipmentActivate equipmentActivate) {
        return equipmentActivateMapper.updateEquipmentActivateByPhoneIsNull(equipmentActivate);
    }

    /**
     * 批量删除设备归属激活
     *
     * @param equipmentIds 需要删除的设备归属激活主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentActivateByEquipmentIds(Long[] equipmentIds)
    {
        return equipmentActivateMapper.deleteEquipmentActivateByEquipmentIds(equipmentIds);
    }

    /**
     * 删除设备归属激活信息
     *
     * @param equipmentId 设备归属激活主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentActivateByEquipmentId(Long equipmentId)
    {
        return equipmentActivateMapper.deleteEquipmentActivateByEquipmentId(equipmentId);
    }
}





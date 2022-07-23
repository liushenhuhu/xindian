package com.ruoyi.xindian.hospital_management.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital_management.mapper.HospitalManagementMapper;
import com.ruoyi.xindian.hospital_management.domain.HospitalManagement;
import com.ruoyi.xindian.hospital_management.service.IHospitalManagementService;

/**
 * 医院管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
@Service
public class HospitalManagementServiceImpl implements IHospitalManagementService 
{
    @Autowired
    private HospitalManagementMapper hospitalManagementMapper;

    /**
     * 查询医院管理
     * 
     * @param id 医院管理主键
     * @return 医院管理
     */
    @Override
    public HospitalManagement selectHospitalManagementById(Long id)
    {
        return hospitalManagementMapper.selectHospitalManagementById(id);
    }

    /**
     * 查询医院管理列表
     * 
     * @param hospitalManagement 医院管理
     * @return 医院管理
     */
    @Override
    public List<HospitalManagement> selectHospitalManagementList(HospitalManagement hospitalManagement)
    {
        return hospitalManagementMapper.selectHospitalManagementList(hospitalManagement);
    }

    /**
     * 新增医院管理
     * 
     * @param hospitalManagement 医院管理
     * @return 结果
     */
    @Override
    public int insertHospitalManagement(HospitalManagement hospitalManagement)
    {
        return hospitalManagementMapper.insertHospitalManagement(hospitalManagement);
    }

    /**
     * 修改医院管理
     * 
     * @param hospitalManagement 医院管理
     * @return 结果
     */
    @Override
    public int updateHospitalManagement(HospitalManagement hospitalManagement)
    {
        return hospitalManagementMapper.updateHospitalManagement(hospitalManagement);
    }

    /**
     * 批量删除医院管理
     * 
     * @param ids 需要删除的医院管理主键
     * @return 结果
     */
    @Override
    public int deleteHospitalManagementByIds(Long[] ids)
    {
        return hospitalManagementMapper.deleteHospitalManagementByIds(ids);
    }

    /**
     * 删除医院管理信息
     * 
     * @param id 医院管理主键
     * @return 结果
     */
    @Override
    public int deleteHospitalManagementById(Long id)
    {
        return hospitalManagementMapper.deleteHospitalManagementById(id);
    }
}

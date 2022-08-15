package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalManagementMapper;
import com.ruoyi.xindian.hospital.domain.HospitalManagement;
import com.ruoyi.xindian.hospital.service.IHospitalManagementService;

/**
 * 医院管理Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-15
 */
@Service
public class HospitalManagementServiceImpl implements IHospitalManagementService
{
    @Autowired
    private HospitalManagementMapper hospitalManagementMapper;

    /**
     * 查询医院管理
     *
     * @param hospitalId 医院管理主键
     * @return 医院管理
     */
    @Override
    public HospitalManagement selectHospitalManagementByHospitalId(String hospitalId)
    {
        return hospitalManagementMapper.selectHospitalManagementByHospitalId(hospitalId);
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
     * @param hospitalIds 需要删除的医院管理主键
     * @return 结果
     */
    @Override
    public int deleteHospitalManagementByHospitalIds(String[] hospitalIds)
    {
        return hospitalManagementMapper.deleteHospitalManagementByHospitalIds(hospitalIds);
    }

    /**
     * 删除医院管理信息
     *
     * @param hospitalId 医院管理主键
     * @return 结果
     */
    @Override
    public int deleteHospitalManagementByHospitalId(String hospitalId)
    {
        return hospitalManagementMapper.deleteHospitalManagementByHospitalId(hospitalId);
    }
}

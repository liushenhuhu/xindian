package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalManagement;

/**
 * 医院管理Service接口
 *
 * @author hanhan
 * @date 2022-08-15
 */
public interface IHospitalManagementService
{
    /**
     * 查询医院管理
     *
     * @param hospitalId 医院管理主键
     * @return 医院管理
     */
    public HospitalManagement selectHospitalManagementByHospitalId(String hospitalId);

    /**
     * 查询医院管理列表
     *
     * @param hospitalManagement 医院管理
     * @return 医院管理集合
     */
    public List<HospitalManagement> selectHospitalManagementList(HospitalManagement hospitalManagement);

    /**
     * 新增医院管理
     *
     * @param hospitalManagement 医院管理
     * @return 结果
     */
    public int insertHospitalManagement(HospitalManagement hospitalManagement);

    /**
     * 修改医院管理
     *
     * @param hospitalManagement 医院管理
     * @return 结果
     */
    public int updateHospitalManagement(HospitalManagement hospitalManagement);

    /**
     * 批量删除医院管理
     *
     * @param hospitalIds 需要删除的医院管理主键集合
     * @return 结果
     */
    public int deleteHospitalManagementByHospitalIds(String[] hospitalIds);

    /**
     * 删除医院管理信息
     *
     * @param hospitalId 医院管理主键
     * @return 结果
     */
    public int deleteHospitalManagementByHospitalId(String hospitalId);
}

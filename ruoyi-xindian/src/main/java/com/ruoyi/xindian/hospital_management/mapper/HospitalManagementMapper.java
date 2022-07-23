package com.ruoyi.xindian.hospital_management.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital_management.domain.HospitalManagement;

/**
 * 医院管理Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-23
 */
public interface HospitalManagementMapper 
{
    /**
     * 查询医院管理
     * 
     * @param id 医院管理主键
     * @return 医院管理
     */
    public HospitalManagement selectHospitalManagementById(Long id);

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
     * 删除医院管理
     * 
     * @param id 医院管理主键
     * @return 结果
     */
    public int deleteHospitalManagementById(Long id);

    /**
     * 批量删除医院管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalManagementByIds(Long[] ids);
}

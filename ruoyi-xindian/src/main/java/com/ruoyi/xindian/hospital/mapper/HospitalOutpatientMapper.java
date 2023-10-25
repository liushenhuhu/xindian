package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatient;

/**
 * 医院门诊表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface HospitalOutpatientMapper 
{
    /**
     * 查询医院门诊表
     * 
     * @param id 医院门诊表主键
     * @return 医院门诊表
     */
    public HospitalOutpatient selectHospitalOutpatientById(Long id);

    /**
     * 查询医院门诊表列表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 医院门诊表集合
     */
    public List<HospitalOutpatient> selectHospitalOutpatientList(HospitalOutpatient hospitalOutpatient);

    /**
     * 新增医院门诊表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 结果
     */
    public int insertHospitalOutpatient(HospitalOutpatient hospitalOutpatient);

    /**
     * 修改医院门诊表
     * 
     * @param hospitalOutpatient 医院门诊表
     * @return 结果
     */
    public int updateHospitalOutpatient(HospitalOutpatient hospitalOutpatient);

    /**
     * 删除医院门诊表
     * 
     * @param id 医院门诊表主键
     * @return 结果
     */
    public int deleteHospitalOutpatientById(Long id);

    /**
     * 批量删除医院门诊表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalOutpatientByIds(Long[] ids);


    HospitalOutpatient getOutNameAndSpec(HospitalOutpatient hospitalOutpatient);
}

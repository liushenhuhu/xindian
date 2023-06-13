package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalClinic;

/**
 * 诊室信息表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface HospitalClinicMapper 
{
    /**
     * 查询诊室信息表
     * 
     * @param id 诊室信息表主键
     * @return 诊室信息表
     */
    public HospitalClinic selectHospitalClinicById(Long id);

    /**
     * 查询诊室信息表列表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 诊室信息表集合
     */
    public List<HospitalClinic> selectHospitalClinicList(HospitalClinic hospitalClinic);

    /**
     * 新增诊室信息表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 结果
     */
    public int insertHospitalClinic(HospitalClinic hospitalClinic);

    /**
     * 修改诊室信息表
     * 
     * @param hospitalClinic 诊室信息表
     * @return 结果
     */
    public int updateHospitalClinic(HospitalClinic hospitalClinic);

    /**
     * 删除诊室信息表
     * 
     * @param id 诊室信息表主键
     * @return 结果
     */
    public int deleteHospitalClinicById(Long id);

    /**
     * 批量删除诊室信息表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalClinicByIds(Long[] ids);
}

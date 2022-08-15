package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.Hospital;

/**
 * 医院Mapper接口
 * 
 * @author hanhan
 * @date 2022-08-15
 */
public interface HospitalMapper 
{
    /**
     * 查询医院
     * 
     * @param hospitalId 医院主键
     * @return 医院
     */
    public Hospital selectHospitalByHospitalId(String hospitalId);

    /**
     * 查询医院列表
     * 
     * @param hospital 医院
     * @return 医院集合
     */
    public List<Hospital> selectHospitalList(Hospital hospital);

    /**
     * 新增医院
     * 
     * @param hospital 医院
     * @return 结果
     */
    public int insertHospital(Hospital hospital);

    /**
     * 修改医院
     * 
     * @param hospital 医院
     * @return 结果
     */
    public int updateHospital(Hospital hospital);

    /**
     * 删除医院
     * 
     * @param hospitalId 医院主键
     * @return 结果
     */
    public int deleteHospitalByHospitalId(String hospitalId);

    /**
     * 批量删除医院
     * 
     * @param hospitalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalByHospitalIds(String[] hospitalIds);
}

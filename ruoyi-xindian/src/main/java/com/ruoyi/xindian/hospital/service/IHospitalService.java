package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.Hospital;

/**
 * 医院Service接口
 * 
 * @author hanhan
 * @date 2022-08-15
 */
public interface IHospitalService 
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
     * 批量删除医院
     * 
     * @param hospitalIds 需要删除的医院主键集合
     * @return 结果
     */
    public int deleteHospitalByHospitalIds(String[] hospitalIds);

    /**
     * 删除医院信息
     * 
     * @param hospitalId 医院主键
     * @return 结果
     */
    public int deleteHospitalByHospitalId(String hospitalId);
}

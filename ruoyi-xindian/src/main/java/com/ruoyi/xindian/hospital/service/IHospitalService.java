package com.ruoyi.xindian.hospital.service;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.xindian.hospital.domain.Hospital;

/**
 * 医院Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IHospitalService
{
    /**
     * 查询医院
     *
     * @param hospitalId 医院主键
     * @return 医院
     */
    public Hospital selectHospitalByHospitalId(Long hospitalId);

    /**
     * 查询医院
     *
     * @param hospitalCode 医院代号
     * @return 医院
     */
    public Hospital selectHospitalByHospitalCode(String hospitalCode);

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
    public int deleteHospitalByHospitalIds(Long[] hospitalIds);

    /**
     * 删除医院信息
     *
     * @param hospitalId 医院主键
     * @return 结果
     */
    public int deleteHospitalByHospitalId(Long hospitalId);


    /**
     * 查找用户所存在的医院
     */

    List<Hospital> selectUserId(Long userId);


    Hospital selectId(Long userId);

}

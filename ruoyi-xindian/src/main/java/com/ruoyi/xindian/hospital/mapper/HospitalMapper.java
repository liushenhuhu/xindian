package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.Hospital;
import org.apache.ibatis.annotations.Param;

/**
 * 医院Mapper接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface HospitalMapper
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
     * 查询医院列表
     *
     * @param hospital 医院
     * @return 医院集合
     */
    public List<Hospital> selectHospitalCode(Hospital hospital);
    /**
     * 通过id区查询当前医院
     * @return
     */
    Hospital selectId(Hospital hospital);

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
    public int deleteHospitalByHospitalId(Long hospitalId);

    /**
     * 批量删除医院
     *
     * @param hospitalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalByHospitalIds(Long[] hospitalIds);


    Hospital selectHospitalCodeHospital(@Param("code") String code);
}

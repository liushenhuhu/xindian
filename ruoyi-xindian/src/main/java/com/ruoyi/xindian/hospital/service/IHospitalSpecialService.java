package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalSpecial;

/**
 * 医院专科表Service接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface IHospitalSpecialService 
{
    /**
     * 查询医院专科表
     * 
     * @param id 医院专科表主键
     * @return 医院专科表
     */
    public HospitalSpecial selectHospitalSpecialById(Long id);

    /**
     * 查询医院专科表列表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 医院专科表集合
     */
    public List<HospitalSpecial> selectHospitalSpecialList(HospitalSpecial hospitalSpecial);

    /**
     * 新增医院专科表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 结果
     */
    public int insertHospitalSpecial(HospitalSpecial hospitalSpecial);

    /**
     * 修改医院专科表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 结果
     */
    public int updateHospitalSpecial(HospitalSpecial hospitalSpecial);

    /**
     * 批量删除医院专科表
     * 
     * @param ids 需要删除的医院专科表主键集合
     * @return 结果
     */
    public int deleteHospitalSpecialByIds(Long[] ids);

    /**
     * 删除医院专科表信息
     * 
     * @param id 医院专科表主键
     * @return 结果
     */
    public int deleteHospitalSpecialById(Long id);


    HospitalSpecial getHospitalSpecialName(String specialName);
}

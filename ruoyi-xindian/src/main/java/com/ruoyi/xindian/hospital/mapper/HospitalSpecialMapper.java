package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.HospitalSpecial;

/**
 * 医院专科表Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface HospitalSpecialMapper 
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
     * 删除医院专科表
     * 
     * @param id 医院专科表主键
     * @return 结果
     */
    public int deleteHospitalSpecialById(Long id);

    /**
     * 批量删除医院专科表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHospitalSpecialByIds(Long[] ids);
}

package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.HospitalSpecialMapper;
import com.ruoyi.xindian.hospital.domain.HospitalSpecial;
import com.ruoyi.xindian.hospital.service.IHospitalSpecialService;

/**
 * 医院专科表Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class HospitalSpecialServiceImpl implements IHospitalSpecialService 
{
    @Autowired
    private HospitalSpecialMapper hospitalSpecialMapper;

    /**
     * 查询医院专科表
     * 
     * @param id 医院专科表主键
     * @return 医院专科表
     */
    @Override
    public HospitalSpecial selectHospitalSpecialById(Long id)
    {
        return hospitalSpecialMapper.selectHospitalSpecialById(id);
    }

    /**
     * 查询医院专科表列表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 医院专科表
     */
    @Override
    public List<HospitalSpecial> selectHospitalSpecialList(HospitalSpecial hospitalSpecial)
    {
        return hospitalSpecialMapper.selectHospitalSpecialList(hospitalSpecial);
    }

    /**
     * 新增医院专科表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 结果
     */
    @Override
    public int insertHospitalSpecial(HospitalSpecial hospitalSpecial)
    {
        return hospitalSpecialMapper.insertHospitalSpecial(hospitalSpecial);
    }

    /**
     * 修改医院专科表
     * 
     * @param hospitalSpecial 医院专科表
     * @return 结果
     */
    @Override
    public int updateHospitalSpecial(HospitalSpecial hospitalSpecial)
    {
        return hospitalSpecialMapper.updateHospitalSpecial(hospitalSpecial);
    }

    /**
     * 批量删除医院专科表
     * 
     * @param ids 需要删除的医院专科表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSpecialByIds(Long[] ids)
    {
        return hospitalSpecialMapper.deleteHospitalSpecialByIds(ids);
    }

    /**
     * 删除医院专科表信息
     * 
     * @param id 医院专科表主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSpecialById(Long id)
    {
        return hospitalSpecialMapper.deleteHospitalSpecialById(id);
    }
}

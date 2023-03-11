package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.service.IAssociatedHospitalService;

/**
 * 医院关联Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-03-10
 */
@Service
public class AssociatedHospitalServiceImpl implements IAssociatedHospitalService 
{
    @Autowired
    private AssociatedHospitalMapper associatedHospitalMapper;

    /**
     * 查询医院关联
     * 
     * @param hId 医院关联主键
     * @return 医院关联
     */
    @Override
    public AssociatedHospital selectAssociatedHospitalByHId(Long hId)
    {
        return associatedHospitalMapper.selectAssociatedHospitalByHId(hId);
    }

    /**
     * 查询医院关联列表
     * 
     * @param associatedHospital 医院关联
     * @return 医院关联
     */
    @Override
    public List<AssociatedHospital> selectAssociatedHospitalList(AssociatedHospital associatedHospital)
    {
        return associatedHospitalMapper.selectAssociatedHospitalList(associatedHospital);
    }

    /**
     * 新增医院关联
     * 
     * @param associatedHospital 医院关联
     * @return 结果
     */
    @Override
    public int insertAssociatedHospital(AssociatedHospital associatedHospital)
    {
        return associatedHospitalMapper.insertAssociatedHospital(associatedHospital);
    }

    /**
     * 修改医院关联
     * 
     * @param associatedHospital 医院关联
     * @return 结果
     */
    @Override
    public int updateAssociatedHospital(AssociatedHospital associatedHospital)
    {
        return associatedHospitalMapper.updateAssociatedHospital(associatedHospital);
    }

    /**
     * 批量删除医院关联
     * 
     * @param hIds 需要删除的医院关联主键
     * @return 结果
     */
    @Override
    public int deleteAssociatedHospitalByHIds(Long[] hIds)
    {
        return associatedHospitalMapper.deleteAssociatedHospitalByHIds(hIds);
    }

    /**
     * 删除医院关联信息
     * 
     * @param hId 医院关联主键
     * @return 结果
     */
    @Override
    public int deleteAssociatedHospitalByHId(Long hId)
    {
        return associatedHospitalMapper.deleteAssociatedHospitalByHId(hId);
    }
}

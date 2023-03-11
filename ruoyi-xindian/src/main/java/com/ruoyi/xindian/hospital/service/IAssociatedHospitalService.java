package com.ruoyi.xindian.hospital.service;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.AssociatedHospital;

/**
 * 医院关联Service接口
 * 
 * @author chenpeng
 * @date 2023-03-10
 */
public interface IAssociatedHospitalService 
{
    /**
     * 查询医院关联
     * 
     * @param hId 医院关联主键
     * @return 医院关联
     */
    public AssociatedHospital selectAssociatedHospitalByHId(Long hId);

    /**
     * 查询医院关联列表
     * 
     * @param associatedHospital 医院关联
     * @return 医院关联集合
     */
    public List<AssociatedHospital> selectAssociatedHospitalList(AssociatedHospital associatedHospital);

    /**
     * 新增医院关联
     * 
     * @param associatedHospital 医院关联
     * @return 结果
     */
    public int insertAssociatedHospital(AssociatedHospital associatedHospital);

    /**
     * 修改医院关联
     * 
     * @param associatedHospital 医院关联
     * @return 结果
     */
    public int updateAssociatedHospital(AssociatedHospital associatedHospital);

    /**
     * 批量删除医院关联
     * 
     * @param hIds 需要删除的医院关联主键集合
     * @return 结果
     */
    public int deleteAssociatedHospitalByHIds(Long[] hIds);

    /**
     * 删除医院关联信息
     * 
     * @param hId 医院关联主键
     * @return 结果
     */
    public int deleteAssociatedHospitalByHId(Long hId);
}

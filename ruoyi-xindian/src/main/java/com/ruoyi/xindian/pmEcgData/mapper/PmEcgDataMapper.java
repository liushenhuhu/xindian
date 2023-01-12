package com.ruoyi.xindian.pmEcgData.mapper;

import java.util.List;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;

/**
 * 心电数据Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-12
 */
public interface PmEcgDataMapper 
{
    /**
     * 查询心电数据
     * 
     * @param pId 心电数据主键
     * @return 心电数据
     */
    public PmEcgData selectPmEcgDataByPId(String pId);

    /**
     * 查询心电数据列表
     * 
     * @param pmEcgData 心电数据
     * @return 心电数据集合
     */
    public List<PmEcgData> selectPmEcgDataList(PmEcgData pmEcgData);

    /**
     * 新增心电数据
     * 
     * @param pmEcgData 心电数据
     * @return 结果
     */
    public int insertPmEcgData(PmEcgData pmEcgData);

    /**
     * 修改心电数据
     * 
     * @param pmEcgData 心电数据
     * @return 结果
     */
    public int updatePmEcgData(PmEcgData pmEcgData);

    /**
     * 删除心电数据
     * 
     * @param pId 心电数据主键
     * @return 结果
     */
    public int deletePmEcgDataByPId(String pId);

    /**
     * 批量删除心电数据
     * 
     * @param pIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePmEcgDataByPIds(String[] pIds);
}

package com.ruoyi.xindian.pmEcgData.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.pmEcgData.mapper.PmEcgDataMapper;
import com.ruoyi.xindian.pmEcgData.domain.PmEcgData;
import com.ruoyi.xindian.pmEcgData.service.IPmEcgDataService;

/**
 * 心电数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-12
 */
@Service
public class PmEcgDataServiceImpl implements IPmEcgDataService 
{
    @Autowired
    private PmEcgDataMapper pmEcgDataMapper;

    /**
     * 查询心电数据
     * 
     * @param pId 心电数据主键
     * @return 心电数据
     */
    @Override
    public PmEcgData selectPmEcgDataByPId(String pId)
    {
        return pmEcgDataMapper.selectPmEcgDataByPId(pId);
    }

    /**
     * 查询心电数据列表
     * 
     * @param pmEcgData 心电数据
     * @return 心电数据
     */
    @Override
    public List<PmEcgData> selectPmEcgDataList(PmEcgData pmEcgData)
    {
        return pmEcgDataMapper.selectPmEcgDataList(pmEcgData);
    }

    /**
     * 新增心电数据
     * 
     * @param pmEcgData 心电数据
     * @return 结果
     */
    @Override
    public int insertPmEcgData(PmEcgData pmEcgData)
    {
        return pmEcgDataMapper.insertPmEcgData(pmEcgData);
    }

    /**
     * 修改心电数据
     * 
     * @param pmEcgData 心电数据
     * @return 结果
     */
    @Override
    public int updatePmEcgData(PmEcgData pmEcgData)
    {
        return pmEcgDataMapper.updatePmEcgData(pmEcgData);
    }

    /**
     * 批量删除心电数据
     * 
     * @param pIds 需要删除的心电数据主键
     * @return 结果
     */
    @Override
    public int deletePmEcgDataByPIds(String[] pIds)
    {
        return pmEcgDataMapper.deletePmEcgDataByPIds(pIds);
    }

    /**
     * 删除心电数据信息
     * 
     * @param pId 心电数据主键
     * @return 结果
     */
    @Override
    public int deletePmEcgDataByPId(String pId)
    {
        return pmEcgDataMapper.deletePmEcgDataByPId(pId);
    }
}

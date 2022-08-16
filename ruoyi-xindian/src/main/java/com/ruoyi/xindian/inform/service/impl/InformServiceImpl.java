package com.ruoyi.xindian.inform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.inform.mapper.InformMapper;
import com.ruoyi.xindian.inform.domain.Inform;
import com.ruoyi.xindian.inform.service.IInformService;

/**
 * 报告Service业务层处理
 * 
 * @author hanhan
 * @date 2022-08-15
 */
@Service
public class InformServiceImpl implements IInformService 
{
    @Autowired
    private InformMapper informMapper;

    /**
     * 查询报告
     * 
     * @param informId 报告主键
     * @return 报告
     */
    @Override
    public Inform selectInformByInformId(String informId)
    {
        return informMapper.selectInformByInformId(informId);
    }

    /**
     * 查询报告列表
     * 
     * @param inform 报告
     * @return 报告
     */
    @Override
    public List<Inform> selectInformList(Inform inform)
    {
        return informMapper.selectInformList(inform);
    }

    /**
     * 新增报告
     * 
     * @param inform 报告
     * @return 结果
     */
    @Override
    public int insertInform(Inform inform)
    {
        return informMapper.insertInform(inform);
    }

    /**
     * 修改报告
     * 
     * @param inform 报告
     * @return 结果
     */
    @Override
    public int updateInform(Inform inform)
    {
        return informMapper.updateInform(inform);
    }

    /**
     * 批量删除报告
     * 
     * @param informIds 需要删除的报告主键
     * @return 结果
     */
    @Override
    public int deleteInformByInformIds(String[] informIds)
    {
        return informMapper.deleteInformByInformIds(informIds);
    }

    /**
     * 删除报告信息
     * 
     * @param informId 报告主键
     * @return 结果
     */
    @Override
    public int deleteInformByInformId(String informId)
    {
        return informMapper.deleteInformByInformId(informId);
    }
}

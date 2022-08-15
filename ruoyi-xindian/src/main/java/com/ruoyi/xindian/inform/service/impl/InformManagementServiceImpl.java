package com.ruoyi.xindian.inform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.inform.mapper.InformManagementMapper;
import com.ruoyi.xindian.inform.domain.InformManagement;
import com.ruoyi.xindian.inform.service.IInformManagementService;

/**
 * 报告管理Service业务层处理
 * 
 * @author hanhan
 * @date 2022-08-14
 */
@Service
public class InformManagementServiceImpl implements IInformManagementService 
{
    @Autowired
    private InformManagementMapper informManagementMapper;

    /**
     * 查询报告管理
     * 
     * @param informId 报告管理主键
     * @return 报告管理
     */
    @Override
    public InformManagement selectInformManagementByInformId(String informId)
    {
        return informManagementMapper.selectInformManagementByInformId(informId);
    }

    /**
     * 查询报告管理列表
     * 
     * @param informManagement 报告管理
     * @return 报告管理
     */
    @Override
    public List<InformManagement> selectInformManagementList(InformManagement informManagement)
    {
        return informManagementMapper.selectInformManagementList(informManagement);
    }

    /**
     * 新增报告管理
     * 
     * @param informManagement 报告管理
     * @return 结果
     */
    @Override
    public int insertInformManagement(InformManagement informManagement)
    {
        return informManagementMapper.insertInformManagement(informManagement);
    }

    /**
     * 修改报告管理
     * 
     * @param informManagement 报告管理
     * @return 结果
     */
    @Override
    public int updateInformManagement(InformManagement informManagement)
    {
        return informManagementMapper.updateInformManagement(informManagement);
    }

    /**
     * 批量删除报告管理
     * 
     * @param informIds 需要删除的报告管理主键
     * @return 结果
     */
    @Override
    public int deleteInformManagementByInformIds(String[] informIds)
    {
        return informManagementMapper.deleteInformManagementByInformIds(informIds);
    }

    /**
     * 删除报告管理信息
     * 
     * @param informId 报告管理主键
     * @return 结果
     */
    @Override
    public int deleteInformManagementByInformId(String informId)
    {
        return informManagementMapper.deleteInformManagementByInformId(informId);
    }
}

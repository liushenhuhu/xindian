package com.ruoyi.xindian.inform.service;

import java.util.List;
import com.ruoyi.xindian.inform.domain.InformManagement;

/**
 * 报告管理Service接口
 * 
 * @author hanhan
 * @date 2022-08-14
 */
public interface IInformManagementService 
{
    /**
     * 查询报告管理
     * 
     * @param informId 报告管理主键
     * @return 报告管理
     */
    public InformManagement selectInformManagementByInformId(String informId);

    /**
     * 查询报告管理列表
     * 
     * @param informManagement 报告管理
     * @return 报告管理集合
     */
    public List<InformManagement> selectInformManagementList(InformManagement informManagement);

    /**
     * 新增报告管理
     * 
     * @param informManagement 报告管理
     * @return 结果
     */
    public int insertInformManagement(InformManagement informManagement);

    /**
     * 修改报告管理
     * 
     * @param informManagement 报告管理
     * @return 结果
     */
    public int updateInformManagement(InformManagement informManagement);

    /**
     * 批量删除报告管理
     * 
     * @param informIds 需要删除的报告管理主键集合
     * @return 结果
     */
    public int deleteInformManagementByInformIds(String[] informIds);

    /**
     * 删除报告管理信息
     * 
     * @param informId 报告管理主键
     * @return 结果
     */
    public int deleteInformManagementByInformId(String informId);
}

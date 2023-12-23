package com.ruoyi.xindian.label.service;

import com.ruoyi.xindian.label.VO.AuditVo;
import com.ruoyi.xindian.label.domain.AlertLogAudit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【alert_log_audit】的数据库操作Service
* @createDate 2023-12-22 16:33:22
*/
public interface AlertLogAuditService extends IService<AlertLogAudit> {

    /**
     * 查询标注数据审核
     *
     * @param logId 标注数据审核主键
     * @return 标注数据审核
     */
    public AlertLogAudit selectAlertLogAuditByLogId(String logId);

    /**
     * 查询标注数据审核列表
     *
     * @param AuditVo 标注数据审核
     * @return 标注数据审核集合
     */
    public List<AuditVo> selectAlertLogAuditList(AuditVo auditVo);

    /**
     * 新增标注数据审核
     *
     * @param alertLogAudit 标注数据审核
     * @return 结果
     */
    public int insertAlertLogAudit(AlertLogAudit alertLogAudit);

    /**
     * 修改标注数据审核
     *
     * @param alertLogAudit 标注数据审核
     * @return 结果
     */
    public int updateAlertLogAudit(AlertLogAudit alertLogAudit);

    /**
     * 批量删除标注数据审核
     *
     * @param logIds 需要删除的标注数据审核主键集合
     * @return 结果
     */
    public int deleteAlertLogAuditByLogIds(String[] logIds);

    /**
     * 删除标注数据审核信息
     *
     * @param logId 标注数据审核主键
     * @return 结果
     */
    public int deleteAlertLogAuditByLogId(String logId);
}

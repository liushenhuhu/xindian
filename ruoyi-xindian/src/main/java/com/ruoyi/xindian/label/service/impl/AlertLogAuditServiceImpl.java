package com.ruoyi.xindian.label.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.label.VO.AuditVo;
import com.ruoyi.xindian.label.domain.AlertLogAudit;
import com.ruoyi.xindian.label.service.AlertLogAuditService;
import com.ruoyi.xindian.label.mapper.AlertLogAuditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【alert_log_audit】的数据库操作Service实现
* @createDate 2023-12-22 16:33:22
*/
@Service
public class AlertLogAuditServiceImpl extends ServiceImpl<AlertLogAuditMapper, AlertLogAudit>
    implements AlertLogAuditService{

    @Resource
    private AlertLogAuditMapper alertLogAuditMapper;

    /**
     * 查询标注数据审核
     *
     * @param logId 标注数据审核主键
     * @return 标注数据审核
     */
    @Override
    public AlertLogAudit selectAlertLogAuditByLogId(String logId)
    {
        return alertLogAuditMapper.selectAlertLogAuditByLogId(logId);
    }

    /**
     * 查询标注数据审核列表
     *
     * @return 标注数据审核
     */
    @Override
    public List<AuditVo> selectAlertLogAuditList(AuditVo auditVo)
    {
        return alertLogAuditMapper.selectAlertLogAuditList(auditVo);
    }

    /**
     * 新增标注数据审核
     *
     * @param alertLogAudit 标注数据审核
     * @return 结果
     */
    @Override
    public int insertAlertLogAudit(AlertLogAudit alertLogAudit)
    {
        return alertLogAuditMapper.insertAlertLogAudit(alertLogAudit);
    }

    /**
     * 修改标注数据审核
     *
     * @param alertLogAudit 标注数据审核
     * @return 结果
     */
    @Override
    public int updateAlertLogAudit(AlertLogAudit alertLogAudit)
    {
        return alertLogAuditMapper.updateAlertLogAudit(alertLogAudit);
    }

    /**
     * 批量删除标注数据审核
     *
     * @param logIds 需要删除的标注数据审核主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogAuditByLogIds(String[] logIds)
    {
        return alertLogAuditMapper.deleteAlertLogAuditByLogIds(logIds);
    }

    /**
     * 删除标注数据审核信息
     *
     * @param logId 标注数据审核主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogAuditByLogId(String logId)
    {
        return alertLogAuditMapper.deleteAlertLogAuditByLogId(logId);
    }
}





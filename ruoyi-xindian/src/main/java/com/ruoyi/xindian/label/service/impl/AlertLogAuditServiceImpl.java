package com.ruoyi.xindian.label.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.xindian.label.VO.AuditVo;
import com.ruoyi.xindian.label.VO.LogUserVO;
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
        alertLogAudit.setEventDescription(alertLogAudit.getLogType());
        alertLogAudit.setEventName(alertLogAudit.getLogType());

        int noise = getNoise(alertLogAudit.getLogNoiseLevel());
        alertLogAudit.setLogNoise(noise);

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
        alertLogAudit.setEventDescription(alertLogAudit.getLogType());
        alertLogAudit.setEventName(alertLogAudit.getLogType());

        int noise = getNoise(alertLogAudit.getLogNoiseLevel());
        alertLogAudit.setLogNoise(noise);
        return alertLogAuditMapper.updateAlertLogAudit(alertLogAudit);
    }
    public int getNoise(String level){
        if(level.length()!=12){
            throw new ServiceException("噪声参数错误");
        }
        StringBuilder noise= new StringBuilder();
        String[] split = level.split("");
        for (int i = 0; i < split.length; i++) {
            if(split[i].equals("A")){
                noise.append("0");
                continue;
            }
            noise.append("1");
        }
        return bin2Dec(noise.toString());
    }
    public  int bin2Dec(String binaryString){
        int sum = 0;
        for(int i = 0;i < binaryString.length();i++){
            char ch = binaryString.charAt(i);
            if(ch > '2' || ch < '0')
                throw new NumberFormatException(String.valueOf(i));
            sum = sum * 2 + (binaryString.charAt(i) - '0');
        }
        return sum;
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

    @Override
    public List<LogUserVO> selectLogUser(String logId) {
        return alertLogAuditMapper.selectLogUser(logId);
    }

    @Override
    public List<AlertLogAudit> selectAuditList(AuditVo auditVo) {
        return alertLogAuditMapper.selectAuditList(auditVo);
    }
}





package com.ruoyi.xindian.log_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.log_user.domain.AlertLogCount;
import com.ruoyi.xindian.log_user.service.AlertLogCountService;
import com.ruoyi.xindian.log_user.mapper.AlertLogCountMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.mapper.PatientMapper;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 13401
* @description 针对表【alert_log_count】的数据库操作Service实现
* @createDate 2024-03-28 10:23:05
*/
@Service
public class AlertLogCountServiceImpl extends ServiceImpl<AlertLogCountMapper, AlertLogCount>
    implements AlertLogCountService{

    @Resource
    private AlertLogCountMapper alertLogCountMapper;


    @Autowired
    private IPatientManagementService patientManagementService;


    @Resource
    private PatientMapper patientMapper;
    /**
     * 查询预警类型统计
     *
     * @param countId 预警类型统计主键
     * @return 预警类型统计
     */
    @Override
    public AlertLogCount selectAlertLogCountByCountId(Long countId)
    {
        return alertLogCountMapper.selectAlertLogCountByCountId(countId);
    }

    /**
     * 查询预警类型统计列表
     *
     * @param alertLogCount 预警类型统计
     * @return 预警类型统计
     */
    @Override
    public List<AlertLogCount> selectAlertLogCountList(AlertLogCount alertLogCount)
    {
        return alertLogCountMapper.selectAlertLogCountList(alertLogCount);
    }

    /**
     * 新增预警类型统计
     *
     * @param alertLogCount 预警类型统计
     * @return 结果
     */
    @Override
    public int insertAlertLogCount(AlertLogCount alertLogCount)
    {
        if (StringUtils.isEmpty(alertLogCount.getpId())){
            return 0;
        }
        PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(alertLogCount.getpId());
        if (patientManagement!=null){
            Patient patient = patientMapper.selectPatientByPatientPhone(patientManagement.getPatientPhone());
            if (patient!=null){
                alertLogCount.setGender(patient.getPatientSex());
                alertLogCount.setAge(patient.getPatientAge());
            }
        }else {
            alertLogCount.setGender("男");
            alertLogCount.setAge("2");
        }
        alertLogCount.setRecordDate(new Date());
        return alertLogCountMapper.insertAlertLogCount(alertLogCount);
    }

    /**
     * 修改预警类型统计
     *
     * @param alertLogCount 预警类型统计
     * @return 结果
     */
    @Override
    public int updateAlertLogCount(AlertLogCount alertLogCount)
    {
        return alertLogCountMapper.updateAlertLogCount(alertLogCount);
    }

    /**
     * 批量删除预警类型统计
     *
     * @param countIds 需要删除的预警类型统计主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogCountByCountIds(Long[] countIds)
    {
        return alertLogCountMapper.deleteAlertLogCountByCountIds(countIds);
    }

    /**
     * 删除预警类型统计信息
     *
     * @param countId 预警类型统计主键
     * @return 结果
     */
    @Override
    public int deleteAlertLogCountByCountId(Long countId)
    {
        return alertLogCountMapper.deleteAlertLogCountByCountId(countId);
    }

    @Override
    public List<ListValueAndLabelVO> getLogType() {
        List<ListValueAndLabelVO> logType = alertLogCountMapper.getLogType();
        DocVO docVO2 = new DocVO();
        docVO2.setValue("1");
        docVO2.setLabel("是");
        DocVO docVO3 = new DocVO();
        docVO3.setValue("0");
        docVO3.setLabel("否");
        for (ListValueAndLabelVO valueAndLabelVO : logType){
            valueAndLabelVO.getChildren().add(docVO2);
            valueAndLabelVO.getChildren().add(docVO3);
        }
        return logType;
    }
}





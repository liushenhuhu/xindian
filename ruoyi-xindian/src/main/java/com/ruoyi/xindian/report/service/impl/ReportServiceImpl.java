package com.ruoyi.xindian.report.service.impl;

import java.util.List;

import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient_management.vo.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.report.mapper.ReportMapper;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.report.service.IReportService;

/**
 * 报告Service业务层处理
 * 
 * @author hanhan
 * @date 2022-10-05
 */
@Service
public class ReportServiceImpl implements IReportService 
{
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询报告
     * 
     * @param reportId 报告主键
     * @return 报告
     */
    @Override
    public Report selectReportByReportId(Long reportId)
    {
        return reportMapper.selectReportByReportId(reportId);
    }

    @Override
    public Report selectReportByPId(String pId) {
        return reportMapper.selectReportByPId(pId);
    }

    /**
     * 查询报告列表
     * 
     * @param report 报告
     * @return 报告
     */
    @Override
    public List<Report> selectReportList(Report report)
    {
        return reportMapper.selectReportList(report);
    }


    /**
     * 新增报告
     * 
     * @param report 报告
     * @return 结果
     */
    @Override
    public int insertReport(Report report)
    {
        return reportMapper.insertReport(report);
    }

    /**
     * 修改报告
     * 
     * @param report 报告
     * @return 结果
     */
    @Override
    public int updateReport(Report report)
    {
        return reportMapper.updateReport(report);
    }

    @Override
    public int updateReportByPId(Report report) {
        return reportMapper.updateReportByPId(report);
    }

    @Override
    public int updateReportNull(Report report) {
        return reportMapper.updateReportNull(report);
    }

    /**
     * 批量删除报告
     * 
     * @param reportIds 需要删除的报告主键
     * @return 结果
     */
    @Override
    public int deleteReportByReportIds(Long[] reportIds)
    {
        return reportMapper.deleteReportByReportIds(reportIds);
    }

    /**
     * 删除报告信息
     * 
     * @param reportId 报告主键
     * @return 结果
     */
    @Override
    public int deleteReportByReportId(Long reportId)
    {
        return reportMapper.deleteReportByReportId(reportId);
    }

    @Override
    public List<Report> groupByPatientPhone(String dPhone) {
        return reportMapper.groupByPatientPhone(dPhone);
    }

    @Override
    public void reportAes(Limit limit) throws Exception {

        List<Report> reports = reportMapper.selectA(limit);
        for (Report c : reports){
            if (c.getdPhone()!=null&&!"".equals(c.getdPhone())){
                c.setdPhone(aesUtils.encrypt(c.getdPhone()));
            }
            if (c.getPPhone()!=null&&!"".equals(c.getPPhone())){
                c.setPPhone(aesUtils.encrypt(c.getPPhone()));
            }
            if (c.getDiagnosisDoctor()!=null&&!"".equals(c.getDiagnosisDoctor())){
                c.setDiagnosisDoctor(aesUtils.encrypt(c.getDiagnosisDoctor()));
            }
            reportMapper.updateReport(c);
        }
    }
}

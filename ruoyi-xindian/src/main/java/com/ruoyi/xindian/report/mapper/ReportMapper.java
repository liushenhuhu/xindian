package com.ruoyi.xindian.report.mapper;

import java.util.List;

import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.report.domain.Report;

/**
 * 报告Mapper接口
 * 
 * @author hanhan
 * @date 2022-10-05
 */
public interface ReportMapper 
{
    /**
     * 查询报告
     * 
     * @param reportId 报告主键
     * @return 报告
     */
    public Report selectReportByReportId(Long reportId);

    /**
     * 查询报告列表
     * 
     * @param report 报告
     * @return 报告集合
     */
    public List<Report> selectReportList(Report report);

    /**
     * 新增报告
     * 
     * @param report 报告
     * @return 结果
     */
    public int insertReport(Report report);

    /**
     * 修改报告
     * 
     * @param report 报告
     * @return 结果
     */
    public int updateReport(Report report);


    /**
     * 修改报告
     *
     * @param report 报告
     * @return 结果
     */
    public int updateReportByPId(Report report);
    /**
     * 修改报告
     *
     * @param report 报告
     * @return 结果
     */
    public int updateReportNull(Report report);

    /**
     * 删除报告
     * 
     * @param reportId 报告主键
     * @return 结果
     */
    public int deleteReportByReportId(Long reportId);

    /**
     * 批量删除报告
     * 
     * @param reportIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportByReportIds(Long[] reportIds);

    public Report selectReportByPId(String pId);

    public List<Report> groupByPatientPhone(String dPhone);


    List<Report> selectA(Limit limit);


}

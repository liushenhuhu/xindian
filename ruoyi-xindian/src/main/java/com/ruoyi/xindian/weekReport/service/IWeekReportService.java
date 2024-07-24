package com.ruoyi.xindian.weekReport.service;

import java.util.List;
import com.ruoyi.xindian.weekReport.domain.WeekReport;

/**
 * weekReportService接口
 * 
 * @author chenpeng
 * @date 2024-07-19
 */
public interface IWeekReportService 
{
    /**
     * 查询weekReport
     * 
     * @param id weekReport主键
     * @return weekReport
     */
    public WeekReport selectWeekReportById(Long id);

    /**
     * 查询weekReport列表
     * 
     * @param weekReport weekReport
     * @return weekReport集合
     */
    public List<WeekReport> selectWeekReportList(WeekReport weekReport);

    /**
     * 新增weekReport
     * 
     * @param weekReport weekReport
     * @return 结果
     */
    public int insertWeekReport(WeekReport weekReport);

    /**
     * 修改weekReport
     * 
     * @param weekReport weekReport
     * @return 结果
     */
    public int updateWeekReport(WeekReport weekReport);

    /**
     * 批量删除weekReport
     * 
     * @param ids 需要删除的weekReport主键集合
     * @return 结果
     */
    public int deleteWeekReportByIds(Long[] ids);

    /**
     * 删除weekReport信息
     * 
     * @param id weekReport主键
     * @return 结果
     */
    public int deleteWeekReportById(Long id);

    List<WeekReport> groupByPatientPhone(String getdPhone);

}

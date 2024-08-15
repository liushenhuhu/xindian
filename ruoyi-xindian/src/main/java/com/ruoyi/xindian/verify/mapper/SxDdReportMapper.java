package com.ruoyi.xindian.verify.mapper;

import com.ruoyi.xindian.verify.domain.SxDdReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【sx_dd_report】的数据库操作Mapper
* @createDate 2024-07-25 12:05:06
* @Entity com.ruoyi.xindian.verify.domain.SxDdReport
*/
public interface SxDdReportMapper extends BaseMapper<SxDdReport> {

    /**
     * 查询善行单导心电贴
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 善行单导心电贴
     */
    public SxDdReport selectSxDdReportBySxReportId(Long sxReportId);

    /**
     * 查询善行单导心电贴列表
     *
     * @param sxDdReport 善行单导心电贴
     * @return 善行单导心电贴集合
     */
    public List<SxDdReport> selectSxDdReportList(SxDdReport sxDdReport);

    /**
     * 新增善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    public int insertSxDdReport(SxDdReport sxDdReport);

    /**
     * 修改善行单导心电贴
     *
     * @param sxDdReport 善行单导心电贴
     * @return 结果
     */
    public int updateSxDdReport(SxDdReport sxDdReport);

    /**
     * 删除善行单导心电贴
     *
     * @param sxReportId 善行单导心电贴主键
     * @return 结果
     */
    public int deleteSxDdReportBySxReportId(Long sxReportId);

    /**
     * 批量删除善行单导心电贴
     *
     * @param sxReportIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSxDdReportBySxReportIds(Long[] sxReportIds);
}





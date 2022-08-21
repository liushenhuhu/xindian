package com.ruoyi.xindian.inform.service;

import java.util.List;
import com.ruoyi.xindian.inform.domain.Inform;

/**
 * 报告Service接口
 *
 * @author hanhan
 * @date 2022-08-20
 */
public interface IInformService
{
    /**
     * 查询报告
     *
     * @param informId 报告主键
     * @return 报告
     */
    public Inform selectInformByInformId(Long informId);

    /**
     * 查询报告列表
     *
     * @param inform 报告
     * @return 报告集合
     */
    public List<Inform> selectInformList(Inform inform);

    /**
     * 新增报告
     *
     * @param inform 报告
     * @return 结果
     */
    public int insertInform(Inform inform);

    /**
     * 修改报告
     *
     * @param inform 报告
     * @return 结果
     */
    public int updateInform(Inform inform);

    /**
     * 批量删除报告
     *
     * @param informIds 需要删除的报告主键集合
     * @return 结果
     */
    public int deleteInformByInformIds(Long[] informIds);

    /**
     * 删除报告信息
     *
     * @param informId 报告主键
     * @return 结果
     */
    public int deleteInformByInformId(Long informId);
}

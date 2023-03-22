package com.ruoyi.xindian.report.service;


import java.util.List;
import com.ruoyi.xindian.report.domain.NotDealWith;

/**
 * 医生处理表Service接口
 *
 * @author chenpeng
 * @date 2023-03-22
 */
public interface INotDealWithService
{
    /**
     * 查询医生处理表
     *
     * @param id 医生处理表主键
     * @return 医生处理表
     */
    public NotDealWith selectNotDealWithById(Long id);

    /**
     * 查询医生处理表列表
     *
     * @param notDealWith 医生处理表
     * @return 医生处理表集合
     */
    public List<NotDealWith> selectNotDealWithList(NotDealWith notDealWith);

    /**
     * 新增医生处理表
     *
     * @param notDealWith 医生处理表
     * @return 结果
     */
    public int insertNotDealWith(NotDealWith notDealWith);

    /**
     * 修改医生处理表
     *
     * @param notDealWith 医生处理表
     * @return 结果
     */
    public int updateNotDealWith(NotDealWith notDealWith);

    /**
     * 批量删除医生处理表
     *
     * @param ids 需要删除的医生处理表主键集合
     * @return 结果
     */
    public int deleteNotDealWithByIds(Long[] ids);

    /**
     * 删除医生处理表信息
     *
     * @param id 医生处理表主键
     * @return 结果
     */
    public int deleteNotDealWithById(Long id);
}


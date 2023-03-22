package com.ruoyi.xindian.report.mapper;

import java.util.List;
import com.ruoyi.xindian.report.domain.NotDealWith;

/**
 * 医生处理表Mapper接口
 *
 * @author chenpeng
 * @date 2023-03-22
 */
public interface NotDealWithMapper
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
     * 删除医生处理表
     *
     * @param id 医生处理表主键
     * @return 结果
     */
    public int deleteNotDealWithById(Long id);

    /**
     * 批量删除医生处理表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNotDealWithByIds(Long[] ids);
}


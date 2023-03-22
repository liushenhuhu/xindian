package com.ruoyi.xindian.report.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.report.mapper.NotDealWithMapper;
import com.ruoyi.xindian.report.domain.NotDealWith;
import com.ruoyi.xindian.report.service.INotDealWithService;

/**
 * 医生处理表Service业务层处理
 *
 * @author chenpeng
 * @date 2023-03-22
 */
@Service
public class NotDealWithServiceImpl implements INotDealWithService
{
    @Autowired
    private NotDealWithMapper notDealWithMapper;

    /**
     * 查询医生处理表
     *
     * @param id 医生处理表主键
     * @return 医生处理表
     */
    @Override
    public NotDealWith selectNotDealWithById(Long id)
    {
        return notDealWithMapper.selectNotDealWithById(id);
    }

    /**
     * 查询医生处理表列表
     *
     * @param notDealWith 医生处理表
     * @return 医生处理表
     */
    @Override
    public List<NotDealWith> selectNotDealWithList(NotDealWith notDealWith)
    {
        return notDealWithMapper.selectNotDealWithList(notDealWith);
    }

    /**
     * 新增医生处理表
     *
     * @param notDealWith 医生处理表
     * @return 结果
     */
    @Override
    public int insertNotDealWith(NotDealWith notDealWith)
    {
        return notDealWithMapper.insertNotDealWith(notDealWith);
    }

    /**
     * 修改医生处理表
     *
     * @param notDealWith 医生处理表
     * @return 结果
     */
    @Override
    public int updateNotDealWith(NotDealWith notDealWith)
    {
        return notDealWithMapper.updateNotDealWith(notDealWith);
    }

    /**
     * 批量删除医生处理表
     *
     * @param ids 需要删除的医生处理表主键
     * @return 结果
     */
    @Override
    public int deleteNotDealWithByIds(Long[] ids)
    {
        return notDealWithMapper.deleteNotDealWithByIds(ids);
    }

    /**
     * 删除医生处理表信息
     *
     * @param id 医生处理表主键
     * @return 结果
     */
    @Override
    public int deleteNotDealWithById(Long id)
    {
        return notDealWithMapper.deleteNotDealWithById(id);
    }
}


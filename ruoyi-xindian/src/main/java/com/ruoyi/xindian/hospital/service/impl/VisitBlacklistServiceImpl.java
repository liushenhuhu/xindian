package com.ruoyi.xindian.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.hospital.mapper.VisitBlacklistMapper;
import com.ruoyi.xindian.hospital.domain.VisitBlacklist;
import com.ruoyi.xindian.hospital.service.IVisitBlacklistService;

/**
 * 出诊黑名单Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@Service
public class VisitBlacklistServiceImpl implements IVisitBlacklistService 
{
    @Autowired
    private VisitBlacklistMapper visitBlacklistMapper;

    /**
     * 查询出诊黑名单
     * 
     * @param id 出诊黑名单主键
     * @return 出诊黑名单
     */
    @Override
    public VisitBlacklist selectVisitBlacklistById(Long id)
    {
        return visitBlacklistMapper.selectVisitBlacklistById(id);
    }

    /**
     * 查询出诊黑名单列表
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 出诊黑名单
     */
    @Override
    public List<VisitBlacklist> selectVisitBlacklistList(VisitBlacklist visitBlacklist)
    {
        return visitBlacklistMapper.selectVisitBlacklistList(visitBlacklist);
    }

    /**
     * 新增出诊黑名单
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 结果
     */
    @Override
    public int insertVisitBlacklist(VisitBlacklist visitBlacklist)
    {
        return visitBlacklistMapper.insertVisitBlacklist(visitBlacklist);
    }

    /**
     * 修改出诊黑名单
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 结果
     */
    @Override
    public int updateVisitBlacklist(VisitBlacklist visitBlacklist)
    {
        return visitBlacklistMapper.updateVisitBlacklist(visitBlacklist);
    }

    /**
     * 批量删除出诊黑名单
     * 
     * @param ids 需要删除的出诊黑名单主键
     * @return 结果
     */
    @Override
    public int deleteVisitBlacklistByIds(Long[] ids)
    {
        return visitBlacklistMapper.deleteVisitBlacklistByIds(ids);
    }

    /**
     * 删除出诊黑名单信息
     * 
     * @param id 出诊黑名单主键
     * @return 结果
     */
    @Override
    public int deleteVisitBlacklistById(Long id)
    {
        return visitBlacklistMapper.deleteVisitBlacklistById(id);
    }
}

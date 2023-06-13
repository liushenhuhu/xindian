package com.ruoyi.xindian.hospital.mapper;

import java.util.List;
import com.ruoyi.xindian.hospital.domain.VisitBlacklist;

/**
 * 出诊黑名单Mapper接口
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public interface VisitBlacklistMapper 
{
    /**
     * 查询出诊黑名单
     * 
     * @param id 出诊黑名单主键
     * @return 出诊黑名单
     */
    public VisitBlacklist selectVisitBlacklistById(Long id);

    /**
     * 查询出诊黑名单列表
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 出诊黑名单集合
     */
    public List<VisitBlacklist> selectVisitBlacklistList(VisitBlacklist visitBlacklist);

    /**
     * 新增出诊黑名单
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 结果
     */
    public int insertVisitBlacklist(VisitBlacklist visitBlacklist);

    /**
     * 修改出诊黑名单
     * 
     * @param visitBlacklist 出诊黑名单
     * @return 结果
     */
    public int updateVisitBlacklist(VisitBlacklist visitBlacklist);

    /**
     * 删除出诊黑名单
     * 
     * @param id 出诊黑名单主键
     * @return 结果
     */
    public int deleteVisitBlacklistById(Long id);

    /**
     * 批量删除出诊黑名单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitBlacklistByIds(Long[] ids);
}

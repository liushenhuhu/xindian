package com.ruoyi.xindian.ano.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.xindian.ano.domain.AnoUser;

/**
 * anoService接口
 * 
 * @author hanhan
 * @date 2022-12-30
 */
public interface IAnoUserService 
{
    /**
     * 查询ano
     * 
     * @param anoUserId ano主键
     * @return ano
     */
    public AnoUser selectAnoUserByAnoUserId(Long anoUserId);

    /**
     * 查询ano列表
     * 
     * @param anoUser ano
     * @return ano集合
     */
    public List<AnoUser> selectAnoUserList(AnoUser anoUser);

    /**
     * 新增ano
     * 
     * @param anoUser ano
     * @return 结果
     */
    public int insertAnoUser(AnoUser anoUser);

    /**
     * 修改ano
     * 
     * @param anoUser ano
     * @return 结果
     */
    public int updateAnoUser(AnoUser anoUser);

    /**
     * 批量删除ano
     * 
     * @param anoUserIds 需要删除的ano主键集合
     * @return 结果
     */
    public int deleteAnoUserByAnoUserIds(Long[] anoUserIds);

    /**
     * 删除ano信息
     * 
     * @param anoUserId ano主键
     * @return 结果
     */
    public int deleteAnoUserByAnoUserId(Long anoUserId);

    public int insertAno(String userId, String pId);

    List<AnoUser> selectAnoUserList2(SysUser anoUser);

    List<AnoUser> selectNotAssign(AnoUser anoUser);

    int deleteAnoUserByAnoUser(AnoUser anoUser);

    int selectCount(String s);
}

package com.ruoyi.xindian.ano.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.ano.mapper.AnoUserMapper;
import com.ruoyi.xindian.ano.domain.AnoUser;
import com.ruoyi.xindian.ano.service.IAnoUserService;

/**
 * anoService业务层处理
 *
 * @author hanhan
 * @date 2022-12-30
 */
@Service
public class AnoUserServiceImpl implements IAnoUserService {
    @Autowired
    private AnoUserMapper anoUserMapper;

    /**
     * 查询ano
     *
     * @param anoUserId ano主键
     * @return ano
     */
    @Override
    public AnoUser selectAnoUserByAnoUserId(Long anoUserId) {
        return anoUserMapper.selectAnoUserByAnoUserId(anoUserId);
    }

    /**
     * 查询ano列表
     *
     * @param anoUser ano
     * @return ano
     */
    @Override
    public List<AnoUser> selectAnoUserList(AnoUser anoUser) {
        return anoUserMapper.selectAnoUserList(anoUser);
    }

    /**
     * 新增ano
     *
     * @param anoUser ano
     * @return 结果
     */
    @Override
    public int insertAnoUser(AnoUser anoUser) {
        return anoUserMapper.insertAnoUser(anoUser);
    }

    /**
     * 修改ano
     *
     * @param anoUser ano
     * @return 结果
     */
    @Override
    public int updateAnoUser(AnoUser anoUser) {
        return anoUserMapper.updateAnoUser(anoUser);
    }

    /**
     * 批量删除ano
     *
     * @param anoUserIds 需要删除的ano主键
     * @return 结果
     */
    @Override
    public int deleteAnoUserByAnoUserIds(Long[] anoUserIds) {
        return anoUserMapper.deleteAnoUserByAnoUserIds(anoUserIds);
    }

    /**
     * 删除ano信息
     *
     * @param anoUserId ano主键
     * @return 结果
     */
    @Override
    public int deleteAnoUserByAnoUserId(Long anoUserId) {
        return anoUserMapper.deleteAnoUserByAnoUserId(anoUserId);
    }

    @Override
    public int insertAno(String userId, String pId) {
        return anoUserMapper.insertAno(userId, pId);
    }

    @Override
    public List<AnoUser> selectAnoUserList2(SysUser anoUser) {
       return anoUserMapper.selectAnoUserList2(anoUser);
    }

    @Override
    public List<AnoUser> selectNotAssign(AnoUser anoUser) {
        return anoUserMapper.selectNotAssign(anoUser);
    }

    @Override
    public int deleteAnoUserByAnoUser(AnoUser anoUser) {
        return anoUserMapper.deleteAnoUserByAnoUser(anoUser);
    }

    @Override
    public int selectCount(String s) {
        return anoUserMapper.selectCount(s);
    }
}

package com.ruoyi.xindian.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.mapper.ProtocolMapper;
import com.ruoyi.xindian.domain.Protocol;
import com.ruoyi.xindian.service.IProtocolService;

/**
 * 协议Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
@Service
public class ProtocolServiceImpl implements IProtocolService 
{
    @Autowired
    private ProtocolMapper protocolMapper;

    /**
     * 查询协议
     * 
     * @param protocolId 协议主键
     * @return 协议
     */
    @Override
    public Protocol selectProtocolByProtocolId(Long protocolId)
    {
        return protocolMapper.selectProtocolByProtocolId(protocolId);
    }

    /**
     * 查询协议列表
     * 
     * @param protocol 协议
     * @return 协议
     */
    @Override
    public List<Protocol> selectProtocolList(Protocol protocol)
    {
        return protocolMapper.selectProtocolList(protocol);
    }

    /**
     * 新增协议
     * 
     * @param protocol 协议
     * @return 结果
     */
    @Override
    public int insertProtocol(Protocol protocol)
    {
        return protocolMapper.insertProtocol(protocol);
    }

    /**
     * 修改协议
     * 
     * @param protocol 协议
     * @return 结果
     */
    @Override
    public int updateProtocol(Protocol protocol)
    {
        return protocolMapper.updateProtocol(protocol);
    }

    /**
     * 批量删除协议
     * 
     * @param protocolIds 需要删除的协议主键
     * @return 结果
     */
    @Override
    public int deleteProtocolByProtocolIds(Long[] protocolIds)
    {
        return protocolMapper.deleteProtocolByProtocolIds(protocolIds);
    }

    /**
     * 删除协议信息
     * 
     * @param protocolId 协议主键
     * @return 结果
     */
    @Override
    public int deleteProtocolByProtocolId(Long protocolId)
    {
        return protocolMapper.deleteProtocolByProtocolId(protocolId);
    }
}

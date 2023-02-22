package com.ruoyi.xindian.mapper;

import java.util.List;
import com.ruoyi.xindian.domain.Protocol;

/**
 * 协议Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
public interface ProtocolMapper 
{
    /**
     * 查询协议
     * 
     * @param protocolId 协议主键
     * @return 协议
     */
    public Protocol selectProtocolByProtocolId(Long protocolId);

    /**
     * 查询协议列表
     * 
     * @param protocol 协议
     * @return 协议集合
     */
    public List<Protocol> selectProtocolList(Protocol protocol);

    /**
     * 新增协议
     * 
     * @param protocol 协议
     * @return 结果
     */
    public int insertProtocol(Protocol protocol);

    /**
     * 修改协议
     * 
     * @param protocol 协议
     * @return 结果
     */
    public int updateProtocol(Protocol protocol);

    /**
     * 删除协议
     * 
     * @param protocolId 协议主键
     * @return 结果
     */
    public int deleteProtocolByProtocolId(Long protocolId);

    /**
     * 批量删除协议
     * 
     * @param protocolIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProtocolByProtocolIds(Long[] protocolIds);
}

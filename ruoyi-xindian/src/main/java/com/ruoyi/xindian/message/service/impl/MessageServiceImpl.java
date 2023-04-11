package com.ruoyi.xindian.message.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.message.mapper.MessageMapper;
import com.ruoyi.xindian.message.domain.Message;
import com.ruoyi.xindian.message.service.IMessageService;

/**
 * 消息存储Service业务层处理
 * 
 * @author chenpeng
 * @date 2023-04-11
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询消息存储
     * 
     * @param id 消息存储主键
     * @return 消息存储
     */
    @Override
    public Message selectMessageById(Long id)
    {
        return messageMapper.selectMessageById(id);
    }

    /**
     * 查询消息存储列表
     * 
     * @param message 消息存储
     * @return 消息存储
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 新增消息存储
     * 
     * @param message 消息存储
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改消息存储
     * 
     * @param message 消息存储
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        return messageMapper.updateMessage(message);
    }

    /**
     * 批量删除消息存储
     * 
     * @param ids 需要删除的消息存储主键
     * @return 结果
     */
    @Override
    public int deleteMessageByIds(Long[] ids)
    {
        return messageMapper.deleteMessageByIds(ids);
    }

    /**
     * 删除消息存储信息
     * 
     * @param id 消息存储主键
     * @return 结果
     */
    @Override
    public int deleteMessageById(Long id)
    {
        return messageMapper.deleteMessageById(id);
    }
}

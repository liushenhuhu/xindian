package com.ruoyi.xindian.message.mapper;

import java.util.List;
import com.ruoyi.xindian.message.domain.Message;

/**
 * 消息存储Mapper接口
 * 
 * @author chenpeng
 * @date 2023-04-11
 */
public interface MessageMapper 
{
    /**
     * 查询消息存储
     * 
     * @param id 消息存储主键
     * @return 消息存储
     */
    public Message selectMessageById(Long id);

    /**
     * 查询消息存储列表
     * 
     * @param message 消息存储
     * @return 消息存储集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 新增消息存储
     * 
     * @param message 消息存储
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改消息存储
     * 
     * @param message 消息存储
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 删除消息存储
     * 
     * @param id 消息存储主键
     * @return 结果
     */
    public int deleteMessageById(Long id);

    /**
     * 批量删除消息存储
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageByIds(Long[] ids);
}

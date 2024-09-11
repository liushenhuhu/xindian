package com.ruoyi.xindian.webSocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.ruoyi.xindian.webSocket.service.ChattingRecordService;
import com.ruoyi.xindian.webSocket.mapper.ChattingRecordMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @author w
* @description 针对表【chatting_record(聊天消息表)】的数据库操作Service实现
* @createDate 2024-09-06 14:41:11
*/
@Service
public class ChattingRecordServiceImpl extends ServiceImpl<ChattingRecordMapper, ChattingRecord>
    implements ChattingRecordService{



    @Resource
    private ChattingRecordMapper chattingRecordMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public int insertChattingRecord(ChattingRecord chattingRecord) {
        String key = "unread_count:" + chattingRecord.getReceiverId()+":" + chattingRecord.getSenderId();
        redisTemplate.opsForValue().increment(key);
        setLastMessage(chattingRecord.getSenderId(), chattingRecord.getReceiverId(), chattingRecord.getContent());
        addFriend(chattingRecord.getSenderId(), chattingRecord.getReceiverId());
        return chattingRecordMapper.insert(chattingRecord);
    }


    public void setLastMessage(String senderId, String receiverId, String text) {
        // 共享 key 对于发送者和接收者
        String key = "last_message:" + Math.min(senderId.hashCode(), receiverId.hashCode()) + ":" + Math.max(senderId.hashCode(), receiverId.hashCode());
        redisTemplate.opsForHash().put(key, "last_message", text);
    }


    public void addFriend(String userId, String friendId) {
        String key = "user_friends:" + userId;
        redisTemplate.opsForSet().add(key, friendId);
    }

    @Override
    public int putChattingStatus(ChattingRecord chattingRecord) {
        String key = "unread_count:" + chattingRecord.getReceiverId()+":"+chattingRecord.getSenderId();
        redisTemplate.delete(key);
        chattingRecord.setUpdateTime(new Date());
        return chattingRecordMapper.update(chattingRecord,new LambdaQueryWrapper<ChattingRecord>()
                .eq(ChattingRecord::getSenderId,chattingRecord.getSenderId())
                .eq(ChattingRecord::getReceiverId,chattingRecord.getReceiverId()));
    }

    @Override
    public List<ChattingRecord> getChattingList(ChattingRecord chattingRecord) {
        return chattingRecordMapper.selectChattingRecordList(chattingRecord);
    }


    @Override
    public List<Map<String, Object>> getChattingByUserList(ChattingRecord chattingRecord) {

        List<Map<String, Object>> list = new ArrayList<>();
        //获取所有的好友列表
        Set<Object> friends = getFriends(chattingRecord.getSenderId());

        for (Object friend : friends) {
            if (friend==null){
                continue;
            }
            Map<String,Object> unreadCount = new HashMap<>();
            // 遍历好友列表，获取每个好友的未读消息数量
            Integer unreadCount1 = getUnreadCount(chattingRecord.getSenderId(), friend.toString());
            // 获取最后一条数据
            String lastMessage = getLastMessage(chattingRecord.getSenderId(), friend.toString());
            //好友id
            unreadCount.put("tid",friend.toString());
            //未读消息数量
            unreadCount.put("unreadCount",unreadCount1);
            //最后一条消息
            unreadCount.put("lastMessage",lastMessage);
            list.add(unreadCount);
        }
        return list;
    }

    /**
     * 获取未读消息数量
     * @param userId
     * @return
     */
    public Integer getUnreadCount(String userId, String friendId) {
        String key = "unread_count:" + userId+":"+friendId;
        Object count =  redisTemplate.opsForValue().get(key);
        return count != null ? Integer.parseInt(count.toString()) : 0;
    }


    /**
     * 获取最后一条消息
     * @return
     */
    public String getLastMessage(String userId1, String userId2) {
        // 共享 key 对于发送者和接收者
        String key = "last_message:" + Math.min(userId1.hashCode(), userId2.hashCode()) + ":" + Math.max(userId1.hashCode(), userId2.hashCode());
        Object lastMessage = redisTemplate.opsForHash().get(key, "last_message");
        return lastMessage != null ? lastMessage.toString() : null;
    }
    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    public Set<Object> getFriends(String userId) {
        String key = "user_friends:" + userId;
        return redisTemplate.opsForSet().members(key);
    }

    /**
     *  删除好友
     * @param userId
     * @param friendId
     */
    public void removeFriend(String userId, String friendId) {
        String key = "user_friends:" + userId;
        redisTemplate.opsForSet().remove(key, friendId);
    }
}





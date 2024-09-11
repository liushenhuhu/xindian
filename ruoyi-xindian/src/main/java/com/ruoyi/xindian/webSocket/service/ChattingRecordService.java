package com.ruoyi.xindian.webSocket.service;

import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author w
* @description 针对表【chatting_record(聊天消息表)】的数据库操作Service
* @createDate 2024-09-06 14:41:11
*/
public interface ChattingRecordService extends IService<ChattingRecord> {



    public int insertChattingRecord(ChattingRecord chattingRecord);

    int putChattingStatus(ChattingRecord chattingRecord);

    List<ChattingRecord> getChattingList(ChattingRecord chattingRecord);

    List<Map<String, Object>> getChattingByUserList(ChattingRecord chattingRecord);
}

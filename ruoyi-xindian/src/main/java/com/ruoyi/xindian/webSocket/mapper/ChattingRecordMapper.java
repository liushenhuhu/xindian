package com.ruoyi.xindian.webSocket.mapper;

import com.ruoyi.xindian.webSocket.domain.ChattingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【chatting_record(聊天消息表)】的数据库操作Mapper
* @createDate 2024-09-06 14:41:11
* @Entity com.ruoyi.xindian.webSocket.domain.ChattingRecord
*/
public interface ChattingRecordMapper extends BaseMapper<ChattingRecord> {



    List<ChattingRecord> selectChattingRecordList(ChattingRecord chattingRecord);

}





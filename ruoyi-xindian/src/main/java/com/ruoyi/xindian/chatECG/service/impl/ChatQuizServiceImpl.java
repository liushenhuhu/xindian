package com.ruoyi.xindian.chatECG.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.chatECG.domain.Chat;
import com.ruoyi.xindian.chatECG.domain.ChatQuiz;
import com.ruoyi.xindian.chatECG.service.ChatQuizService;
import com.ruoyi.xindian.chatECG.mapper.ChatQuizMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13401
* @description 针对表【chat_quiz】的数据库操作Service实现
* @createDate 2023-10-13 09:10:14
*/
@Service
public class ChatQuizServiceImpl extends ServiceImpl<ChatQuizMapper, ChatQuiz>
    implements ChatQuizService{


    @Resource
    private ChatQuizMapper chatQuizMapper;

    @Override
    public int insertChatQuiz(ChatQuiz chatQuiz) {
        return chatQuizMapper.insert(chatQuiz);
    }

    @Override
    public List<ChatQuiz> getChatQuizList(ChatQuiz chatQuiz) {
        return chatQuizMapper.selectList(new QueryWrapper<ChatQuiz>().eq("conversation_id",chatQuiz.getConversationId()).eq("user_id",chatQuiz.getUserId()).orderByAsc("create_time"));
    }

    @Override
    public List<ChatQuiz> getChatQuizListIsNotTet(ChatQuiz chatQuiz) {
        return chatQuizMapper.selectList(new QueryWrapper<ChatQuiz>().eq("conversation_id",chatQuiz.getConversationId()).eq("user_id",chatQuiz.getUserId()).isNotNull("title"));
    }

    @Override
    public List<ChatQuiz> getConversation(Long userId) {
        return chatQuizMapper.selectList(new QueryWrapper<ChatQuiz>().eq("user_id",userId).isNotNull("title").orderByDesc("create_time"));
    }

    @Override
    public int deleteConversation(ChatQuiz chatQuiz) {
        return chatQuizMapper.delete(new QueryWrapper<ChatQuiz>().eq("conversation_id",chatQuiz.getConversationId()).eq("user_id",chatQuiz.getUserId()));
    }

    @Override
    public int updateConversation(ChatQuiz chatQuiz) {
        return chatQuizMapper.update(chatQuiz,new QueryWrapper<ChatQuiz>().eq("conversation_id",chatQuiz.getConversationId()).eq("user_id",chatQuiz.getUserId()).isNotNull("title"));
    }
}





package com.ruoyi.xindian.chatECG.service;

import com.ruoyi.xindian.chatECG.domain.Chat;
import com.ruoyi.xindian.chatECG.domain.ChatQuiz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13401
* @description 针对表【chat_quiz】的数据库操作Service
* @createDate 2023-10-13 09:10:14
*/
public interface ChatQuizService extends IService<ChatQuiz> {


    /**
     * 添加智能医生提问的问题以及回答
     * @param chatQuiz
     * @return
     */
    int insertChatQuiz(ChatQuiz chatQuiz);


    /**
     * 获取指定窗口的所有问题
     * @param chatQuiz
     * @return
     */
    List<ChatQuiz> getChatQuizList(ChatQuiz chatQuiz);


    /**
     * 获取用户存在的窗口信息
     * @param userId
     * @return
     */
    List<ChatQuiz> getConversation(Long userId);


    /**
     * 删除窗口
     * @return
     */
    int deleteConversation(ChatQuiz chatQuiz);


    /**
     * 修改标题
     * @param chatQuiz
     * @return
     */
    int updateConversation(ChatQuiz chatQuiz);

}

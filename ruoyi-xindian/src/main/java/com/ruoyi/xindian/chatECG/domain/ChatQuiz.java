package com.ruoyi.xindian.chatECG.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName chat_quiz
 */
@TableName(value ="chat_quiz")
@Data
public class ChatQuiz implements Serializable {
    /**
     * 心电机器人问题Id
     */
    @TableId(type = IdType.AUTO)
    private Integer chatId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 会话id
     */
    private Integer conversationId;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息类型（text,image,system,file）
     */
    private String messageType;


    /**
     * 数据响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date responseTime;


    /**
     * 窗口标题
     */
    private String title;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
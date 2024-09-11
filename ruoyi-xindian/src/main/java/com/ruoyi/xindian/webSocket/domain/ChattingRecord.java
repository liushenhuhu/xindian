package com.ruoyi.xindian.webSocket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 聊天消息表
 * @TableName chatting_record
 */
@TableName(value ="chatting_record")
@Data
public class ChattingRecord implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long chattingId;

    /**
     * 发送人(手机号)
     */
    private String senderId;

    /**
     * 接收人（手机号）
     */
    private String receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 是否已读   1  未读    0  已读
     */
    private String isRead;

    /**
     * 是否删除(0未删除，1已删除)
     */
    private String delFlag;


    /**
     * 用户类型（用来判断是否医生发的消息，还是用户发的）
     */
    private String userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
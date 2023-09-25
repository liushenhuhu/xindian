package com.ruoyi.xindian.alert_log.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName alert_log_json_data
 */
@TableName(value ="alert_log_json_data")
@Data
public class AlertLogJsonData implements Serializable {
    /**
     * 
     */
    @TableId(type=IdType.AUTO)
    private Integer alertLogId;

    /**
     * 日志id
     */
    private String logId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * json标注数据
     */
    private String jsonData;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
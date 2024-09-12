package com.ruoyi.system.domain.vo;

import lombok.Data;

import javax.annotation.Resource;
import java.util.Date;

@Data
public class SymCountVo {

    private Long userId;

    private Date createTime;

    private String ecgType;
}

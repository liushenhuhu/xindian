package com.ruoyi.xindian.wx_public.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class WxPublic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer wxId;

    private String openId;

    private String unionId;


}

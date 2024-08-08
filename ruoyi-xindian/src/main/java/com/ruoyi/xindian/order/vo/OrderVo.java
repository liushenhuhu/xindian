package com.ruoyi.xindian.order.vo;

import lombok.Data;

@Data
public class OrderVo {

    private Long productId;

    private String orderId;

    private Integer sum;

    private String equipmentCode;

    private String pId;
}

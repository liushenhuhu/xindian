package com.ruoyi.xindian.order.vo;

import lombok.Data;

@Data
public class OrderVo {

    private Long productId;

    private String orderId;

    private Integer sum;

    private String equipmentCode;

    private String pId;

    private Long addressId;

    private String remark;

    private String discountsCode;
}

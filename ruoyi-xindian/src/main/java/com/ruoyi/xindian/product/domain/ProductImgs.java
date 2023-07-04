package com.ruoyi.xindian.product.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class ProductImgs extends BaseEntity {

    private Long id;

    private Long productId;

    private String productImags;
}

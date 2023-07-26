package com.ruoyi.xindian.product.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class ProductImgs extends BaseEntity {

    private Long id;

    private Long productId;

    private String img;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

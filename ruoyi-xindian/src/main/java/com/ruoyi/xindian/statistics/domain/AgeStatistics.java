package com.ruoyi.xindian.statistics.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lixinlong
 * 2023/7/13 9:28
 */
public class AgeStatistics {

    private String name;

    private Integer value;

    public AgeStatistics() {
    }

    public AgeStatistics(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("value", value)
                .toString();
    }
}

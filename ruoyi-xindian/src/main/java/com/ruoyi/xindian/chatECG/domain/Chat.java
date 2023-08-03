package com.ruoyi.xindian.chatECG.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author lixinlong
 * 2023/7/29 11:20
 */
public class Chat extends BaseEntity {

    private String text;

    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("history", history)
                .toString();
    }
}

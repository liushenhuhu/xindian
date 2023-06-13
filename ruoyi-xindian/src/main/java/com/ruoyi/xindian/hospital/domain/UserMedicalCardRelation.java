package com.ruoyi.xindian.hospital.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户就诊卡关系表对象 user_medical_card_relation
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
public class UserMedicalCardRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关系编号 */
    private Long id;

    /** 关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他 */
    @Excel(name = "关系类型 0：本人，1：父母，2：兄弟/姐妹，3：伴侣，4：子女，5：同事/朋友，6：其他")
    private Long type;

    /** 账号编号 */
    @Excel(name = "账号编号")
    private Long accountId;

    /** 就诊卡编号 */
    @Excel(name = "就诊卡编号")
    private Long cardId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setCardId(Long cardId) 
    {
        this.cardId = cardId;
    }

    public Long getCardId() 
    {
        return cardId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("accountId", getAccountId())
            .append("cardId", getCardId())
            .toString();
    }
}

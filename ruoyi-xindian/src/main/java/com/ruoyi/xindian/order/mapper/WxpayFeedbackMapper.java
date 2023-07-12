package com.ruoyi.xindian.order.mapper;

import com.ruoyi.xindian.order.domain.WxpayFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 13401
* @description 针对表【wxpay_feedback】的数据库操作Mapper
* @createDate 2023-07-08 14:32:48
* @Entity com.ruoyi.xindian.order.domain.WxpayFeedback
*/
public interface WxpayFeedbackMapper extends BaseMapper<WxpayFeedback> {

    /**
     * 查询【请填写功能名称】
     *
     * @param feedbackiId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WxpayFeedback selectWxpayFeedbackByFeedbackiId(Long feedbackiId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WxpayFeedback> selectWxpayFeedbackList(WxpayFeedback wxpayFeedback);

    /**
     * 新增【请填写功能名称】
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 结果
     */
    public int insertWxpayFeedback(WxpayFeedback wxpayFeedback);

    /**
     * 修改【请填写功能名称】
     *
     * @param wxpayFeedback 【请填写功能名称】
     * @return 结果
     */
    public int updateWxpayFeedback(WxpayFeedback wxpayFeedback);

    /**
     * 删除【请填写功能名称】
     *
     * @param feedbackiId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWxpayFeedbackByFeedbackiId(Long feedbackiId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param feedbackiIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxpayFeedbackByFeedbackiIds(Long[] feedbackiIds);
}





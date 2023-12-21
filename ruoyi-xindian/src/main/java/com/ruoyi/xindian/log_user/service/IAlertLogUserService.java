package com.ruoyi.xindian.log_user.service;

import java.util.List;

import com.ruoyi.xindian.ano.domain.AnoUser;
import com.ruoyi.xindian.log_user.domain.AlertLogUser;
import com.ruoyi.xindian.log_user.domain.SelectVO;

/**
 * 标注分配Service接口
 * 
 * @author hanhan
 * @date 2022-12-30
 */
public interface IAlertLogUserService 
{
    /**
     * 查询标注分配
     * 
     * @param logId 标注分配主键
     * @return 标注分配
     */
    public AlertLogUser selectAlertLogUserByLogId(String logId,String userId);

    /**
     * 查询标注分配列表
     * 
     * @param alertLogUser 标注分配
     * @return 标注分配集合
     */
    public List<AlertLogUser> selectAlertLogUserList(AlertLogUser alertLogUser);

    /**
     * 新增标注分配
     * 
     * @param alertLogUser 标注分配
     * @return 结果
     */
    public int insertAlertLogUser(AlertLogUser alertLogUser);

    /**
     * 修改标注分配
     * 
     * @param alertLogUser 标注分配
     * @return 结果
     */
    public int updateAlertLogUser(AlertLogUser alertLogUser);

    /**
     * 批量删除标注分配
     * 
     * @param logIds 需要删除的标注分配主键集合
     * @return 结果
     */
    public int deleteAlertLogUserByLogIds(String[] logIds);

    /**
     * 删除标注分配信息
     * 
     * @param logId 标注分配主键
     * @return 结果
     */
    public int deleteAlertLogUserByLogId(String logId);

    List<SelectVO> selectList();

    void deleteAlertLogUserByUserIdAndPid(AnoUser anoUser);
}

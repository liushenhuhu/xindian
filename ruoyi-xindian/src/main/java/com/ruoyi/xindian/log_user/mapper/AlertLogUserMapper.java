package com.ruoyi.xindian.log_user.mapper;

import java.util.List;

import com.ruoyi.xindian.ano.domain.AnoUser;
import com.ruoyi.xindian.log_user.domain.AlertLogUser;
import com.ruoyi.xindian.log_user.domain.SelectVO;
import org.apache.ibatis.annotations.Param;

/**
 * 标注分配Mapper接口
 * 
 * @author hanhan
 * @date 2022-12-30
 */
public interface AlertLogUserMapper 
{
    /**
     * 查询标注分配
     * 
     * @param logId 标注分配主键
     * @return 标注分配
     */
    public AlertLogUser selectAlertLogUserByLogId(@Param("logId") String logId, @Param("userId")String userId);

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
     * 删除标注分配
     * 
     * @param logId 标注分配主键
     * @return 结果
     */
    public int deleteAlertLogUserByLogId(String logId);

    /**
     * 批量删除标注分配
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlertLogUserByLogIds(String[] logIds);

    public List<SelectVO> selectList();

    void deleteAlertLogUserByUserIdAndPid(AnoUser anoUser);

    int islabel(AlertLogUser alertLogUser);
}

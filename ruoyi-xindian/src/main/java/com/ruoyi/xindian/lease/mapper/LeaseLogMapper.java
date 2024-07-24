package com.ruoyi.xindian.lease.mapper;

import com.ruoyi.xindian.lease.domain.LeaseLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【lease_log】的数据库操作Mapper
* @createDate 2024-07-23 15:36:32
* @Entity com.ruoyi.xindian.lease.domain.LeaseLog
*/
public interface LeaseLogMapper extends BaseMapper<LeaseLog> {

    /**
     * 查询租赁日志
     *
     * @param leaseLogId 租赁日志主键
     * @return 租赁日志
     */
    public LeaseLog selectLeaseLogByLeaseLogId(Long leaseLogId);

    /**
     * 查询租赁日志列表
     *
     * @param leaseLog 租赁日志
     * @return 租赁日志集合
     */
    public List<LeaseLog> selectLeaseLogList(LeaseLog leaseLog);

    /**
     * 新增租赁日志
     *
     * @param leaseLog 租赁日志
     * @return 结果
     */
    public int insertLeaseLog(LeaseLog leaseLog);

    /**
     * 修改租赁日志
     *
     * @param leaseLog 租赁日志
     * @return 结果
     */
    public int updateLeaseLog(LeaseLog leaseLog);

    /**
     * 删除租赁日志
     *
     * @param leaseLogId 租赁日志主键
     * @return 结果
     */
    public int deleteLeaseLogByLeaseLogId(Long leaseLogId);

    /**
     * 批量删除租赁日志
     *
     * @param leaseLogIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLeaseLogByLeaseLogIds(Long[] leaseLogIds);
}





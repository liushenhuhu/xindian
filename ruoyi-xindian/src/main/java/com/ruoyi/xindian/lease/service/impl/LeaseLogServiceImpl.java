package com.ruoyi.xindian.lease.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.lease.domain.LeaseLog;
import com.ruoyi.xindian.lease.service.LeaseLogService;
import com.ruoyi.xindian.lease.mapper.LeaseLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author w
* @description 针对表【lease_log】的数据库操作Service实现
* @createDate 2024-07-23 15:36:32
*/
@Service
public class LeaseLogServiceImpl extends ServiceImpl<LeaseLogMapper, LeaseLog>
    implements LeaseLogService{

    @Autowired
    private LeaseLogMapper leaseLogMapper;

    /**
     * 查询租赁日志
     *
     * @param leaseLogId 租赁日志主键
     * @return 租赁日志
     */
    @Override
    public LeaseLog selectLeaseLogByLeaseLogId(Long leaseLogId)
    {
        return leaseLogMapper.selectLeaseLogByLeaseLogId(leaseLogId);
    }

    /**
     * 查询租赁日志列表
     *
     * @param leaseLog 租赁日志
     * @return 租赁日志
     */
    @Override
    public List<LeaseLog> selectLeaseLogList(LeaseLog leaseLog)
    {
        return leaseLogMapper.selectLeaseLogList(leaseLog);
    }

    /**
     * 新增租赁日志
     *
     * @param leaseLog 租赁日志
     * @return 结果
     */
    @Override
    public int insertLeaseLog(LeaseLog leaseLog)
    {
        leaseLog.setCreateTime(DateUtils.getNowDate());
        return leaseLogMapper.insertLeaseLog(leaseLog);
    }

    /**
     * 修改租赁日志
     *
     * @param leaseLog 租赁日志
     * @return 结果
     */
    @Override
    public int updateLeaseLog(LeaseLog leaseLog)
    {
        leaseLog.setUpdateTime(DateUtils.getNowDate());
        return leaseLogMapper.updateLeaseLog(leaseLog);
    }

    /**
     * 批量删除租赁日志
     *
     * @param leaseLogIds 需要删除的租赁日志主键
     * @return 结果
     */
    @Override
    public int deleteLeaseLogByLeaseLogIds(Long[] leaseLogIds)
    {
        return leaseLogMapper.deleteLeaseLogByLeaseLogIds(leaseLogIds);
    }

    /**
     * 删除租赁日志信息
     *
     * @param leaseLogId 租赁日志主键
     * @return 结果
     */
    @Override
    public int deleteLeaseLogByLeaseLogId(Long leaseLogId)
    {
        return leaseLogMapper.deleteLeaseLogByLeaseLogId(leaseLogId);
    }
}





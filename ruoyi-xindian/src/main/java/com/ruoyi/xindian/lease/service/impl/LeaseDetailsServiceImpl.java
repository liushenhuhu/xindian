package com.ruoyi.xindian.lease.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.lease.mapper.LeaseDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author w
* @description 针对表【lease_details】的数据库操作Service实现
* @createDate 2024-07-18 11:51:35
*/
@Service
public class LeaseDetailsServiceImpl extends ServiceImpl<LeaseDetailsMapper, LeaseDetails>
    implements LeaseDetailsService{

    @Autowired
    private LeaseDetailsMapper leaseDetailsMapper;

    /**
     * 查询租赁详情
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 租赁详情
     */
    @Override
    public LeaseDetails selectLeaseDetailsByLeaseDetailsId(Long leaseDetailsId)
    {
        return leaseDetailsMapper.selectLeaseDetailsByLeaseDetailsId(leaseDetailsId);
    }

    /**
     * 查询租赁详情列表
     *
     * @param leaseDetails 租赁详情
     * @return 租赁详情
     */
    @Override
    public List<LeaseDetails> selectLeaseDetailsList(LeaseDetails leaseDetails)
    {
        return leaseDetailsMapper.selectLeaseDetailsList(leaseDetails);
    }

    /**
     * 新增租赁详情
     *
     * @param leaseDetails 租赁详情
     * @return 结果
     */
    @Override
    public int insertLeaseDetails(LeaseDetails leaseDetails)
    {
        return leaseDetailsMapper.insertLeaseDetails(leaseDetails);
    }

    /**
     * 修改租赁详情
     *
     * @param leaseDetails 租赁详情
     * @return 结果
     */
    @Override
    public int updateLeaseDetails(LeaseDetails leaseDetails)
    {
        return leaseDetailsMapper.updateLeaseDetails(leaseDetails);
    }

    /**
     * 批量删除租赁详情
     *
     * @param leaseDetailsIds 需要删除的租赁详情主键
     * @return 结果
     */
    @Override
    public int deleteLeaseDetailsByLeaseDetailsIds(Long[] leaseDetailsIds)
    {
        return leaseDetailsMapper.deleteLeaseDetailsByLeaseDetailsIds(leaseDetailsIds);
    }

    /**
     * 删除租赁详情信息
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 结果
     */
    @Override
    public int deleteLeaseDetailsByLeaseDetailsId(Long leaseDetailsId)
    {
        return leaseDetailsMapper.deleteLeaseDetailsByLeaseDetailsId(leaseDetailsId);
    }

    @Override
    public int deleteLeaseDetailsByLeaseId(Long leaseId) {
        return leaseDetailsMapper.delete(new LambdaQueryWrapper<LeaseDetails>().eq(LeaseDetails::getLeaseId,leaseId));
    }
}





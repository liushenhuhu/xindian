package com.ruoyi.xindian.lease.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.lease.domain.Lease;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.lease.service.LeaseService;
import com.ruoyi.xindian.lease.mapper.LeaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author w
* @description 针对表【lease】的数据库操作Service实现
* @createDate 2024-07-18 11:51:35
*/
@Service
public class LeaseServiceImpl extends ServiceImpl<LeaseMapper, Lease>
    implements LeaseService{


    @Resource
    private LeaseMapper leaseMapper;

    @Autowired
    private LeaseDetailsService leaseDetailsService;

    /**
     * 查询租赁
     *
     * @param leaseId 租赁主键
     * @return 租赁
     */
    @Override
    public Lease selectLeaseByLeaseId(Long leaseId)
    {
        return leaseMapper.selectLeaseByLeaseId(leaseId);
    }



    /**
     * 查询租赁列表
     *
     * @param lease 租赁
     * @return 租赁
     */
    @Override
    public List<Lease> selectLeaseList(Lease lease)
    {
        return leaseMapper.selectLeaseList(lease);
    }

    @Override
    public List<Lease> selectLeaseListByUsable(LeaseDetails lease) {
        return leaseMapper.selectLeaseListByUsable(lease).stream().filter(lease1 -> !lease1.getDetails().isEmpty()).collect(Collectors.toList());
    }

    @Override
    public List<Lease> selectLeaseListByUsed(Lease lease) {
        return leaseMapper.selectLeaseListByUsed(lease);
    }

    /**
     * 新增租赁
     *
     * @param lease 租赁
     * @return 结果
     */
    @Override
    public int insertLease(Lease lease)
    {
        lease.setCreateTime(DateUtils.getNowDate());
        int i = leaseMapper.insertLease(lease);
        if (i>0){
            List<LeaseDetails> details = lease.getDetails();
            List<LeaseDetails> collect = details.stream().peek(leaseDetails -> {
                leaseDetails.setLeaseId(lease.getLeaseId());

            }).collect(Collectors.toList());
            leaseDetailsService.saveBatch(collect);
        }
        return i;
    }

    /**
     * 修改租赁
     *
     * @param lease 租赁
     * @return 结果
     */
    @Override
    public int updateLease(Lease lease)
    {
        lease.setUpdateTime(DateUtils.getNowDate());
        int i = leaseMapper.updateLease(lease);
        if (i>0&& StringUtils.isNotEmpty(lease.getUserName())){
            leaseDetailsService.deleteLeaseDetailsByLeaseId(lease.getLeaseId());
            List<LeaseDetails> details = lease.getDetails();
            List<LeaseDetails> collect = details.stream().peek(leaseDetails -> {
                leaseDetails.setLeaseId(lease.getLeaseId());
            }).collect(Collectors.toList());
            leaseDetailsService.saveBatch(collect);
        }
        return i;
    }

    /**
     * 批量删除租赁
     *
     * @param leaseIds 需要删除的租赁主键
     * @return 结果
     */
    @Override
    public int deleteLeaseByLeaseIds(Long[] leaseIds)
    {
        return leaseMapper.deleteLeaseByLeaseIds(leaseIds);
    }

    /**
     * 删除租赁信息
     *
     * @param leaseId 租赁主键
     * @return 结果
     */
    @Override
    public int deleteLeaseByLeaseId(Long leaseId)
    {
        return leaseMapper.deleteLeaseByLeaseId(leaseId);
    }
}





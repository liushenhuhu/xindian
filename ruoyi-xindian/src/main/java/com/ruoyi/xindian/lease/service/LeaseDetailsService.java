package com.ruoyi.xindian.lease.service;

import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author w
* @description 针对表【lease_details】的数据库操作Service
* @createDate 2024-07-18 11:51:35
*/
public interface LeaseDetailsService extends IService<LeaseDetails> {

    /**
     * 查询租赁详情
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 租赁详情
     */
    public LeaseDetails selectLeaseDetailsByLeaseDetailsId(Long leaseDetailsId);

    /**
     * 查询租赁详情列表
     *
     * @param leaseDetails 租赁详情
     * @return 租赁详情集合
     */
    public List<LeaseDetails> selectLeaseDetailsList(LeaseDetails leaseDetails);

    /**
     * 新增租赁详情
     *
     * @param leaseDetails 租赁详情
     * @return 结果
     */
    public int insertLeaseDetails(LeaseDetails leaseDetails);

    /**
     * 修改租赁详情
     *
     * @param leaseDetails 租赁详情
     * @return 结果
     */
    public int updateLeaseDetails(LeaseDetails leaseDetails);

    /**
     * 批量删除租赁详情
     *
     * @param leaseDetailsIds 需要删除的租赁详情主键集合
     * @return 结果
     */
    public int deleteLeaseDetailsByLeaseDetailsIds(Long[] leaseDetailsIds);

    /**
     * 删除租赁详情信息
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 结果
     */
    public int deleteLeaseDetailsByLeaseDetailsId(Long leaseDetailsId);
    public int deleteLeaseDetailsByLeaseId(Long leaseId);
}

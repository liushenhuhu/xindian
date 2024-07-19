package com.ruoyi.xindian.lease.mapper;

import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author w
* @description 针对表【lease_details】的数据库操作Mapper
* @createDate 2024-07-18 11:51:35
* @Entity com.ruoyi.xindian.lease.domain.LeaseDetails
*/
public interface LeaseDetailsMapper extends BaseMapper<LeaseDetails> {

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
     * 删除租赁详情
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 结果
     */
    public int deleteLeaseDetailsByLeaseDetailsId(Long leaseDetailsId);

    /**
     * 批量删除租赁详情
     *
     * @param leaseDetailsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLeaseDetailsByLeaseDetailsIds(Long[] leaseDetailsIds);
}





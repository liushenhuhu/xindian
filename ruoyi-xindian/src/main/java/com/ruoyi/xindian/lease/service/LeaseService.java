package com.ruoyi.xindian.lease.service;

import com.ruoyi.xindian.lease.domain.Lease;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.lease.domain.LeaseDetails;

import java.util.List;

/**
* @author w
* @description 针对表【lease】的数据库操作Service
* @createDate 2024-07-18 11:51:35
*/
public interface LeaseService extends IService<Lease> {

    /**
     * 查询租赁
     *
     * @param leaseId 租赁主键
     * @return 租赁
     */
    public Lease selectLeaseByLeaseId(Long leaseId);


    /**
     * 查询租赁列表
     *
     * @param lease 租赁
     * @return 租赁集合
     */
    public List<Lease> selectLeaseList(Lease lease);
    public List<Lease> selectLeaseListByUsable(LeaseDetails lease);
    public List<Lease> selectLeaseListByUsed(LeaseDetails lease);

    /**
     * 新增租赁
     *
     * @param lease 租赁
     * @return 结果
     */
    public int insertLease(Lease lease);

    /**
     * 修改租赁
     *
     * @param lease 租赁
     * @return 结果
     */
    public int updateLease(Lease lease);

    /**
     * 批量删除租赁
     *
     * @param leaseIds 需要删除的租赁主键集合
     * @return 结果
     */
    public int deleteLeaseByLeaseIds(Long[] leaseIds);

    /**
     * 删除租赁信息
     *
     * @param leaseId 租赁主键
     * @return 结果
     */
    public int deleteLeaseByLeaseId(Long leaseId);
}

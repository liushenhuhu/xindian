package com.ruoyi.xindian.lease.service;

import com.ruoyi.xindian.lease.domain.Lease;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* @author w
* @description 针对表【lease_details(租赁详情)】的数据库操作Service
* @createDate 2024-07-23 15:36:32
*/
public interface LeaseDetailsService extends IService<LeaseDetails> {

    /**
     * 查询租赁详情
     *
     * @param leaseDetailsId 租赁详情主键
     * @return 租赁详情
     */
    public LeaseDetails selectLeaseDetailsByLeaseDetailsId(Long leaseDetailsId);


    public LeaseDetails selectLeaseDetailsByEquipmentCode(String equipmentCode);

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
    public int updateLeaseDetailsByEquipmentCode(LeaseDetails leaseDetails) throws Exception;

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

    int deleteLeaseDetailsByLeaseId(Long leaseId);

    public List<LeaseDetails> selectLeaseByPhone(LeaseDetails lease);

    Map<String, Object> giveBack(LeaseDetails lease, HttpServletResponse response) throws Exception;
}

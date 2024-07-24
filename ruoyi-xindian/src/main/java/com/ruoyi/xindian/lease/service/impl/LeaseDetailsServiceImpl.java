package com.ruoyi.xindian.lease.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.domain.LeaseLog;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.lease.mapper.LeaseDetailsMapper;
import com.ruoyi.xindian.lease.service.LeaseLogService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient.service.PatientBloodService;
import com.ruoyi.xindian.patient.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author w
* @description 针对表【lease_details(租赁详情)】的数据库操作Service实现
* @createDate 2024-07-23 15:36:32
*/
@Service
public class LeaseDetailsServiceImpl extends ServiceImpl<LeaseDetailsMapper, LeaseDetails>
    implements LeaseDetailsService{

    @Resource
    private LeaseDetailsMapper leaseDetailsMapper;

    @Resource
    private IPatientService  patientService;

    @Resource
    private LeaseLogService leaseLogService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

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

    @Override
    public LeaseDetails selectLeaseDetailsByEquipmentCode(String equipmentCode) {
        return leaseDetailsMapper.selectLeaseDetailsByCode(equipmentCode);
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

    @Override
    public int updateLeaseDetailsByEquipmentCode(LeaseDetails leaseDetails) throws Exception {


        if (Boolean.TRUE.equals(redisTemplate.hasKey("lease:" + leaseDetails.getEquipmentCode() + ":" + leaseDetails.getPhone()))){
            throw  new ServiceException("请勿重复发送");
        }
        redisTemplate.opsForValue().set("lease:" + leaseDetails.getEquipmentCode() + ":" + leaseDetails.getPhone(), "1", 5, TimeUnit.MINUTES);

        LeaseDetails leaseDetails1 = selectLeaseDetailsByEquipmentCode(leaseDetails.getEquipmentCode());
        if (leaseDetails1==null){
            throw  new ServiceException("设备不存在");
        }
        if (StringUtils.isNotEmpty(leaseDetails1.getStatus())&&leaseDetails1.getStatus().equals("1")){
            return 0;
        }
        Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(leaseDetails.getPhone()));
        if (patient==null){
            throw  new ServiceException("设备已被绑定");
        }
        leaseDetails.setUsername(aesUtils.decrypt(patient.getPatientName()));
        leaseDetails.setCreateTime(new Date());

        int update = leaseDetailsMapper.update(leaseDetails, new LambdaQueryWrapper<LeaseDetails>().eq(LeaseDetails::getEquipmentCode, leaseDetails.getEquipmentCode()));


        LeaseLog leaseLog = new LeaseLog();
        leaseLog.setUsername(aesUtils.decrypt(patient.getPatientName()));
        leaseLog.setPhone(leaseDetails.getPhone());
        leaseLog.setStatus(leaseDetails.getStatus());
        leaseLog.setEquipmentCode(leaseDetails1.getEquipmentCode());
        leaseLog.setEquipmentType(leaseDetails1.getEquipmentType());
        leaseLog.setCreateTime(new Date());
        leaseLog.setUpdateTime(new Date());
        leaseLogService.save(leaseLog);
        return update;
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





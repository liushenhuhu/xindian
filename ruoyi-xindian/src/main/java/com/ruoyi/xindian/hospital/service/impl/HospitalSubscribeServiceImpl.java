package com.ruoyi.xindian.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.hospital.domain.HospitalSubscribe;
import com.ruoyi.xindian.hospital.service.HospitalSubscribeService;
import com.ruoyi.xindian.hospital.mapper.HospitalSubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 13401
* @description 针对表【hospital_subscribe(门诊预约医院)】的数据库操作Service实现
* @createDate 2023-10-20 09:31:04
*/
@Service
public class HospitalSubscribeServiceImpl extends ServiceImpl<HospitalSubscribeMapper, HospitalSubscribe>
    implements HospitalSubscribeService{


    @Autowired
    private HospitalSubscribeMapper hospitalSubscribeMapper;

    /**
     * 查询门诊预约医院
     *
     * @param subscribeId 门诊预约医院主键
     * @return 门诊预约医院
     */
    @Override
    public HospitalSubscribe selectHospitalSubscribeBySubscribeId(Long subscribeId)
    {
        return hospitalSubscribeMapper.selectHospitalSubscribeBySubscribeId(subscribeId);
    }

    /**
     * 查询门诊预约医院列表
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 门诊预约医院
     */
    @Override
    public List<HospitalSubscribe> selectHospitalSubscribeList(HospitalSubscribe hospitalSubscribe)
    {
        return hospitalSubscribeMapper.selectHospitalSubscribeList(hospitalSubscribe);
    }

    /**
     * 新增门诊预约医院
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 结果
     */
    @Override
    public int insertHospitalSubscribe(HospitalSubscribe hospitalSubscribe)
    {
        hospitalSubscribe.setCreateTime(DateUtils.getNowDate());
        return hospitalSubscribeMapper.insertHospitalSubscribe(hospitalSubscribe);
    }

    /**
     * 修改门诊预约医院
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 结果
     */
    @Override
    public int updateHospitalSubscribe(HospitalSubscribe hospitalSubscribe)
    {
        hospitalSubscribe.setUpdateTime(DateUtils.getNowDate());
        return hospitalSubscribeMapper.updateHospitalSubscribe(hospitalSubscribe);
    }

    /**
     * 批量删除门诊预约医院
     *
     * @param subscribeIds 需要删除的门诊预约医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSubscribeBySubscribeIds(Long[] subscribeIds)
    {
        return hospitalSubscribeMapper.deleteHospitalSubscribeBySubscribeIds(subscribeIds);
    }

    /**
     * 删除门诊预约医院信息
     *
     * @param subscribeId 门诊预约医院主键
     * @return 结果
     */
    @Override
    public int deleteHospitalSubscribeBySubscribeId(Long subscribeId)
    {
        return hospitalSubscribeMapper.deleteHospitalSubscribeBySubscribeId(subscribeId);
    }
}





package com.ruoyi.xindian.hospital.service;

import com.ruoyi.xindian.hospital.domain.HospitalSubscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;

import java.util.List;

/**
* @author 13401
* @description 针对表【hospital_subscribe(门诊预约医院)】的数据库操作Service
* @createDate 2023-10-20 09:31:04
*/
public interface HospitalSubscribeService extends IService<HospitalSubscribe> {


    /**
     * 查询门诊预约医院
     *
     * @param subscribeId 门诊预约医院主键
     * @return 门诊预约医院
     */
    public HospitalSubscribe selectHospitalSubscribeBySubscribeId(Long subscribeId);

    /**
     * 查询门诊预约医院列表
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 门诊预约医院集合
     */
    public List<HospitalSubscribe> selectHospitalSubscribeList(HospitalSubscribe hospitalSubscribe);

    /**
     * 新增门诊预约医院
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 结果
     */
    public int insertHospitalSubscribe(HospitalSubscribe hospitalSubscribe);

    /**
     * 修改门诊预约医院
     *
     * @param hospitalSubscribe 门诊预约医院
     * @return 结果
     */
    public int updateHospitalSubscribe(HospitalSubscribe hospitalSubscribe);

    /**
     * 批量删除门诊预约医院
     *
     * @param subscribeIds 需要删除的门诊预约医院主键集合
     * @return 结果
     */
    public int deleteHospitalSubscribeBySubscribeIds(Long[] subscribeIds);

    /**
     * 删除门诊预约医院信息
     *
     * @param subscribeId 门诊预约医院主键
     * @return 结果
     */
    public int deleteHospitalSubscribeBySubscribeId(Long subscribeId);

    List<ListValueAndLabelVO> getHospitalIdFindList(Long hospitalId);
}


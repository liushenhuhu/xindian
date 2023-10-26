package com.ruoyi.xindian.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.xindian.hospital.domain.HospitalOutpatientRelation;
import com.ruoyi.xindian.hospital.domain.HospitalSpecial;
import com.ruoyi.xindian.hospital.domain.HospitalSpecialRelation;
import com.ruoyi.xindian.hospital.domain.HospitalSubscribe;
import com.ruoyi.xindian.hospital.service.HospitalSubscribeService;
import com.ruoyi.xindian.hospital.mapper.HospitalSubscribeMapper;
import com.ruoyi.xindian.hospital.service.IHospitalOutpatientRelationService;
import com.ruoyi.xindian.hospital.service.IHospitalSpecialRelationService;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    @Resource
    private IHospitalSpecialRelationService hospitalSpecialRelationService;


    @Resource
    private IHospitalOutpatientRelationService hospitalOutpatientRelationService;

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

    @Override
    public List<ListValueAndLabelVO> getHospitalIdFindList(Long hospitalId) {
        HospitalSpecialRelation hospitalSpecialRelation = new HospitalSpecialRelation();
        hospitalSpecialRelation.setHospitalId(hospitalId);
        List<HospitalSpecialRelation> hospitalSpecialRelations = hospitalSpecialRelationService.selectHospitalSpecialRelationList(hospitalSpecialRelation);
        List<ListValueAndLabelVO> list = new ArrayList<>();
        for (HospitalSpecialRelation hospitalSpecialRelation1 : hospitalSpecialRelations){
            ListValueAndLabelVO listValueAndLabelVO = new ListValueAndLabelVO();
            listValueAndLabelVO.setLabel(hospitalSpecialRelation1.getHospitalSpecial().getSpecialName());
            listValueAndLabelVO.setValue(hospitalSpecialRelation1.getHospitalSpecial().getSpecialId().toString());
            HospitalOutpatientRelation hospitalOutpatientRelation = new HospitalOutpatientRelation();
            hospitalOutpatientRelation.setHospitalId(hospitalId);
            hospitalOutpatientRelation.setSpecialId(hospitalSpecialRelation1.getHospitalSpecial().getSpecialId());
            List<HospitalOutpatientRelation> hospitalOutpatientRelations = hospitalOutpatientRelationService.selectHospitalOutpatientRelationList(hospitalOutpatientRelation);
            for (HospitalOutpatientRelation hospitalOutpatientRelation1 : hospitalOutpatientRelations){

                DocVO docVO = new DocVO();
                docVO.setLabel(hospitalOutpatientRelation1.getHospitalOutpatient().getOutpatientName());
                docVO.setValue(hospitalOutpatientRelation1.getHospitalOutpatient().getOutpatientId().toString());
                listValueAndLabelVO.getChildren().add(docVO);
            }
            list.add(listValueAndLabelVO);
        }
        return list;
    }
}




